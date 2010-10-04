package de.htwmaps.shared;

import java.io.Serializable;


/**
 * Enthaellt alle Daten der optimierten Route.
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 *
 */
public class OptPathData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private float[] nodeLats;
	private float[] nodeLons;
	private String[] destinations;
	private long selectNodesRuntime;
	private long selectEdgesRuntime;
	private long buildNodesRuntime;
	private long buildEdgesRuntime;
	private long algorithmRuntime;
	private int selectedNodesNumber;
	private int selectedEdgesNumber;
	private int optNodesNumber;
	private float[][] closedLatLons;
	
	
	public OptPathData() {}

	public void setNodeLats(float[] nodeLats) {
		this.nodeLats = nodeLats;
	}

	public void setNodeLons(float[] nodeLons) {
		this.nodeLons = nodeLons;
	}

	public void setDestinations(String[] destinations) {
		this.destinations = destinations;
	}
	
	public float[] getNodeLats() {
		return nodeLats;
	}

	public float[] getNodeLons() {
		return nodeLons;
	}
	
	public String[] getDestinations() {
		return destinations;
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

	public void setClosedLatLons(float[][] closedLatLons) {
		this.closedLatLons = closedLatLons;
	}

	public float[][] getClosedLatLons() {
		return closedLatLons;
	}
	
}
