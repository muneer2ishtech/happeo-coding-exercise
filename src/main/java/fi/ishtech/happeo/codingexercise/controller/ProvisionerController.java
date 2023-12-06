package fi.ishtech.happeo.codingexercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fi.ishtech.happeo.codingexercise.payload.request.ProvisionerRequest;
import fi.ishtech.happeo.codingexercise.payload.response.OrgProvisionerResponse;
import fi.ishtech.happeo.codingexercise.payload.response.ProvisionerResponse;
import fi.ishtech.happeo.codingexercise.payload.response.UserProvisioningResponse;
import fi.ishtech.happeo.codingexercise.service.ProvisionerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller with Rest APIs for Provisioner
 *
 * @author Muneer Ahmed Syed
 */
@RestController
@Slf4j
public class ProvisionerController {

	@Autowired
	private ProvisionerService provisionerService;

	/**
	 * Fetches all Provisioners
	 *
	 * @return {@link ResponseEntity}&lt;{@link List}&lt;{@link ProvisionerResponse}&gt;&gt;
	 */
	@GetMapping(path = "/api/provisioners", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProvisionerResponse>> findProvisioners() {
		return ResponseEntity.ok(provisionerService.findAll());
	}

	/**
	 * Creates Organisation and Provisioner map and a secret for it.<br>
	 * Creates Provisioner if not present.
	 *
	 * @param provisioner    - {@link ProvisionerRequest}
	 * @param organisationId
	 * @return {@link ResponseEntity}&lt;{@link UserProvisioningResponse}&gt;
	 */
	@PostMapping(path = "/api/organisations/{organisationId}/provisioners", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrgProvisionerResponse> createOrgProvisioner(
			@Valid @RequestBody ProvisionerRequest provisioner, @PathVariable Long organisationId) {
		log.debug("Creating OrgProvisioner for Organisation:{}, Provisioner:{}", organisationId, provisioner.getName());

		OrgProvisionerResponse orgProvisionerResponse = provisionerService.createOrgProvisioner(organisationId,
				provisioner);

		return ResponseEntity.status(HttpStatus.CREATED).body(orgProvisionerResponse);
	}

}
