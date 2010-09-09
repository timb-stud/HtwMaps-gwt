package de.htwmaps.client.GUI.exceptions;

public class MotorWaySpeedException extends Exception {

	private static final long serialVersionUID = 1L;

	public MotorWaySpeedException() {
	}

	public MotorWaySpeedException(String message) {
		super(message);
	}

	public MotorWaySpeedException(Throwable cause) {
		super(cause);
	}

	public MotorWaySpeedException(String message, Throwable cause) {
		super(message, cause);
	}

}
