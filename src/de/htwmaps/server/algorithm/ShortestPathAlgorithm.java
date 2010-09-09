package de.htwmaps.server.algorithm;

import java.util.LinkedList;

import de.htwmaps.shared.exceptions.PathNotFoundException;


public abstract class ShortestPathAlgorithm {
	GraphData graphData;
	
	private long buildNodesTime;
	private long buildEdgesTime;
	private long alorithmTime;
	
	
	public ShortestPathAlgorithm(GraphData gd) {
		if (gd == null) {
			throw new IllegalArgumentException("Graph data must not be null");
		}
		this.graphData = gd;
	}
	
	public abstract LinkedList<Node> findShortestPath(int startNodeID,
									int goalNodeID,
									int motorwaySpeed, 
									int primarySpeed,
									int residentialSpeed) throws PathNotFoundException;
	

	
	
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

	public long getBuildNodesTime() {
		return buildNodesTime;
	}

	public void setBuildNodesTime(long buildNodesTime) {
		this.buildNodesTime += buildNodesTime;
	}

	public long getBuildEdgesTime() {
		return buildEdgesTime;
	}

	public void setBuildEdgesTime(long buildEdgesTime) {
		this.buildEdgesTime += buildEdgesTime;
	}

	public long getAlorithmTime() {
		return alorithmTime;
	}

	public void setAlorithmTime(long alorithmTime) {
		this.alorithmTime += alorithmTime;
	}

	
	
}