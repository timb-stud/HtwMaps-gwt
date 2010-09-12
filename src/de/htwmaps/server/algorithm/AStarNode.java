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
	private LinkedList<AStarEdge> edgeList;


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
		edgeList = new LinkedList<AStarEdge>();
	}
	
	/**
	 * Adds an Edge to this Node.
	 * @param e Edge
	 * @return true (as specified by Collection.add(E))
	 */
	public boolean addEdge(AStarEdge e){
		return edgeList.add(e);
	}
	
	/**
	 * Returns all outgoing Edges
	 * @return LinkedList<AStarEdge> with all outgoing Edges
	 */
	public LinkedList<AStarEdge> getEdgeList(){
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

	/**
	 * Returns predeccessor which is necessary to reconstruct the path.
	 * 
	 * @return AStarNode
	 */
	public AStarNode getPredeccessor() {
		return predeccessor;
	}

	/**
	 * Sets predeccessor which is necessary to reconstruct the path.
	 * 
	 * @param predeccessor
	 */
	public void setPredeccessor(AStarNode predeccessor) {
		this.predeccessor = predeccessor;
	}
}
