package de.htwmaps.shared.exceptions;

/**
 * Keine Orte in der Datenbank gefunden.
 * 
 * @author Thomas Altmeyer
 *
 */
public class NoCitiesFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoCitiesFoundException() {
	}

	public NoCitiesFoundException(String message) {
		super(message);
	}

	public NoCitiesFoundException(Throwable cause) {
		super(cause);
	}

	public NoCitiesFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
