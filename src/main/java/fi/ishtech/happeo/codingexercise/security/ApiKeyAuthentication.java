package fi.ishtech.happeo.codingexercise.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Muneer Ahmed Syed
 */
public class ApiKeyAuthentication extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 3209748235450487555L;
	
	private final String apiKey;

    public ApiKeyAuthentication(String apiKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return apiKey;
    }

}
