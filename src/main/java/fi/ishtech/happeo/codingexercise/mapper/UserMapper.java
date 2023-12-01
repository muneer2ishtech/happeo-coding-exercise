package fi.ishtech.happeo.codingexercise.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fi.ishtech.happeo.codingexercise.entity.User;
import fi.ishtech.happeo.codingexercise.payload.request.UserProvisioningRequest;
import fi.ishtech.happeo.codingexercise.payload.response.UserProvisioningResponse;

/**
 * Mapper to convert Provisioner entity to request/response payload
 *
 * @author Muneer Ahmed Syed
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

	@BeanMapping(ignoreByDefault = true)
	@Mapping(source = "email", target = "email")
	@Mapping(source = "name.firstName", target = "firstName")
	@Mapping(source = "name.lastName", target = "lastName")
	@Mapping(source = "id", target = "externalId")
	@Mapping(target = "active", constant = "false")
	User toEntity(UserProvisioningRequest userProvisioningRequest);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(source = "email", target = "email")
	@Mapping(source = "firstName", target = "name.firstName")
	@Mapping(source = "lastName", target = "name.lastName")
	@Mapping(source = "externalId", target = "id")
	@Mapping(source = "id", target = "applicationId")
	UserProvisioningResponse toUserProvisioningResponse(User user);

}
