package fi.ishtech.happeo.codingexercise.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ishtech.happeo.codingexercise.entity.OrgProvisioner;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface OrgProvisionerRepo extends JpaRepository<OrgProvisioner, Long> {

	boolean existsByOrganisationIdAndProvisionerId(Long organisationId, Long provisionerId);

	Optional<OrgProvisioner> findByOrganisationIdAndProvisionerId(Long organisationId, Long provisionerId);

}
