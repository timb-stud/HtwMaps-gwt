package de.htwmaps.shared;

import java.io.Serializable;



public class OptPathData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private float[] nodeLats;
	private float[] nodeLons;
	private long selectNodesRuntime;
	private long selectEdgesRuntime;
	private long buildNodesRuntime;
	private long buildEdgesRuntime;
	private long algorithmRuntime;
	private int selectedNodesNumber;
	private int selectedEdgesNumber;
	private int optNodesNumber;
	
	
	
	public OptPathData() {}

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

	public long getSelectNodesRuntime() {
		return selectNodesRuntime;
	}

	public void setSelectNodesRuntime(long selectNodesRuntime) {
		this.selectNodesRuntime = selectNodesRuntime;
	}

	public long getSelectEdgesRuntime() {
		return selectEdgesRuntime;
	}

	public void setSelectEdgesRuntime(long selectEdgesRuntime) {
		this.selectEdgesRuntime = selectEdgesRuntime;
	}

	public long getBuildNodesRuntime() {
		return buildNodesRuntime;
	}

	public void setBuildNodesRuntime(long buildNodesRuntime) {
		this.buildNodesRuntime = buildNodesRuntime;
	}

	public long getBuildEdgesRuntime() {
		return buildEdgesRuntime;
	}

	public void setBuildEdgesRuntime(long buildEdgesRuntime) {
		this.buildEdgesRuntime = buildEdgesRuntime;
	}

	public long getAlgorithmRuntime() {
		return algorithmRuntime;
	}

	public void setAlgorithmRuntime(long algorithmRuntime) {
		this.algorithmRuntime = algorithmRuntime;
	}

	public int getSelectedNodesNumber() {
		return selectedNodesNumber;
	}

	public void setSelectedNodesNumber(int selectedNodesNumber) {
		this.selectedNodesNumber = selectedNodesNumber;
	}

	public int getSelectedEdgesNumber() {
		return selectedEdgesNumber;
	}

	public void setSelectedEdgesNumber(int selectedEdgesNumber) {
		this.selectedEdgesNumber = selectedEdgesNumber;
	}

	public int getOptNodesNumber() {
		return optNodesNumber;
	}

	public void setOptNodesNumber(int optNodesNumber) {
		this.optNodesNumber = optNodesNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
