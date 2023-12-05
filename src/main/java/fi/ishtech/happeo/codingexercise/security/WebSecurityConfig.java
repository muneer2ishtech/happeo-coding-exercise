package fi.ishtech.happeo.codingexercise.security;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import fi.ishtech.happeo.codingexercise.security.jwt.JwtFilter;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	@Bean
	JwtFilter jwtFilter() {
		return new JwtFilter();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// @formatter:off
 		http
 			.csrf(csrf -> csrf.disable())
 			.cors(corsConfigSource -> corsConfigurationSource())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(
					auth -> auth
						.requestMatchers(
								"/error",
								"/",
								"/api-docs",
								"/api-docs/**",
								"/swagger-ui/**",
								"/swagger-ui.html",
								"/favicon.ico"
						)
							.permitAll()
						.anyRequest()
							.authenticated())
			;
		// @formatter:on

		http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	/**
	 * Creates {@link CorsConfiguration}<br>
	 * Defaults are allowedOrigins = *, allowedHeaders = *, maxAge = 1800<br>
	 * You can use addAllowedOriginPattern to set http and/or https, subdomains etc.
	 * 
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.applyPermitDefaultValues();
		corsConfig.setAllowedMethods(
				Arrays.stream(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.toList()));

		UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();
		corsConfigSource.registerCorsConfiguration("/**", corsConfig);

		return corsConfigSource;
	}

}
