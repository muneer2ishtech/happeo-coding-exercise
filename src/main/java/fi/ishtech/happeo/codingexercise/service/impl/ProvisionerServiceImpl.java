package fi.ishtech.happeo.codingexercise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ishtech.happeo.codingexercise.entity.OrgProvisioner;
import fi.ishtech.happeo.codingexercise.entity.Provisioner;
import fi.ishtech.happeo.codingexercise.mapper.ProvisionerMapper;
import fi.ishtech.happeo.codingexercise.payload.request.ProvisionerRequest;
import fi.ishtech.happeo.codingexercise.payload.response.ProvisionerResponse;
import fi.ishtech.happeo.codingexercise.repo.OrgProvisionerRepo;
import fi.ishtech.happeo.codingexercise.repo.ProvisionerRepo;
import fi.ishtech.happeo.codingexercise.security.jwt.JwtUtil;
import fi.ishtech.happeo.codingexercise.service.ProvisionerService;
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

	@Autowired
	private JwtUtil jwtUtil;

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

		String encodedSecret = createOrgProvisioner(organisationId, provisioner.getId());

		ProvisionerResponse provisionerResponse = provisionerMapper.toResponse(provisioner);
		provisionerResponse.setSecret(jwtUtil.decodeBase64(encodedSecret));

		return provisionerResponse;
	}

	private String createOrgProvisioner(Long organisationId, Long provisionerId) {
		OrgProvisioner orgProvisioner = new OrgProvisioner();
		orgProvisioner.setOrganisationId(organisationId);
		orgProvisioner.setProvisionerId(provisionerId);

		String encodedSecret = jwtUtil.generateEncodedSecretString();

		orgProvisioner.setSecret(encodedSecret);

		orgProvisioner = orgProvisionerRepo.save(orgProvisioner);
		log.debug("Created new OrgProvisioner({})", orgProvisioner.getId());

		return encodedSecret;
	}

}
