package fi.ishtech.happeo.codingexercise.payload.response;

import java.io.Serializable;

import lombok.Data;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Data
public class OrganisationResponse implements Serializable {

	private static final long serialVersionUID = 5097262016957765498L;

	private Long id;

	private String name;

}
