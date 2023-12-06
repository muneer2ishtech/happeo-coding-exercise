package fi.ishtech.happeo.codingexercise.payload.response;

import java.io.Serializable;

import lombok.Data;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Data
public class OrgProvisionerResponse implements Serializable {

	private static final long serialVersionUID = -6028445160132978886L;

	private Long id;

	private OrganisationResponse organisation;

	private ProvisionerResponse provisioner;

	/**
	 * This is temporary solution to share the secret<br>
	 * It is not safe to share secret in API response<br>
	 *
	 * Base64 encoded secret string
	 */
	private String encodedSecret;

	/**
	 * This is temporary solution to share the secret<br>
	 * It is not safe to share secret in API response<br>
	 *
	 * Straight (Base64 decoded) secret string
	 */
	private String decodedSecret;

}
