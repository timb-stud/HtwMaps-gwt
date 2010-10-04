package de.htwmaps.server.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;

import de.htwmaps.shared.exceptions.PathNotFoundException;

/**
 * Schnittstelle zu AStar und ASartBi
 * 
 * @author Stanislaw Tartakowski, Tim Bartsch
 *
 */
public abstract class ShortestPathAlgorithm {
	GraphData graphData;
	ArrayList<Node> closedNodes = new ArrayList<Node>();
	
	private long buildNodesTime;
	private long buildEdgesTime;
	private long alorithmTime;
	
	
	public ShortestPathAlgorithm(GraphData gd) {
		if (gd == null) {
			throw new IllegalArgumentException("Graph data must not be null");
		}
		this.graphData = gd;
	}
	
	/**
	 * Sucht den kuerzesten Weg zwischen start und ziel node.
	 * 
	 * @param motorwaySpeed durchschnitts Autobahngeschwindigkeit
	 * @param primarySpeed durchschnitts Landstrassengeschwindigkeit
	 * @param residentialSpeed durchschnitts Innerortsgeschwindigkeit
	 * 
	 * @return eine Node List mit allen Nodes der Route. Der erste Node in der Liste ist der ziel Node
	 * der letzte ist der start Node.
	 */
	public abstract LinkedList<Node> findShortestPath(int startNodeID,
									int goalNodeID,
									int motorwaySpeed, 
									int primarySpeed,
									int residentialSpeed) throws PathNotFoundException;
	

	
	
	/**
	 * Sucht den schnellsten Weg zwischen start und ziel node.
	 * 
	 * @param motorwaySpeed durchschnitts Autobahngeschwindigkeit
	 * @param primarySpeed durchschnitts Landstrassengeschwindigkeit
	 * @param residentialSpeed durchschnitts Innerortsgeschwindigkeit
	 * 
	 * @return eine Node List mit allen Nodes der Route. Der erste Node in der Liste ist der ziel Node
	 * der letzte ist der start Node.
	 */
	public abstract LinkedList<Node> findFastestPath(int startNodeID, 
									int goalNodeID, 
									int motorwaySpeed, 
									int primarySpeed,
									int residentialSpeed) throws PathNotFoundException;

	public static AStarEdge[] getResultEdges(Node[] result) {
		AStarEdge[] edges = new AStarEdge[result.length - 1];
		 loop:for(int i=result.length -1; i>0; i--){
			 for(AStarEdge e: result[i].getEdgeList()){
				 if(e.getSuccessor().equals(result[i-1])){
					 edges[i - 1] = e;
					 continue loop;
				 }
			 }
			 if (edges[i - 1] == null) {
				 for(AStarEdge e: result[i - 1].getEdgeList()){
					 if(e.getSuccessor().equals(result[i])){
						 edges[i - 1] = e;
						 continue loop;
					 }
				 }
			 }
		 }
		return edges;
	}

	/**
	 * 
	 * @return Laufzeit zum Nodes aufbauen.
	 */
	public long getBuildNodesTime() {
		return buildNodesTime;
	}

	/**
	 * Setze Laufzeit zum Nodes aufbauen.
	 * @param buildNodesTime
	 */
	public void setBuildNodesTime(long buildNodesTime) {
		this.buildNodesTime += buildNodesTime;
	}

	/**
	 * 
	 * @return Laufzeit zum Edges aufbauen.
	 */
	public long getBuildEdgesTime() {
		return buildEdgesTime;
	}

	/**
	 * Setze Laufzeit zum Edges aufbauen.
	 * @param buildEdgesTime
	 */
	public void setBuildEdgesTime(long buildEdgesTime) {
		this.buildEdgesTime += buildEdgesTime;
	}
	/**
	 * 
	 * @return Algorithmen Laufzeit.
	 */
	public long getAlorithmTime() {
		return alorithmTime;
	}

	/**
	 * Setze Algorithmen Laufzeit.
	 * @param alorithmTime
	 */
	public void setAlorithmTime(long alorithmTime) {
		this.alorithmTime += alorithmTime;
	}

	public float[][] getClosedLatLons(){
		float[][] latLons = new float[2][closedNodes.size()];
		for(int i=0; i<latLons[0].length;i++){
			latLons[0][i] = closedNodes.get(i).lat;
			latLons[1][i] = closedNodes.get(i).lon;
		}
		return latLons;
	}
	
}