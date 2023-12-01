package fi.ishtech.happeo.codingexercise.payload.response;

import java.io.Serializable;

import fi.ishtech.happeo.codingexercise.payload.NameVo;
import lombok.Data;

/**
 * The response expected by external identity system
 */
@Data
public class UserProvisioningResponse implements Serializable {

	private static final long serialVersionUID = -8012225876107492951L;

	private String email;

	private NameVo name;

	/**
	 * id in external identity system
	 */
	private String id;

	/**
	 * id in Happeo
	 */
	private String applicationId;

}
