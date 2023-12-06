package fi.ishtech.happeo.codingexercise.service.impl;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ishtech.happeo.codingexercise.entity.OrgProvisioner;
import fi.ishtech.happeo.codingexercise.entity.Provisioner;
import fi.ishtech.happeo.codingexercise.mapper.ProvisionerMapper;
import fi.ishtech.happeo.codingexercise.payload.request.ProvisionerRequest;
import fi.ishtech.happeo.codingexercise.payload.response.ProvisionerResponse;
import fi.ishtech.happeo.codingexercise.repo.OrgProvisionerRepo;
import fi.ishtech.happeo.codingexercise.repo.ProvisionerRepo;
import fi.ishtech.happeo.codingexercise.service.ProvisionerService;
import io.jsonwebtoken.io.Encoders;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 * @author Muneer Ahmed Syed
 */
@Service
@Transactional
@Slf4j
public class ProvisionerServiceImpl implements ProvisionerService {

	@Autowired
	private ProvisionerRepo provisionerRepo;

	@Autowired
	private OrgProvisionerRepo orgProvisionerRepo;

	@Autowired
	private ProvisionerMapper provisionerMapper;

	@Override
	public List<ProvisionerResponse> findAll() {
		return provisionerMapper.toResponse(provisionerRepo.findAll());
	}

	@Override
	public ProvisionerResponse createOrgProvisioner(Long organisationId, @Valid ProvisionerRequest provisionerRequest) {
		Provisioner provisioner = provisionerRepo.findOneByNameIgnoreCase(provisionerRequest.getName());

		if (provisioner == null) {
			provisioner = new Provisioner();
			provisioner.setName(provisionerRequest.getName());

			provisioner = provisionerRepo.save(provisioner);
			log.info("Created new Provisioner({}) as missing for {}", provisioner.getId(), provisioner.getName());
		}

		String secret = createOrgProvisioner(organisationId, provisioner.getId());

		ProvisionerResponse provisionerResponse = provisionerMapper.toResponse(provisioner);
		provisionerResponse.setSecret(secret);

		return provisionerResponse;
	}

	private String createOrgProvisioner(Long organisationId, Long provisionerId) {
		OrgProvisioner orgProvisioner = new OrgProvisioner();
		orgProvisioner.setOrganisationId(organisationId);
		orgProvisioner.setProvisionerId(provisionerId);

		// 64 char string generates 256 bit length encoding needed for jwt signing
		String secret = RandomStringUtils.randomAlphanumeric(64);

		String encodedSecret = Encoders.BASE64.encode(secret.getBytes());

		orgProvisioner.setSecret(encodedSecret);

		orgProvisioner = orgProvisionerRepo.save(orgProvisioner);
		log.debug("Created new OrgProvisioner({})", orgProvisioner.getId());

		return secret;
	}

}
