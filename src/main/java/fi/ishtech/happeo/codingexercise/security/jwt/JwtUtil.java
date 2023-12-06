package fi.ishtech.happeo.codingexercise.security.jwt;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

	public boolean validateJwtToken(String jwtSecret, String subject, String authToken) {
		try {
			jwtParser(jwtSecret, subject).parse(authToken);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	private JwtParser jwtParser(String jwtSecret, String subject) {
		return Jwts.parser().requireSubject(subject).verifyWith(jwtKey(jwtSecret)).build();
	}

	public String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

	private SecretKey jwtKey(String jwtSecret) {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	private SecretKey generateSecretKey() {
		return Jwts.SIG.HS256.key().build();
	}

	public String generateEncodedSecretString() {
		return Encoders.BASE64.encode(generateSecretKey().getEncoded());
	}

	public String decodeBase64(String encoded) {
		return new String(Decoders.BASE64.decode(encoded));
	}

}
