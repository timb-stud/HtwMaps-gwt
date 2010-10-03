package de.htwmaps.server.algorithm;



import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import de.htwmaps.server.algorithm.utils.FibonacciHeap;

/**
 * @author Stanislaw Tartakowski
 * 		Dies ist eine konkurrierende Implementierung eines Suchalgorithmus in einem zusammenhängend Graphen.
 * 		Der Algorithmus basiert auf der Idee des Dijkstra-Suchalgorithmus und erweitert diesen um eine
 * 		zielorientierte Heuristik.
 * 		Die Implementierung ist nicht deterministisch. Das Resultat ist jedoch nie "schlecht"
 */
public class AStarBi extends Thread {
	protected volatile static boolean finished;
	protected volatile static AtomicInteger count = new AtomicInteger();
	private AStarBi dij;
	private boolean thread;
	private AStarBiNode startNode, endNode;
	private Object caller;
	private int nodesVisited;
	private double maxSpeed;

	/**
	 * 
	 * @param startNode Startknoten
	 * @param endNode Endknoten
	 * @param thread der jeweilige Suchthread
	 * @param caller der Starter dieses Objektes.
	 */
	public AStarBi(AStarBiNode startNode, AStarBiNode endNode,
			boolean thread, Object caller) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.thread = thread;
		this.caller = caller;
		maxSpeed = getMaxSpeed(AStarEdge.LIVING_STREET_SPEED, AStarEdge.MOTORWAY_SPEED, AStarEdge.PRIMARY_SPEED, AStarEdge.RESIDENTIAL_SPEED, AStarEdge.ROAD_SPEED, AStarEdge.SECONDARY_SPEED);
		System.out.println(maxSpeed);
	}

	/**
	 * Threadeingang und Hauptschleife
	 */
	@Override
	public void run() {
		FibonacciHeap Q = new FibonacciHeap();
		startNode.setDist(0.0);
		touch(startNode);
		Q.add(startNode, potential(startNode));
		nodesVisited++;
		while (Q.size() > 0) {
			AStarBiNode currentNode = (AStarBiNode) Q.popMin();
			System.out.println(currentNode.getLat()+"\t"+currentNode.getLon()+"\t"+"title\t"+"descr\t"+"rosa_punkt.png\t"+"8,8\t"+"0,0");
			if (currentNode == endNode) {
				reactivateCaller();
				return;
			}
			currentNode.setRemovedFromQ(true);
			LinkedList<AStarEdge> edges = currentNode.getEdgeList();
			for (AStarEdge edge : edges) {
				AStarBiNode successor = (AStarBiNode) edge.getSuccessor();
				if (!thread && successor == currentNode || thread && successor != currentNode || !((AStarBiEdge) edge).isOneway()) {
					if (successor == currentNode) {
						successor = (AStarBiNode) ((AStarBiEdge) edge).getPredecessor();
					}
					if (!thread && successor.isTouchedByTh1() || thread && successor.isTouchedByTh2() || !successor.isRemovedFromQ() || finished || isInterrupted()) {
						synchronized (getClass()) {
							if (finished || isInterrupted()) {
								return;
							}
							if (checkForCommonNode(currentNode, successor)) {
								return;
							}
							if (!successor.isRemovedFromQ()) {
								updateSuccDist(Q, currentNode, successor, edge.getPrioLength());
							}
						}
					}
				}
			}
		}
		if (count.incrementAndGet() == 2) {
			reactivateCaller();
			return;
		}
	}

	/**
	 * 
	 * @param Q Behälter mit allen momentan zu bearbeitenden Knoten
	 * @param currentNode der aktuell bearbeitende knoten
	 * @param successor der aktuell ausgewählte Folgeknoten
	 * @param dist die distanz zwischen aktuellem Knoten und Folgeknoten
	 */
	private void updateSuccDist(FibonacciHeap Q, AStarBiNode currentNode, AStarBiNode successor, double dist) {
		double alternative = currentNode.getDist() + dist;

		if (!Q.contains(successor)) {
			successor.setPredeccessor(currentNode);
			successor.setDist(alternative);
			touch(successor);
			nodesVisited++;
			Q.add(successor, alternative + potential(successor));
		} else {
			if (alternative < successor.getDist()) {
				successor.setPredeccessor(currentNode);
				successor.setDist(alternative);
				Q.decreaseKey(successor, alternative + potential(successor));
			}
		}
	}

	/**
	 * Heuristik. Die Distanz zum Endknoten wird als Auswahlkritärium benutzt
	 * @param node der aktuelle Knoten
	 * @return	Distanz zum Endknoten
	 */
	private double potential(AStarBiNode node) {
		return node.getDistanceTo(endNode)/maxSpeed;
	}


	private double getMaxSpeed(int... values) {
		int max = 0;
		for (int i : values) {
			if (max <= i) {
				max = i;
			}
		}
		return max;
	}

	/**
	 * Setzt ein Hinweis auf jeden Knoten, dass dieser von dem jeweiligen Thread bearbeitet wurde oder wird.
	 * @param node der aktuelle Knoten
	 */
	private void touch(AStarBiNode node) {
		if (thread) {
			node.setTouchedByTh1(true);
		} else {
			node.setTouchedByTh2(true);
		}
	}

	/**
	 * Prüfen, ob der aktuelle Thread den Algorithmus beenden kann, da ein Knoten eines anderen Threads gefunden wurde.
	 */
	private boolean checkForCommonNode(AStarBiNode currentNode, AStarBiNode successor) {
		if (!thread && successor.isTouchedByTh1() || thread && successor.isTouchedByTh2()) {
			if (dij.getNodesVisited() > nodesVisited) {
				concantenate(successor, currentNode);
			} else {
				concantenate(currentNode, successor);
			}
			return true;
		}
		return false;
	}

	/**
	 * Zusammenfügen der Resultete der beiden Threads zu einem Resultat
	 */
	private void concantenate(AStarBiNode currentNode, AStarBiNode successor) {
		AStarBiNode tmp;
		while (successor != null) {
			tmp = successor.getPredecessor();
			successor.setPredeccessor(currentNode);
			currentNode = successor;
			successor = tmp;
		}
		reactivateCaller();
	}

	/**
	 * Aufwecken des Starters, diese Methode kann nur einmal betreten werden.
	 */
	private void reactivateCaller() {
		synchronized (caller.getClass()) {
			if (!finished && !isInterrupted()) {
				finished = true;
				caller.getClass().notifyAll();
			}
		}
	}

	@Override
	public String toString() {
		return getName();
	}

	public void setDijkstra(AStarBi dij) {
		this.setDij(dij);
	}

	public int getNodesVisited() {
		return nodesVisited;
	}

	public void setDij(AStarBi dij) {
		this.dij = dij;
	}

	public AStarBi getDij() {
		return dij;
	}

}