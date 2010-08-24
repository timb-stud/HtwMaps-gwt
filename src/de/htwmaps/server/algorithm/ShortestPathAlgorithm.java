package de.htwmaps.server.algorithm;

import de.htwmaps.shared.exceptions.PathNotFoundException;


public abstract class ShortestPathAlgorithm {
	GraphData graphData;
	
	public static final int MOTORWAY = 1;
	public static final int PRIMARY = 5;
	public static final int SECONDARY = 7;
	public static final int RESIDENTIAL = 10;
	public static final int ROAD = 11;
	public static final int LIVING_STREET = 13;
	private int motorwaySpeed = 130; //Autobahn
	private int primarySpeed = 80; //Landstraﬂe
	private int secondarySpeed = 60; //Ortsverbindung
	private int residentialSpeed = 45; //Innerorts
	private int roadSpeed = 50; //unclassified
	private int livingStreetSpeed = 5; //Spielstrasse
	
	private long buildNodesTime;
	private long buildEdgesTime;
	private long alorithmTime;
	
	
	public ShortestPathAlgorithm(GraphData gd) {
		if (gd == null) {
			throw new IllegalArgumentException("Graph data must not be null");
		}
		this.graphData = gd;
	}
	
	public abstract Node[] findShortestPath(int startNodeID, int goalNodeID) throws PathNotFoundException;
	
	public abstract Node[] findFastestPath(int startNodeID, 
									int goalNodeID, 
									int motorwaySpeed, 
									int primarySpeed,
									int secondarySpeed,
									int residentialSpeed,
									int roadSpeed,
									int livingStreetSpeed) throws PathNotFoundException;
	
	
	public abstract Node[] findFastestPath(int startNodeID, 
									int goalNodeID, 
									int motorwaySpeed, 
									int primarySpeed,
									int residentialSpeed) throws PathNotFoundException;

	public int getMotorwaySpeed() {
		return motorwaySpeed;
	}

	public void setMotorwaySpeed(int motorwaySpeed) {
		this.motorwaySpeed = motorwaySpeed;
	}

	public int getPrimarySpeed() {
		return primarySpeed;
	}

	public void setPrimarySpeed(int primarySpeed) {
		this.primarySpeed = primarySpeed;
	}

	public int getSecondarySpeed() {
		return secondarySpeed;
	}

	public void setSecondarySpeed(int secondarySpeed) {
		this.secondarySpeed = secondarySpeed;
	}

	public int getResidentialSpeed() {
		return residentialSpeed;
	}

	public void setResidentialSpeed(int residentialSpeed) {
		this.residentialSpeed = residentialSpeed;
	}

	public int getRoadSpeed() {
		return roadSpeed;
	}

	public void setRoadSpeed(int roadSpeed) {
		this.roadSpeed = roadSpeed;
	}

	public int getLivingStreetSpeed() {
		return livingStreetSpeed;
	}

	public void setLivingStreetSpeed(int livingStreetSpeed) {
		this.livingStreetSpeed = livingStreetSpeed;
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

	public long getAlorithmTime() {
		return alorithmTime;
	}

	public void setAlorithmTime(long alorithmTime) {
		this.alorithmTime = alorithmTime;
	}

	
	
}