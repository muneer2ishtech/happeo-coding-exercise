package fi.ishtech.happeo.codingexercise.payload.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Data
public class ProvisionerRequest implements Serializable {

	private static final long serialVersionUID = -7042267222063916657L;

	@NotBlank
	private String name;

}
