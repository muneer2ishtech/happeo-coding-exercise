package fi.ishtech.happeo.codingexercise.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ishtech.happeo.codingexercise.entity.Provisioner;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface ProvisionerRepo extends JpaRepository<Provisioner, Long> {

	Provisioner findOneByNameIgnoreCase(String name);

}
