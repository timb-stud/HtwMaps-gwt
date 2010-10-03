package de.htwmaps.server.algorithm;

import java.util.LinkedList;


/**
 * @author Stanislaw Tartakowski
 * 
 *	Diese Klasse dient dem Bidirektionalem A* als Knoten im Graphen. Sie erweitert die Klasse Node um zusätzliche, für den bidirektionalen Algorithmus notwendige Daten.
 */
public class AStarBiNode extends Node {

	private double dist;								
	private volatile AStarBiNode predecessor; 					
	private volatile boolean removed, touchedByTh1, touchedByTh2;	
	private LinkedList<AStarEdge> edgeList;

	/**
	 * @param id unique node on earth
	 * @param y latitude
	 * @param x longitude
	 */
	public AStarBiNode(float x, float y, int id) {
		super(x, y, id);
		setDist(Double.MAX_VALUE);
		edgeList = new LinkedList<AStarEdge>();
	}
	
	
	/**
	 * 
	 * @param e Kante die auf den Knoten gesetzt wird
	 * @return false: fehler beim setzen
	 */
	public boolean addEdge(AStarBiEdge e){
		return edgeList.add(e);
	}
	
	/**
	 * @return liefert alle Kanten die von diesem Knoten ausgehen
	 */
	public LinkedList<AStarEdge> getEdgeList(){
		return edgeList;
	}
	/**
	 * 
	 * @return removed flag. Algorithm depending feature to optimize running time.
	 */
	public boolean isRemovedFromQ() {
		return removed;
	}

	/**
	 * 
	 * @param removed Algorithm depending feature to optimize running time.
	 */
	public void setRemovedFromQ(boolean removed) {
		this.removed = removed;
	}

	/**
	 * 
	 * @return whole distance to start node
	 */
	public double getDist() {
		return dist;
	}

	/**
	 * 
	 * @param dist new distance to start node
	 */
	public void setDist(double dist) {
		this.dist = dist;
	}

	/**
	 * @param new predecessor as a new item of the result set
	 */
	public void setPredeccessor(AStarBiNode predecessor) {
		this.predecessor = predecessor;
	}

	/**
	 * 
	 * @return first predecessor as last item of the result set
	 */
	public AStarBiNode getPredecessor() {
		return predecessor;
	}
	
	/**
	 * 
	 * @param touchedByTh1 indicator for the reverse running thread, that this node can be
	 * the connection between the two result sets
	 */
	public void setTouchedByTh1(boolean touchedByTh1) {
		this.touchedByTh1 = touchedByTh1;
	}

	/**
	 * 
	 * @return if this thread can be a connection node of the result sets
	 */
	public boolean isTouchedByTh1() {
		return touchedByTh1;
	}

	/**
	 * 
	 * @param touchedByTh2 watch setTouchedByTh1
	 */
	public void setTouchedByTh2(boolean touchedByTh2) {
		this.touchedByTh2 = touchedByTh2;
	}

	/**
	 * 
	 * @return watch isTouchedByTh1
	 */
	public boolean isTouchedByTh2() {
		return touchedByTh2;
	}
}
