package de.htwmaps.server.algorithm;



import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import de.htwmaps.server.utils.FibonacciHeap;

/**
 * @author Stanislaw Tartakowski
 * 
 *         This is a concurrent implementation of an graph search algorithm
 *         based on Dijkstra's. Depart from classic implementations this
 *         algorithm has a goal oriented heuristic similar to A*'s and is
 *         optimized for maximal speed performance. Though, this algorithm
 *         doesn't guarantee best possible solution, but a relatively good one.
 *         This class can only be reasonably used if the caller of this class
 *         remains sleeping until this class awakens him when the work is done.
 */
public class AStarBi extends Thread {
	protected volatile static boolean finished;
	protected volatile static AtomicInteger count = new AtomicInteger();
	private AStarBi dij;
	private boolean thread;
	private AStarBiNode startNode, endNode;
	private Object caller;
	private int nodesVisited;

	/**
	 * 
	 * @param Q
	 *            the container of all nodes of the graph
	 * @param startNode
	 *            destination
	 * @param endNode
	 *            goal
	 * @param thread
	 *            flags the threads to run from destination to goal (true) or
	 *            reverse (false)
	 * @param caller
	 *            the object who started this class and waits for its completion
	 */
	public AStarBi(AStarBiNode startNode, AStarBiNode endNode,
			boolean thread, Object caller) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.thread = thread;
		this.caller = caller;
	}

	/**
	 * thread entrance
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
			if (currentNode == endNode) {
				reactivateCaller();
				return;
			}
			currentNode.setRemovedFromQ(true);
			LinkedList<Edge> edges = currentNode.getEdgeList();
			for (Edge edge : edges) {
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

	private void updateSuccDist(FibonacciHeap Q, AStarBiNode currentNode, AStarBiNode successor, double dist) {
		double alternative = currentNode.getDist() + dist;

		if (alternative < successor.getDist()) {
			successor.setDist(alternative);
			successor.setPredecessor(currentNode);
			touch(successor);
			nodesVisited++;
			if (Q.contains(successor)) {
				Q.decreaseKey(successor, alternative + potential(successor));
			} else {
				Q.add(successor, alternative + potential(successor));
			}
		}
	}

	private double potential(AStarBiNode node) {
		return node.getDistanceTo(endNode);
	}

	/**
	 * Sets a touched mark on the current node as a hint for the threads whether
	 * the node has been analyzed before
	 */
	private void touch(AStarBiNode node) {
		if (thread) {
			node.setTouchedByTh1(true);
		} else {
			node.setTouchedByTh2(true);
		}
	}

	/**
	 * Checks whether the current thread may build the result and finish the
	 * algorithm
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
	 * Builds the result. Can be entered only once.
	 */
	private void concantenate(AStarBiNode currentNode, AStarBiNode successor) {
		AStarBiNode tmp;
		while (successor != null) {
			tmp = successor.getPredecessor();
			successor.setPredecessor(currentNode);
			currentNode = successor;
			successor = tmp;
		}
		reactivateCaller();
	}

	/**
	 * wakes up every thread thread, waiting on caller's class
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