package de.htwmaps.server.algorithm;

/**
 * 
 * @author Stanislaw Tartakowski, Tim Bartsch
 *	Diese Klasse ist eine Kante zwischen 2 Knoten im Graphen
 */
public class AStarEdge {
	public static final int MOTORWAY_ID 		= 1;
	public static final int PRIMARY_ID 			= 5;
	public static final int SECONDARY_ID 		= 7;
	public static final int RESIDENTIAL_ID 		= 10;
	public static final int ROAD_ID 			= 11;
	public static final int LIVING_STREET_ID 	= 13;
	
	public static final int MOTORWAY_SPEED 		= 100; 	//Autobahn
	public static final int PRIMARY_SPEED 		= 70; 	//Landstraﬂe
	public static final int SECONDARY_SPEED 	= 60; 	//Ortsverbindung
	public static final int RESIDENTIAL_SPEED 	= 40; 	//Innerorts
	public static final int ROAD_SPEED 			= 50; 	//unclassified
	public static final int LIVING_STREET_SPEED = 5; 	//Spielstrasse
	
	private Node successor;
	private double length;
	private int highwayType;
	private int wayID;
	private int speed;
	private int id;

	/**
	 * 
	 * @param successor der Knoten auf den die Kante gerichtet ist
	 * @param length laenge der Kante
	 * @param speed 
	 */
	public AStarEdge(Node successor, double length, int highwayType, int wayID, int speed, int id) {
		this.successor = successor;
		this.length = length;
		this.highwayType = highwayType;
		this.wayID = wayID;
		this.speed = speed;
		this.id = id;
	}
	
	public int getSpeed() {
		if(speed == 1){
			switch (highwayType) {
			case MOTORWAY_ID:
				return MOTORWAY_SPEED;
			case PRIMARY_ID:
				return PRIMARY_SPEED;
			case SECONDARY_ID:
				return SECONDARY_SPEED;
			case RESIDENTIAL_ID:
				return RESIDENTIAL_SPEED;
			case ROAD_ID:
				return ROAD_SPEED;
			case LIVING_STREET_ID:
				return LIVING_STREET_SPEED;
			default:
				throw new RuntimeException("highwayType: " + highwayType + " is no defined ID");
			}
		}
		else
			return speed;
	}
	
	public int getWayID() {
		return wayID;
	}
	
	public int getHighwayType() {
		return highwayType;
	}
	
	/**
	 * @return der Knoten, auf den die Kante zeigt
	 */
	public Node getSuccessor() {
		return successor;
	}

	/**
	 * 
	 * @return Laenge der Kante
	 */
	public double getLenght() {
		return length;
	}

	@Override
	public String toString() {
		return "[" + successor + "; " + length + "]";
	}

	public double getPrioLength() {
		return length / speed;
	}

	
	public int getID() {
		return id;
	}
}
