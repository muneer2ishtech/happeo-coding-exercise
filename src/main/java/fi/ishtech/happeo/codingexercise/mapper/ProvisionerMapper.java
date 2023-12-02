package fi.ishtech.happeo.codingexercise.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import fi.ishtech.happeo.codingexercise.entity.Provisioner;
import fi.ishtech.happeo.codingexercise.payload.response.ProvisionerResponse;

/**
 * Mapper to convert Provisioner entity to request/response payload
 *
 * @author Muneer Ahmed Syed
 */
@Mapper(componentModel = "spring")
public interface ProvisionerMapper {

	ProvisionerResponse toResponse(Provisioner provisioner);

	List<ProvisionerResponse> toResponse(List<Provisioner> provisioner);

}
