package de.htwmaps.shared.exceptions;

public class SQLException extends Exception {

	private static final long serialVersionUID = 1L;

	public SQLException() {
	}

	public SQLException(String arg0) {
		super(arg0);
	}

	public SQLException(Throwable arg0) {
		super(arg0);
	}

	public SQLException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
