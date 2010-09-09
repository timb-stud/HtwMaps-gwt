package de.htwmaps.server.algorithm;
import java.util.HashMap;
import java.util.LinkedList;

import de.htwmaps.shared.exceptions.PathNotFoundException;





/**
 * @author Stanislaw Tartakowski
 * 
 * This class build a graph, calls the search algorithms, awaits their end and builds result
 */
public class AStarBiStarter extends ShortestPathAlgorithm {
	public AStarBiStarter(GraphData gd) {
		super(gd);
	}

	/**
	 * knotenobjekte miteinander referenzieren
	 * @param edgeLengths 
	 * @param highwayTypes 
	 * @param edgeIDs 
	 */
	private void generateReferences(HashMap<Integer, AStarBiNode> Q, int[] edgeStartNodeIDs, int[] edgeEndNodeIDs, boolean[] oneways, double[] edgeLengths, int[] highwayTypes, int[] wayIDs, int[] edgeIDs, int option) {
		for (int i = 0 ; i < edgeStartNodeIDs.length; i++) {
			AStarBiNode fromNode = Q.get(edgeStartNodeIDs[i]), toNode = Q.get(edgeEndNodeIDs[i]);
			AStarBiEdge edge = null;
			switch (highwayTypes[i]) {
			case AStarEdge.MOTORWAY_ID: edge = new AStarBiEdge(toNode, edgeLengths[i], AStarEdge.MOTORWAY_ID, wayIDs[i], option == 0 ? AStarEdge.MOTORWAY_SPEED : 1, edgeIDs[i]); break;
			case AStarEdge.PRIMARY_ID: edge = new AStarBiEdge(toNode, edgeLengths[i],  AStarEdge.PRIMARY_ID, wayIDs[i],  option == 0 ? AStarEdge.PRIMARY_SPEED : 1, edgeIDs[i]); break;
			case AStarEdge.SECONDARY_ID: edge = new AStarBiEdge(toNode, edgeLengths[i],  AStarEdge.SECONDARY_ID, wayIDs[i],  option == 0 ? AStarEdge.SECONDARY_SPEED : 1, edgeIDs[i]); break;
			case AStarEdge.ROAD_ID: edge = new AStarBiEdge(toNode, edgeLengths[i],  AStarEdge.ROAD_ID, wayIDs[i],  option == 0 ? AStarEdge.ROAD_SPEED : 1, edgeIDs[i]); break;
			case AStarEdge.RESIDENTIAL_ID: edge = new AStarBiEdge(toNode, edgeLengths[i],  AStarEdge.RESIDENTIAL_ID, wayIDs[i],  option == 0 ? AStarEdge.RESIDENTIAL_SPEED : 1, edgeIDs[i]); break;
			case AStarEdge.LIVING_STREET_ID: edge = new AStarBiEdge(toNode, edgeLengths[i], AStarEdge.LIVING_STREET_ID, wayIDs[i],  option == 0 ? AStarEdge.LIVING_STREET_SPEED : 1, edgeIDs[i]); break;
			default: throw new IllegalArgumentException();
			}
			edge.setPredecessor(fromNode);
			fromNode.addEdge(edge);
			toNode.addEdge(edge);
			edge.setOneway(true);
			if (oneways[i] == false) {
				edge.setOneway(false);
			}
		}
		
	}
	
	/**
	 * Knoten erstellen
	 */
	private void generateNodes(HashMap<Integer, AStarBiNode> Q, int[] allNodesIDs, float[] lon, float[] lat) {
		for (int i = 0; i < allNodesIDs.length; i++) {
			Q.put(allNodesIDs[i], new AStarBiNode(lon[i], lat[i], allNodesIDs[i]));
		}
	}
	
	/**
	 * node list -> Node[] array
	 */
	public LinkedList<Node> nodeToArray(AStarBiNode start, AStarBiNode goal) {
		AStarBiNode tmp = start.getPredecessor() != null ? start : goal;
		LinkedList<Node> nodesContainer = new LinkedList<Node>();
		while (tmp != null) {
			nodesContainer.add(tmp);
			tmp = tmp.getPredecessor();
		}	
		if (start.getPredecessor() != null) {
			Node first = nodesContainer.getFirst();
			int i = 0;
			while (!first.equals(nodesContainer.getLast())) {
				nodesContainer.add(i, nodesContainer.removeLast()); 
				i++;
			} 
		}
		return nodesContainer;
	}
	
	

	public LinkedList<Node> aStar(int startNodeID, int goalNodeID, int option) throws PathNotFoundException {
		HashMap<Integer, AStarBiNode> Q = new HashMap<Integer, AStarBiNode>(graphData.getAllNodeIDs().length);

		long time = System.currentTimeMillis();
		generateNodes(Q, graphData.getAllNodeIDs(), graphData.getAllNodeLons(), graphData.getAllNodeLats());
		setBuildNodesTime(System.currentTimeMillis() - time);
		time = System.currentTimeMillis();
		generateReferences(Q, graphData.getEdgeStartNodeIDs(), graphData.getEdgeEndNodeIDs(), graphData.getOneways(), graphData.getEdgeLengths(), graphData.getHighwayTypes(), graphData.getWayIDs(), graphData.getEdgeIDs(), option);
		setBuildEdgesTime(System.currentTimeMillis() - time);
		
		AStarBiNode start = Q.get(startNodeID); 
		AStarBiNode goal = Q.get(goalNodeID);
		
		AStarBi d0 = new AStarBi(start, goal, true, this);
		AStarBi d1 = new AStarBi(goal, start, false, this);
		
		d0.setDijkstra(d1);
		d1.setDijkstra(d0);
		time = System.currentTimeMillis();
		d0.start();
		d1.start();
		
		synchronized(getClass()) {
			try {
				while (!AStarBi.finished) {
					this.getClass().wait();
				}
			} catch (InterruptedException e) {
				System.out.println("fatal error.");
			}
		}
		d0.interrupt();
		d1.interrupt();
		setAlorithmTime(System.currentTimeMillis() - time);
		LinkedList<Node> result = nodeToArray(start, goal);
		AStarBi.count.set(0);
		AStarBi.finished = false;
		if (result.size() == 1) {
			throw new PathNotFoundException("Weg nicht gefunden.");
		}
		return result;
	}
	
	@Override
	public LinkedList<Node> findShortestPath(int startNodeID, int goalNodeID,
			int motorwaySpeed, int primarySpeed, int residentialSpeed)
			throws PathNotFoundException {
		AStarEdge.MOTORWAY_SPEED = motorwaySpeed;
		AStarEdge.PRIMARY_SPEED = primarySpeed;
		AStarEdge.RESIDENTIAL_SPEED = residentialSpeed;
		return aStar(startNodeID, goalNodeID, 1);
	}

	@Override
	public LinkedList<Node> findFastestPath(int startNodeID, int goalNodeID,
			int motorwaySpeed, int primarySpeed, int residentialSpeed)
			throws PathNotFoundException {
		AStarEdge.MOTORWAY_SPEED = motorwaySpeed;
		AStarEdge.PRIMARY_SPEED = primarySpeed;
		AStarEdge.RESIDENTIAL_SPEED = residentialSpeed;
		return aStar(startNodeID, goalNodeID, 0);
	}
}
