package fi.ishtech.happeo.codingexercise.exception;

/**
 * Exception if Organisation or Provisioner are not present
 *
 * @author Muneer Ahmed Syed
 */
public class MissingOrganisationProvisionerException extends RuntimeException {

	private static final long serialVersionUID = 6566496493332976803L;

	public MissingOrganisationProvisionerException(String message) {
		super(message);
	}

}
