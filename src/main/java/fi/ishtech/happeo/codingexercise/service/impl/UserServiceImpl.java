package fi.ishtech.happeo.codingexercise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import fi.ishtech.happeo.codingexercise.entity.User;
import fi.ishtech.happeo.codingexercise.exception.MissingOrganisationProvisionerException;
import fi.ishtech.happeo.codingexercise.mapper.UserMapper;
import fi.ishtech.happeo.codingexercise.payload.request.UserProvisioningRequest;
import fi.ishtech.happeo.codingexercise.payload.response.UserProvisioningResponse;
import fi.ishtech.happeo.codingexercise.payload.response.UserResponse;
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
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private OrgProvisionerRepo orgProvisionerRepo;

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserProvisioningResponse create(Long organisationId, Long provisionerId,
			UserProvisioningRequest userProvisioningRequest) {
		if (!orgProvisionerRepo.existsByOrganisationIdAndProvisionerId(organisationId, provisionerId)) {
			throw new MissingOrganisationProvisionerException(
					"Organisation and Provisioner combination is not present");
		}

		User user = userMapper.toEntity(userProvisioningRequest);
		user.setOrganisationId(organisationId);

		user = userRepo.save(user);
		log.debug("Created new User({})", user.getId());

		return userMapper.toUserProvisioningResponse(user);
	}

	@Override
	public void updateAsActive(Long organisationId, List<Long> userIds) {
		int result = userRepo.updateAsActive(organisationId, userIds);
		log.debug("Acivated {} users of Organisation {}", result, organisationId);
	}

	private Page<User> findAll(Specification<User> spec, Pageable pageable) {
		return userRepo.findAll(spec, pageable);
	}

	@Override
	public Page<UserResponse> findAllAndMapToResponse(Specification<User> spec, Pageable pageable) {
		return this.findAll(spec, pageable).map(userMapper::toUserResponse);
	}

}
