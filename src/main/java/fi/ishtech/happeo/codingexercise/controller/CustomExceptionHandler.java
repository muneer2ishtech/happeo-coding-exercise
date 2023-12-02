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

import fi.ishtech.happeo.codingexercise.MissingOrganisationProvisionerException;
import fi.ishtech.happeo.codingexercise.payload.response.CustomErrorResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler class for Exceptions and return appropriate HttpStatus and error
 * message.
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
			if (StringUtils.containsIgnoreCase(ex.getMessage(), "t_user")) {
				if (StringUtils.containsIgnoreCase(ex.getMessage(), "Key (email)")) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CustomErrorResponse
							.of(HttpStatus.BAD_REQUEST.value(), "User with input email already exists"));
				} else if (StringUtils.containsIgnoreCase(ex.getMessage(), "Key (organisation_id, external_id)")) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(CustomErrorResponse.of(HttpStatus.BAD_REQUEST.value(),
									"User with input external Id already exists for organisationId"));
				}
			} else if (StringUtils.containsIgnoreCase(ex.getMessage(), "t_org_provisioner")) {
				if (StringUtils.containsIgnoreCase(ex.getMessage(), "Key (organisation_id, provisioner_id)")) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CustomErrorResponse
							.of(HttpStatus.BAD_REQUEST.value(), "Provisioner already exists for Organisation"));
				}
			}
		} else if (StringUtils.containsIgnoreCase(ex.getMessage(), FK_CONSTRAINT_VIOLATION)) {
			if (StringUtils.containsIgnoreCase(ex.getMessage(), "Key (organisation_id)")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(CustomErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "Invalid organisationId"));
			}
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingOrganisationProvisionerException.class)
	public ResponseEntity<CustomErrorResponse> handleMissingOrganisationProvisionerException(
			MissingOrganisationProvisionerException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(CustomErrorResponse.of(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
	}

}
