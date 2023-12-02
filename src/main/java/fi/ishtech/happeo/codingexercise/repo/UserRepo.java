package fi.ishtech.happeo.codingexercise.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import fi.ishtech.happeo.codingexercise.entity.User;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface UserRepo extends JpaRepository<User, Long> {

	@Modifying
	@Query("UPDATE User u SET u.isActive = true WHERE organisationId = :organisationId AND u.id IN :userIds")
	int updateAsActive(Long organisationId, List<Long> userIds);

	Page<User> findAll(Specification<User> specification, Pageable pageable);

}
