package de.htwmaps.client.GUI.exceptions;

public class PrimarySpeedException extends Exception {

	private static final long serialVersionUID = 1L;

	public PrimarySpeedException() {
	}

	public PrimarySpeedException(String message) {
		super(message);
	}

	public PrimarySpeedException(Throwable cause) {
		super(cause);
	}

	public PrimarySpeedException(String message, Throwable cause) {
		super(message, cause);
	}

}
