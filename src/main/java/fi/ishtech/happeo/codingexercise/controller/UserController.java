package fi.ishtech.happeo.codingexercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.ishtech.happeo.codingexercise.payload.request.UserProvisioningRequest;
import fi.ishtech.happeo.codingexercise.payload.response.UserProvisioningResponse;
import fi.ishtech.happeo.codingexercise.payload.response.UserResponse;
import fi.ishtech.happeo.codingexercise.service.UserService;
import fi.ishtech.happeo.codingexercise.spec.UserSpec;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller with Rest APIs for User related operations
 *
 * @author Muneer Ahmed Syed
 */
@RestController
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Finds User(s) matching the params<br>
	 * default sorted by User.id ascending<br>
	 * You can sort with other fields by passing sort parameters<br>
	 *
	 * @param organisationId
	 * @param isActive
	 * @param unpaged
	 * @param pageable
	 * @return {@link ResponseEntity}&lt;{@link Page}&lt;{@link UserResponse}&gt;&gt;
	 */
	@GetMapping(path = "/api/organisations/{organisationId}/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<UserResponse>> findUsers(@PathVariable Long organisationId,
			@RequestParam(required = false) Boolean isActive,
			@RequestParam(name = "unpaged", required = false, defaultValue = "false") Boolean unpaged,
			@SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

		pageable = unpaged ? pageable == null ? Pageable.unpaged() : Pageable.unpaged(pageable.getSort()) : pageable;

		return ResponseEntity.ok(userService.findAllAndMapToResponse(UserSpec.of(organisationId, isActive), pageable));
	}

	/**
	 * Activates inactive users belonging to organisation<br>
	 * If the user is already active, it does nothing, simply ignores those user
	 * IDs<br>
	 * If the user does not belong to organisation, it will not be activated, simply
	 * ignores those user IDs (No error thrown)<br>
	 *
	 * @param organisationId
	 * @param userIds        - {@link List}&lt;{@link Long}&gt;
	 * @return - no boby
	 */
	@PatchMapping("/api/organisations/{organisationId}/activate-users")
	public ResponseEntity<Void> updateUsersAsActive(@PathVariable Long organisationId,
			@RequestBody List<Long> userIds) {

		userService.updateAsActive(organisationId, userIds);

		return ResponseEntity.ok().build();
	}

	/**
	 * For provisioning new user by External identity system (Provisioner)
	 *
	 * @param user           - {@link UserProvisioningRequest}
	 * @param organisationId
	 * @param provisionerId
	 * @return {@link ResponseEntity}&lt;{@link UserProvisioningResponse}&gt;
	 */
	@PostMapping(path = "/api/organisations/{organisationId}/provisioner/{provisionerId}/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserProvisioningResponse> provisionNewUser(@Valid @RequestBody UserProvisioningRequest user,
			@PathVariable Long organisationId, @PathVariable Long provisionerId) {
		log.debug("Provisioning request for Organisation:{}, Provisioner:{}, externalId:{}", organisationId,
				provisionerId, user.getId());
		// TODO: validate token based on organisationId and provisionerId

		UserProvisioningResponse userProvisioningResponse = userService.create(organisationId, provisionerId, user);

		return ResponseEntity.status(HttpStatus.CREATED).body(userProvisioningResponse);
	}

}
