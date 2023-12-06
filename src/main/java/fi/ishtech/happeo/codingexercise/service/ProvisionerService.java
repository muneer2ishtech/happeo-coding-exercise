package fi.ishtech.happeo.codingexercise.service;

import java.util.List;

import fi.ishtech.happeo.codingexercise.payload.request.ProvisionerRequest;
import fi.ishtech.happeo.codingexercise.payload.response.OrgProvisionerResponse;
import fi.ishtech.happeo.codingexercise.payload.response.ProvisionerResponse;
import jakarta.validation.Valid;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface ProvisionerService {

	List<ProvisionerResponse> findAll();

	/**
	 * Creates Organisation and Provisioner map and a secret for it.<br>
	 * Creates Provisioner if not present.
	 *
	 * @param organisationId
	 * @param provisionerRequest
	 * @return {@link OrgProvisionerResponse}
	 */
	OrgProvisionerResponse createOrgProvisioner(Long organisationId, @Valid ProvisionerRequest provisionerRequest);

}
