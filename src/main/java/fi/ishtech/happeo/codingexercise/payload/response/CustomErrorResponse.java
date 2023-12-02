package fi.ishtech.happeo.codingexercise.payload.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Error Response
 *
 * @author Muneer Ahmed Syed
 */
@Data
@AllArgsConstructor(staticName = "of")
public class CustomErrorResponse implements Serializable {

	private static final long serialVersionUID = -5506330792596620199L;

	private Integer httpStatus;

	private String error;

}
