package de.htwmaps.server.algorithm;
import java.util.ArrayList;
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
	private void generateReferences(HashMap<Integer, AStarBiNode> Q, int[] edgeStartNodeIDs, int[] edgeEndNodeIDs, boolean[] oneways, double[] edgeLengths, int[] highwayTypes, int[] wayIDs, int[] edgeIDs) {
		for (int i = 0 ; i < edgeStartNodeIDs.length; i++) {
			AStarBiNode fromNode = Q.get(edgeStartNodeIDs[i]), toNode = Q.get(edgeEndNodeIDs[i]);
			AStarBiEdge edge = null;
			switch (highwayTypes[i]) {
			case MOTORWAY: edge = new AStarBiEdge(toNode, edgeLengths[i], MOTORWAY, wayIDs[i], getMotorwaySpeed(), edgeIDs[i]); break;
			case PRIMARY: edge = new AStarBiEdge(toNode, edgeLengths[i], PRIMARY, wayIDs[i], getPrimarySpeed(), edgeIDs[i]); break;
			case SECONDARY: edge = new AStarBiEdge(toNode, edgeLengths[i], SECONDARY, wayIDs[i], getSecondarySpeed(), edgeIDs[i]); break;
			case ROAD: edge = new AStarBiEdge(toNode, edgeLengths[i], ROAD, wayIDs[i], getRoadSpeed(), edgeIDs[i]); break;
			case RESIDENTIAL: edge = new AStarBiEdge(toNode, edgeLengths[i], RESIDENTIAL, wayIDs[i], getResidentialSpeed(), edgeIDs[i]); break;
			case LIVING_STREET: edge = new AStarBiEdge(toNode, edgeLengths[i], LIVING_STREET, wayIDs[i], getLivingStreetSpeed(), edgeIDs[i]); break;
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
		//TODO umdrehen
		return nodesContainer;
	}
	

	public LinkedList<Node> aStar(int startNodeID, int goalNodeID) throws PathNotFoundException {
		HashMap<Integer, AStarBiNode> Q = new HashMap<Integer, AStarBiNode>(graphData.getAllNodeIDs().length);

		long time = System.currentTimeMillis();
		generateNodes(Q, graphData.getAllNodeIDs(), graphData.getAllNodeLons(), graphData.getAllNodeLats());
		setBuildNodesTime(System.currentTimeMillis() - time);
		time = System.currentTimeMillis();
		generateReferences(Q, graphData.getEdgeStartNodeIDs(), graphData.getEdgeEndNodeIDs(), graphData.getOneways(), graphData.getEdgeLengths(), graphData.getHighwayTypes(), graphData.getWayIDs(), graphData.getEdgeIDs());
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
		if (result.length == 1) {
			throw new PathNotFoundException("Weg nicht gefunden.");
		}
		return result;
	}
	
	@Override
	public LinkedList<Node> findShortestPath(int startNodeID, int goalNodeID)
			throws PathNotFoundException {
		setMotorwaySpeed(1);
		setPrimarySpeed(1);
		setSecondarySpeed(1);
		setResidentialSpeed(1);
		setRoadSpeed(1);
		setLivingStreetSpeed(1);
		return aStar(startNodeID, goalNodeID);
	}

	@Override
	public LinkedList<Node> findFastestPath(int startNodeID, int goalNodeID,
			int motorwaySpeed, int primarySpeed, int secondarySpeed,
			int residentialSpeed, int roadSpeed, int livingStreetSpeed)
			throws PathNotFoundException {
		setMotorwaySpeed(motorwaySpeed);
		setPrimarySpeed(primarySpeed);
		setSecondarySpeed(secondarySpeed);
		setResidentialSpeed(residentialSpeed);
		setRoadSpeed(roadSpeed);
		setLivingStreetSpeed(livingStreetSpeed);
		return aStar(startNodeID, goalNodeID);
	}

	@Override
	public LinkedList<Node> findFastestPath(int startNodeID, int goalNodeID,
			int motorwaySpeed, int primarySpeed, int residentialSpeed)
			throws PathNotFoundException {
		setMotorwaySpeed(motorwaySpeed);
		setPrimarySpeed(primarySpeed);
		setResidentialSpeed(residentialSpeed);
		return aStar(startNodeID, goalNodeID);
	}
}
