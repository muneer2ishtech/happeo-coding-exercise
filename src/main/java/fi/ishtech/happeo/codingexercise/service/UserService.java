package fi.ishtech.happeo.codingexercise.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import fi.ishtech.happeo.codingexercise.entity.User;
import fi.ishtech.happeo.codingexercise.payload.request.UserProvisioningRequest;
import fi.ishtech.happeo.codingexercise.payload.response.UserProvisioningResponse;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface UserService {

	UserProvisioningResponse create(Long organisationId, Long provisionerId,
			UserProvisioningRequest userProvisioningRequest);

	void updateAsActive(Long organisationId, List<Long> userIds);

	Page<UserProvisioningResponse> findAllAndMapToResponse(Specification<User> spec, Pageable pageable);

}
