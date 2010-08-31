package de.htwmaps.shared;

import java.io.Serializable;

public class AllPathData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long optToAllRuntime;
	private int allNodesNumber;
	private float[] lats;
	private float[] lons;
	
	
	public long getOptToAllRuntime() {
		return optToAllRuntime;
	}
	public void setOptToAllRuntime(long optToAllTime) {
		this.optToAllRuntime = optToAllTime;
	}
	public int getAllNodesNumber() {
		return allNodesNumber;
	}
	public void setAllNodesNumber(int allNodeNumber) {
		this.allNodesNumber = allNodeNumber;
	}
	public float[] getLats() {
		return lats;
	}
	public void setLats(float[] lats) {
		this.lats = lats;
	}
	public float[] getLons() {
		return lons;
	}
	public void setLons(float[] lons) {
		this.lons = lons;
	}
	
}
