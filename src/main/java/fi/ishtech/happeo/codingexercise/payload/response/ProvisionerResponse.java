package fi.ishtech.happeo.codingexercise.payload.response;

import java.io.Serializable;

import lombok.Data;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Data
public class ProvisionerResponse implements Serializable {

	private static final long serialVersionUID = -3632618989125743001L;

	private Long id;

	private String name;

	/**
	 * This is temporary solution to share the secret<br>
	 * It is not safe to share secret in API response
	 */
	private String secret;

}
