package fi.ishtech.happeo.codingexercise.payload.request;

import java.io.Serializable;

import fi.ishtech.happeo.codingexercise.payload.NameVo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * The schema of the user entry as described by the external identity system
 */
@Data
public class UserProvisioningRequest implements Serializable {

	private static final long serialVersionUID = 2384954746607514263L;

	@NotBlank
	@Email
	private String email;

	@NotNull
	@Valid
	private NameVo name;

	/**
	 * id in external identity system
	 */
	@NotBlank
	private String id;

}
