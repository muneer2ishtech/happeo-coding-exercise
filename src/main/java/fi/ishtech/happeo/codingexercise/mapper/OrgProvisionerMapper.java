package fi.ishtech.happeo.codingexercise.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import fi.ishtech.happeo.codingexercise.entity.OrgProvisioner;
import fi.ishtech.happeo.codingexercise.payload.response.OrgProvisionerResponse;

/**
 * Mapper to convert OrgProvisioner entity to request/response payload
 *
 * @author Muneer Ahmed Syed
 */
@Mapper(componentModel = "spring", uses = { OrganisationMapper.class, ProvisionerMapper.class })
public interface OrgProvisionerMapper {

	@BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
	OrgProvisionerResponse toResponse(OrgProvisioner orgProvisioner);

}
