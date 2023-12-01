package fi.ishtech.happeo.codingexercise.payload;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NameVo implements Serializable {

	private static final long serialVersionUID = 7124893040014239287L;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

}
