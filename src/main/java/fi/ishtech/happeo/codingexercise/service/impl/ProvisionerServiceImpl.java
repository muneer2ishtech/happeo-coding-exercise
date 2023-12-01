package fi.ishtech.happeo.codingexercise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ishtech.happeo.codingexercise.mapper.ProvisionerMapper;
import fi.ishtech.happeo.codingexercise.payload.response.ProvisionerResponse;
import fi.ishtech.happeo.codingexercise.repo.ProvisionerRepo;
import fi.ishtech.happeo.codingexercise.service.ProvisionerService;

/**
 * 
 *
 * @author Muneer Ahmed Syed
 */
@Service
public class ProvisionerServiceImpl implements ProvisionerService {

	@Autowired
	private ProvisionerRepo provisionerRepo;

	@Autowired
	private ProvisionerMapper provisionerMapper;

	@Override
	public List<ProvisionerResponse> findAll() {
		return provisionerMapper.toResponse(provisionerRepo.findAll());
	}

}
