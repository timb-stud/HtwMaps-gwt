package de.htwmaps.shared.exceptions;

/**
 * Eine Serialisierte Verision der {@link java.sql.SQLException}
 * 
 * @author Tim Bartsch
 *
 */
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
