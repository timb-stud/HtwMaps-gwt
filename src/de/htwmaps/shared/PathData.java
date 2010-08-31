package de.htwmaps.shared;

import java.io.Serializable;
import java.util.LinkedList;



public class PathData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private float[] nodeLats;
	private float[] nodeLons;
	private long receiveNodesTime;
	private long receiveEdgesTime;
	private long buildNodesTime;
	private long buildEdgesTime;
	private long alorithmTime;
	private long optToAllTime;
	private long routeToTextTime;
	private int edgesCount;
	private int nodesCount;
	private int optNodesResultCount;
	private int allNodesResultCount;
	private LinkedList<String> description;
	
	
	
	public PathData() {}

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

	public long getReceiveNodesTime() {
		return receiveNodesTime;
	}

	public void setReceiveNodesTime(long receiveNodesTime) {
		this.receiveNodesTime = receiveNodesTime;
	}

	public long getReceiveEdgesTime() {
		return receiveEdgesTime;
	}

	public void setReceiveEdgesTime(long receiveEdgesTime) {
		this.receiveEdgesTime = receiveEdgesTime;
	}

	public long getBuildNodesTime() {
		return buildNodesTime;
	}

	public void setBuildNodesTime(long buildNodesTime) {
		this.buildNodesTime = buildNodesTime;
	}

	public long getBuildEdgesTime() {
		return buildEdgesTime;
	}

	public void setBuildEdgesTime(long buildEdgesTime) {
		this.buildEdgesTime = buildEdgesTime;
	}

	public int getEdgesCount() {
		return edgesCount;
	}

	public void setEdgesCount(int edgesCount) {
		this.edgesCount = edgesCount;
	}

	public int getNodesCount() {
		return nodesCount;
	}

	public void setNodesCount(int nodesCount) {
		this.nodesCount = nodesCount;
	}
	
	public long getAlorithmTime() {
		return alorithmTime;
	}
	
	public void setAlorithmTime(long alorithmTime) {
		this.alorithmTime = alorithmTime;
	}

	public void setOptToAllTime(long optToAllTime) {
		this.optToAllTime = optToAllTime;
	}

	public long getOptToAllTime() {
		return optToAllTime;
	}

	public void setOptNodesResultCount(int optNodesResultCount) {
		this.optNodesResultCount = optNodesResultCount;
	}

	public int getOptNodesResultCount() {
		return optNodesResultCount;
	}

	public void setAllNodesResultCount(int allNodesResultCount) {
		this.allNodesResultCount = allNodesResultCount;
	}

	public int getAllNodesResultCount() {
		return allNodesResultCount;
	}

	public void setDescription(LinkedList<String> discription) {
		this.description = discription;
	}

	public LinkedList<String> getDescription() {
		return description;
	}

	public void setRouteToTextTime(long routeToTextTime) {
		this.routeToTextTime = routeToTextTime;
	}

	public long getRouteToTextTime() {
		return routeToTextTime;
	}
}
