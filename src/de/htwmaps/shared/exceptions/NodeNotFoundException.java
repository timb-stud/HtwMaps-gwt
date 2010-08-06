package de.htwmaps.shared.exceptions;

/**
 * 
 * @author Tim Bartsch
 *
 */

public class NodeNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NodeNotFoundException() {
	}

	public NodeNotFoundException(String message) {
		super(message);
	}

	public NodeNotFoundException(Throwable cause) {
		super(cause);
	}

	public NodeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
