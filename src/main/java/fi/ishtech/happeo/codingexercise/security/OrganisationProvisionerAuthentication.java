package fi.ishtech.happeo.codingexercise.security;

import java.util.Arrays;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author Muneer Ahmed Syed
 */
public class OrganisationProvisionerAuthentication extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 2051524332149251942L;

	private final Long organisationId;

	public OrganisationProvisionerAuthentication(Long organisationId) {
		super(Arrays.asList(new SimpleGrantedAuthority("ROLE_EXTERNAL_SYSTEM")));
		this.organisationId = organisationId;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return organisationId;
	}

}
