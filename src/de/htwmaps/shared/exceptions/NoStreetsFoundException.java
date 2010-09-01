package de.htwmaps.shared.exceptions;

public class NoStreetsFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoStreetsFoundException() {
	}

	public NoStreetsFoundException(String arg0) {
		super(arg0);
	}

	public NoStreetsFoundException(Throwable arg0) {
		super(arg0);
	}

	public NoStreetsFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
