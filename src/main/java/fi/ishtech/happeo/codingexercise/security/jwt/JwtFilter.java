package fi.ishtech.happeo.codingexercise.security.jwt;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
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

@NoArgsConstructor
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
				Long organisationId = 2L; // fetchFromPathVariable(request, "organisationId");
				Long provisionerId = 1L; // fetchFromPathVariable(request, "provisionerId");

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
			logger.error("Cannot set authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> fetchPathVariables(HttpServletRequest request) {
		return (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

	}

	private Long fetchFromPathVariable(HttpServletRequest request, String key) {
		Map<String, String> pathVars = fetchPathVariables(request);

		Assert.isTrue(pathVars.containsKey(key), key + " not in URL");

		String str = pathVars.get(key);
		Assert.isTrue(StringUtils.isNotBlank(str), key + " not in URL");

		Long val;
		try {
			val = Long.valueOf(key);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid " + key + " in URL, should be an integer");
		}

		return val;
	}

}
