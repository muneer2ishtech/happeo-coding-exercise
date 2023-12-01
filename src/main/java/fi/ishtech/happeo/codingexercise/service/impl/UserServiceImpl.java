package fi.ishtech.happeo.codingexercise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ishtech.happeo.codingexercise.entity.OrgProvisioner;
import fi.ishtech.happeo.codingexercise.entity.User;
import fi.ishtech.happeo.codingexercise.mapper.UserMapper;
import fi.ishtech.happeo.codingexercise.payload.request.UserProvisioningRequest;
import fi.ishtech.happeo.codingexercise.payload.response.UserProvisioningResponse;
import fi.ishtech.happeo.codingexercise.repo.OrgProvisionerRepo;
import fi.ishtech.happeo.codingexercise.repo.UserRepo;
import fi.ishtech.happeo.codingexercise.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private OrgProvisionerRepo orgProvisionerRepo;

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserProvisioningResponse create(Long orgnisationId, Long provisionerId,
			UserProvisioningRequest userProvisioningRequest) {
		User user = userMapper.toEntity(userProvisioningRequest);
		user.setOrganisationId(orgnisationId);

		user = userRepo.save(user);
		log.debug("Created new User({})", user.getId());

		createOrgProvisioner(orgnisationId, provisionerId);

		return userMapper.toUserProvisioningResponse(user);
	}

	@Override
	public void updateAsActive(Long organisationId, List<Long> userIds) {
		int result = userRepo.updateAsActive(organisationId, userIds);
		log.debug("Acivated {} users of Organisation {}", result, organisationId);
	}

	private void createOrgProvisioner(Long orgnisationId, Long provisionerId) {
		OrgProvisioner orgProvisioner = new OrgProvisioner();
		orgProvisioner.setOrganisationId(orgnisationId);
		orgProvisioner.setProvisionerId(provisionerId);
		orgProvisioner.setSecret("TODO");

		orgProvisioner = orgProvisionerRepo.save(orgProvisioner);
		log.debug("Created new OrgProvisioner({})", orgProvisioner.getId());
	}

}
