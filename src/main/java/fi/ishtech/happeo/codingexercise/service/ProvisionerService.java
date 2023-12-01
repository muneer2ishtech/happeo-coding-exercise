package fi.ishtech.happeo.codingexercise.service;

import java.util.List;

import fi.ishtech.happeo.codingexercise.payload.response.ProvisionerResponse;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface ProvisionerService {

	List<ProvisionerResponse> findAll();

}
