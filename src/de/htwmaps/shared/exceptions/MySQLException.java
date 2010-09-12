package de.htwmaps.shared.exceptions;

/**
 * Es trat ein Fehler mit dem My SQL Server auf.
 * @author Tim Bartsch
 *
 */
public class MySQLException extends Exception {

	private static final long serialVersionUID = 1L;

	public MySQLException() {
	}

	public MySQLException(String arg0) {
		super(arg0);
	}

	public MySQLException(Throwable arg0) {
		super(arg0);
	}

	public MySQLException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
