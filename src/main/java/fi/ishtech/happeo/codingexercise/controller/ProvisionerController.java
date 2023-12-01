package fi.ishtech.happeo.codingexercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.ishtech.happeo.codingexercise.payload.response.ProvisionerResponse;
import fi.ishtech.happeo.codingexercise.service.ProvisionerService;

/**
 * Controller with Rest APIs for Provisioner
 *
 * @author Muneer Ahmed Syed
 */
@RestController
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

}
