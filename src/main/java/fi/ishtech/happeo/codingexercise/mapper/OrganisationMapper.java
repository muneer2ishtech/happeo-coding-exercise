package fi.ishtech.happeo.codingexercise.mapper;

import org.mapstruct.Mapper;

import fi.ishtech.happeo.codingexercise.entity.Organisation;
import fi.ishtech.happeo.codingexercise.payload.response.OrganisationResponse;

/**
 * Mapper to convert Organisation entity to request/response payload
 *
 * @author Muneer Ahmed Syed
 */
@Mapper(componentModel = "spring")
public interface OrganisationMapper {

	OrganisationResponse toResponse(Organisation organisation);

}
