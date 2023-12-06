package fi.ishtech.happeo.codingexercise.security.jwt;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerMapping;

import fi.ishtech.happeo.codingexercise.entity.OrgProvisioner;
import fi.ishtech.happeo.codingexercise.repo.OrgProvisionerRepo;
import fi.ishtech.happeo.codingexercise.security.OrganisationProvisionerAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private OrgProvisionerRepo orgProvisionerRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = jwtUtil.parseJwt(request);

			if (jwt != null) {
				Long organisationId = fetchOrganisationIdFromPathVariable(request);
				Long provisionerId = fetchProvisionerIdFromPathVariable(request);

				OrgProvisioner orgProvisioner = orgProvisionerRepo
						.findByOrganisationIdAndProvisionerId(organisationId, provisionerId)
						.orElseThrow(() -> new BadCredentialsException(
								"Secret not present for organisationId and provisionerId"));

				String secret = orgProvisioner.getSecret();

				if (jwtUtil.validateJwtToken(secret, organisationId.toString(), jwt)) {
					OrganisationProvisionerAuthentication authentication = new OrganisationProvisionerAuthentication(
							organisationId);

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception e) {
			log.error("Cannot set authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> fetchPathVariables(HttpServletRequest request) {
		// This is not getting PathVariable
		return (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
	}

	/**
	 * Fetches path variable from Rest URI.<br>
	 * It is not working now
	 * 
	 * @param request
	 * @param name
	 * @return {@link PathVariable}
	 */
	@SuppressWarnings("unused")
	private Long fetchFromPathVariable(HttpServletRequest request, String name) {
		Map<String, String> pathVars = fetchPathVariables(request);

		Assert.isTrue(pathVars.containsKey(name), name + " not in URL");

		String str = pathVars.get(name);
		Assert.isTrue(StringUtils.isNotBlank(str), name + " not in URL");

		Long val;
		try {
			val = Long.valueOf(name);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid " + name + " in URL, should be an integer");
		}

		return val;
	}

	private Long fetchOrganisationIdFromPathVariable(HttpServletRequest request) {
		String uri = request.getRequestURI();
		log.trace("request uri:{}", request.getRequestURI());

		String str = StringUtils.substringBetween(uri, "/api/organisations/", "/provisioner/");
		log.debug("orgnaisationId:{}", str);
		Assert.isTrue(StringUtils.isNotBlank(str), "orgnisationId not in URL");

		try {
			return Long.valueOf(str);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid orgnisationId in URL, should be an integer");
		}
	}

	private Long fetchProvisionerIdFromPathVariable(HttpServletRequest request) {
		String uri = request.getRequestURI();
		log.trace("request uri:{}", request.getRequestURI());

		String str = StringUtils.substringBetween(uri, "/provisioner/", "/users");
		log.debug("provisionerId:{}", str);
		Assert.isTrue(StringUtils.isNotBlank(str), "provisionerId not in URL");

		try {
			return Long.valueOf(str);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid provisionerId in URL, should be an integer");
		}
	}

}
