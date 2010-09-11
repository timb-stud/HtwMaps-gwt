package de.htwmaps.client.GUI.exceptions;

/**
 * Wird geworfen wenn die Innerortsgeschwindigkeit kein realistischer Wert ist.
 * 
 * @author Tim Bartsch
 *
 */
public class ResidentialSpeedException extends Exception {

	private static final long serialVersionUID = 1L;

	public ResidentialSpeedException() {
	}

	public ResidentialSpeedException(String message) {
		super(message);
	}

	public ResidentialSpeedException(Throwable cause) {
		super(cause);
	}

	public ResidentialSpeedException(String message, Throwable cause) {
		super(message, cause);
	}

}
