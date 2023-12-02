package fi.ishtech.happeo.codingexercise.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The response object for User entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends UserProvisioningResponse {

	private static final long serialVersionUID = -6302406437610098432L;

	@JsonProperty("isActive")
	private boolean isActive;

}
