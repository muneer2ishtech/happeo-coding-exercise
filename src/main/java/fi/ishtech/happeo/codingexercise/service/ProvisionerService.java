package fi.ishtech.happeo.codingexercise.service;

import java.util.List;

import fi.ishtech.happeo.codingexercise.payload.request.ProvisionerRequest;
import fi.ishtech.happeo.codingexercise.payload.response.ProvisionerResponse;
import jakarta.validation.Valid;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface ProvisionerService {

	List<ProvisionerResponse> findAll();

	ProvisionerResponse createOrgProvisioner(Long organisationId, @Valid ProvisionerRequest provisionerRequest);

}
