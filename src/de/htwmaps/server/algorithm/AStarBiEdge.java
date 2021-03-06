package de.htwmaps.server.algorithm;

/**
 * 
 * @author bline
 *	Diese Klasse dient dem Bidirektionalem A* als Kante im Graphen. Sie erweitert die AStarEdge um zusätzliche, für den bidirektionalen Algorithmus notwendige Daten.
 */
public class AStarBiEdge extends AStarEdge {
	private Node predecessor;
	private boolean isOneway;
	
	public AStarBiEdge(Node successor, double length, int highwayType, int wayID, int speed, int id) {
		super(successor, length, highwayType, wayID, speed, id);
	}
	
	public void setOneway(boolean oneway) {
		this.isOneway = oneway;
	}

	/**
	 * @return der Knoten, vom dem aus die Kante ausgeht. Ein Indiz fuer beidseitiges Betreten der Kante
	 */
	public Node getPredecessor() {
		return predecessor;
	}
	
	/**
	 * 
	 * @param predecessor der Knoten, vom dem aus die Kante ausgeht. Ein Indiz fuer beidseitiges Betreten der Kante
	 */
	public void setPredecessor(Node predecessor) {
		this.predecessor = predecessor;
	}
	

	public boolean isOneway() {
		return isOneway;
	}

}
