package fi.ishtech.happeo.codingexercise.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

	private static final String UNIQUE_CONSTRAINT_VIOLATION = "violates unique constraint";
	private static final String FK_CONSTRAINT_VIOLATION = "violates foreign key constraint";

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(ErrorResponse.create(ex, HttpStatus.UNAUTHORIZED, ex.getMessage()));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage()));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		log.error("DB Constraint failed: {}", ex.getMessage());
		if (StringUtils.containsIgnoreCase(ex.getMessage(), UNIQUE_CONSTRAINT_VIOLATION)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage()));
		} else if (StringUtils.containsIgnoreCase(ex.getMessage(), FK_CONSTRAINT_VIOLATION)) {
			if (StringUtils.containsIgnoreCase(ex.getMessage(), "fk_user_org_id")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid organisationId");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage()));
			}
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
		}
	}

}
