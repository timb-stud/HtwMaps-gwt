package de.htwmaps.shared;

import java.io.Serializable;



public class PathData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	float[] nodeLats;
	float[] nodeLons;
	
	public PathData() {
	}

	public void setNodeLats(float[] nodeLats) {
		this.nodeLats = nodeLats;
	}

	public void setNodeLons(float[] nodeLons) {
		this.nodeLons = nodeLons;
	}

	public float[] getNodeLats() {
		return nodeLats;
	}

	public float[] getNodeLons() {
		return nodeLons;
	}
	
}
