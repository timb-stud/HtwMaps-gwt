package de.htwmaps.shared;

import java.io.Serializable;

public class PathDescription implements Serializable{
	private static final long serialVersionUID = 1L;
	private long runtime;
	private String description;

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setRuntime(long runtime) {
		this.runtime = runtime;
	}

	public long getRuntime() {
		return runtime;
	}
	
	
}
