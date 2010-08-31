package de.htwmaps.server.db;

import java.text.DecimalFormat;
import java.util.LinkedList;

import de.htwmaps.server.algorithm.AStarEdge;

public class StreetDetails {
	private String name;
	private String addition;
	private String state;
	private String city;
	private double distance;
	private String direction;
	private LinkedList<AStarEdge> edgeList;
	
	public StreetDetails(String streetname, String ref, String city, String state, double distance, LinkedList<AStarEdge> edgeList, String direction) {
		this.name = streetname;
		this.addition = ref;
		this.city = city;
		this.state = state;
		this.distance = distance;
		this.edgeList = new LinkedList<AStarEdge>();
		this.edgeList.addAll(edgeList);
		this.direction = direction;
	}
	
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		String text = df.format((distance / 1000)) + " km \t" + name + "\t" + addition + "\t" + city + "\t" + state;
		return text;
	}
	
	public String getName() {
		return name;
	}

	public String getAddition() {
		return addition;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public double getDistance() {
		return distance;
	}

	public LinkedList<AStarEdge> getEdgeList() {
		return this.edgeList;
	}

	public String getDirection() {
		return direction;
	}
}
