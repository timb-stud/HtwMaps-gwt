package de.htwmaps.server.algorithm;

import java.util.LinkedList;


/**
 * Node representation used by A Star
 * @author Tim Bartsch
 *
 */
public class AStarNode extends Node{
	private double f; // h + g
	private double g; // length from start Node to this node
	private AStarNode predeccessor;
	private LinkedList<Edge> edgeList;


	/**
	 * Constructs a Node with the given parameters.
	 * @param id an unique identification number
	 * @param lon latitude
	 * @param lat longitude
	 */
	public AStarNode(int id, float lon, float lat) {
		super(lon, lat, id);
		this.f = -1;
		this.g = -1;
		edgeList = new LinkedList<Edge>();
	}
	
	/**
	 * 
	 * @param e Kante die auf den Knoten gesetzt wird
	 * @return false: fehler beim setzen
	 */
	public boolean addEdge(Edge e){
		return edgeList.add(e);
	}
	
	/**
	 * @return liefert alle Kanten die von diesem Knoten ausgehen
	 */
	public LinkedList<Edge> getEdgeList(){
		return edgeList;
	}

	public double getF() {
		return f;
	}

	public void setF(double f) {
		this.f = f;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public AStarNode getPredeccessor() {
		return predeccessor;
	}

	public void setPredeccessor(AStarNode predeccessor) {
		this.predeccessor = predeccessor;
	}
}
