package fi.ishtech.happeo.codingexercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fi.ishtech.ekahau.codingexercise.service.UserService;
import fi.ishtech.happeo.codingexercise.payload.request.UserProvisioningRequest;
import fi.ishtech.happeo.codingexercise.payload.response.UserProvisioningResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@RestController
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @return
	 */
	@GetMapping("/api/organisations/{organisationId}/users")
	public ResponseEntity<Void> findUsers() {
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}

	/**
	 * @param user - {@link UserProvisioningRequest}
	 * @param organisationId
	 * @param provisionerId
	 * @return {@link UserProvisioningResponse}
	 */
	@PostMapping("/api/organisations/{organisationId}/provisioner/{provisionerId}/users")
	public ResponseEntity<UserProvisioningResponse> provisionNewUser(@Valid @RequestBody UserProvisioningRequest user,
			@PathVariable Long organisationId, @PathVariable Long provisionerId) {
		log.debug("Provisioning request for Organisation:{}, Provisioner:{}, externalId:{}", organisationId,
				provisionerId, user.getId());
		// TODO: validate token based on organisationId and provisionerId

		UserProvisioningResponse userProvisioningResponse = userService.create(organisationId, user);

		return ResponseEntity.ok(userProvisioningResponse);
	}

}
