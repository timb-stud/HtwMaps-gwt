package de.htwmaps.server.algorithm;

import java.util.HashMap;
import java.util.LinkedList;

import de.htwmaps.server.algorithm.utils.FibonacciHeap;
import de.htwmaps.shared.exceptions.PathNotFoundException;

/**
 * A Star implementation from en.wikipedia.org
 * 
 * @author Tim Bartsch
 * 
 */
public class AStar extends ShortestPathAlgorithm {
	HashMap<Integer, AStarNode> allNodes;

	public AStar(GraphData gd) {
		super(gd);
	}

	/**
	 * Implementation of the A Star algorithm. It uses referenced Nodes as a
	 * graph representation.
	 * 
	 * @param startNodeID
	 * @param goalNodeID
	 * @param maxSpeed
	 * @return a Node list representing the found path. First node is goalNode
	 *         startNode is the last Node.
	 * @throws PathNotFoundException
	 */
	private LinkedList<Node> aStar(int startNodeID, int goalNodeID, int maxSpeed)
			throws PathNotFoundException {
		long time = System.currentTimeMillis();
		AStarNode start = allNodes.get(startNodeID);
		AStarNode goal = allNodes.get(goalNodeID);
		HashMap<Integer, AStarNode> closedSet = new HashMap<Integer, AStarNode>(
				allNodes.size());
		FibonacciHeap openSet = new FibonacciHeap();
		AStarNode current;
		start.setG(0);
		start.setF(start.getDistanceTo(goal) / maxSpeed);
		openSet.add(start, start.getF());

		while (openSet.size() > 0) {
			current = (AStarNode) openSet.popMin();
			if (current == goal) {
				setAlorithmTime(System.currentTimeMillis() - time);
				return reconstructPath(goal);
			}
			closedSet.put(current.getId(), current);
			for (AStarEdge edge : current.getEdgeList()) {
				AStarNode successor = (AStarNode) edge.getSuccessor();
				if (closedSet.containsKey(successor.id))
					continue;
				double tentativeG = current.getG() + edge.getPrioLength();

				if (!openSet.contains(successor)) {
					successor.setPredeccessor(current);
					successor.setG(tentativeG);
					successor.setF(successor.getG()
							+ (successor.getDistanceTo(goal) / maxSpeed));
					openSet.add(successor, successor.getF());
				} else {
					if (tentativeG < successor.getG()) {
						successor.setPredeccessor(current);
						successor.setG(tentativeG);
						successor.setF(successor.getG()
								+ (successor.getDistanceTo(goal) / maxSpeed));
						openSet.decreaseKey(successor, successor.getF());
					}
				}
			}
		}
		throw new PathNotFoundException();
	}

	/**
	 * Reconstructs the path found by astar into a Linked Node List.
	 * 
	 * @param goal
	 *            the goal Node of the searched way.
	 * @return LinkedList<Node> containing all Nodes from goal to start.
	 */
	private LinkedList<Node> reconstructPath(AStarNode goal) {
		LinkedList<Node> path = new LinkedList<Node>();
		while (goal != null) {
			path.add(goal);
			goal = goal.getPredeccessor();
		}
		return path;
	}

	/**
	 * Builds Nodes with the information given from the GraphData Object and adds them to a global Hash Map.
	 * 
	 */
	private void buildNodes() {
		long time = System.currentTimeMillis();
		int[] allNodeIDs = graphData.getAllNodeIDs();
		float[] allNodeLats = graphData.getAllNodeLats();
		float[] allNodeLons = graphData.getAllNodeLons();
		this.allNodes = new HashMap<Integer, AStarNode>(allNodeIDs.length, 1.0f);
		for (int i = 0; i < allNodeIDs.length; i++) {
			allNodes.put(allNodeIDs[i], new AStarNode(allNodeIDs[i],
					allNodeLons[i], allNodeLats[i]));
		}
		setBuildNodesTime(System.currentTimeMillis() - time);
	}

	/**
	 * Builds Edges between the Nodes with the information given from the GraphData Object.
	 * 
	 * @param motorwaySpeed average speed on motorway edges
	 * @param primarySpeed average speed on primary edges
	 * @param secondarySpeed average speed on secondaryedges
	 * @param residentialSpeed average speed on residentialedges
	 * @param roadSpeed average speed on road edges
	 * @param livingStreetSpeed average speed on livingStreetedges
	 */
	private void buildEdges(int motorwaySpeed, int primarySpeed,
			int secondarySpeed, int residentialSpeed, int roadSpeed,
			int livingStreetSpeed) {
		long time = System.currentTimeMillis();
		int[] edgeStartNodeIDs = graphData.getEdgeStartNodeIDs();
		int[] edgeEndNodeIDs = graphData.getEdgeEndNodeIDs();
		double[] edgeLenghts = graphData.getEdgeLengths();
		int[] highwayTypes = graphData.getHighwayTypes();
		int[] wayIDs = graphData.getWayIDs();
		boolean[] oneways = graphData.getOneways();
		int[] edgeIDs = graphData.getEdgeIDs();

		for (int i = 0; i < edgeStartNodeIDs.length; i++) {
			AStarNode fromNode = allNodes.get(edgeStartNodeIDs[i]);
			AStarNode toNode = allNodes.get(edgeEndNodeIDs[i]);

			switch (highwayTypes[i]) {
			case AStarEdge.MOTORWAY_ID:
				fromNode.addEdge(new AStarEdge(toNode, edgeLenghts[i],
						highwayTypes[i], wayIDs[i], motorwaySpeed, edgeIDs[i]));
				if (!oneways[i])
					toNode.addEdge(new AStarEdge(fromNode, edgeLenghts[i],
							highwayTypes[i], wayIDs[i], motorwaySpeed,
							edgeIDs[i]));
				break;
			case AStarEdge.PRIMARY_ID:
				fromNode.addEdge(new AStarEdge(toNode, edgeLenghts[i],
						highwayTypes[i], wayIDs[i], primarySpeed, edgeIDs[i]));
				if (!oneways[i])
					toNode.addEdge(new AStarEdge(fromNode, edgeLenghts[i],
							highwayTypes[i], wayIDs[i], primarySpeed,
							edgeIDs[i]));
				break;
			case AStarEdge.SECONDARY_ID:
				fromNode.addEdge(new AStarEdge(toNode, edgeLenghts[i],
						highwayTypes[i], wayIDs[i], secondarySpeed, edgeIDs[i]));
				if (!oneways[i])
					toNode.addEdge(new AStarEdge(fromNode, edgeLenghts[i],
							highwayTypes[i], wayIDs[i], secondarySpeed,
							edgeIDs[i]));
				break;
			case AStarEdge.RESIDENTIAL_ID:
				fromNode.addEdge(new AStarEdge(toNode, edgeLenghts[i],
						highwayTypes[i], wayIDs[i], residentialSpeed,
						edgeIDs[i]));
				if (!oneways[i])
					toNode.addEdge(new AStarEdge(fromNode, edgeLenghts[i],
							highwayTypes[i], wayIDs[i], residentialSpeed,
							edgeIDs[i]));
				break;
			case AStarEdge.ROAD_ID:
				fromNode.addEdge(new AStarEdge(toNode, edgeLenghts[i],
						highwayTypes[i], wayIDs[i], roadSpeed, edgeIDs[i]));
				if (!oneways[i])
					toNode.addEdge(new AStarEdge(fromNode, edgeLenghts[i],
							highwayTypes[i], wayIDs[i], roadSpeed, edgeIDs[i]));
				break;
			case AStarEdge.LIVING_STREET_ID:
				fromNode.addEdge(new AStarEdge(toNode, edgeLenghts[i],
						highwayTypes[i], wayIDs[i], livingStreetSpeed,
						edgeIDs[i]));
				if (!oneways[i])
					toNode.addEdge(new AStarEdge(fromNode, edgeLenghts[i],
							highwayTypes[i], wayIDs[i], livingStreetSpeed,
							edgeIDs[i]));
				break;
			default:
				throw new IllegalArgumentException();
			}
		}
		setBuildEdgesTime(System.currentTimeMillis() - time);
	}

	/**
	 * Builds Edges between the Nodes with the information given from the GraphData Object.<br>
	 * The average speed on all Edges is set to 1.
	 */
	private void buildEdges() {
		long time = System.currentTimeMillis();
		int[] edgeStartNodeIDs = graphData.getEdgeStartNodeIDs();
		int[] edgeEndNodeIDs = graphData.getEdgeEndNodeIDs();
		double[] edgeLenghts = graphData.getEdgeLengths();
		int[] highwayTypes = graphData.getHighwayTypes();
		int[] wayIDs = graphData.getWayIDs();
		boolean[] oneways = graphData.getOneways();
		int[] edgeIDs = graphData.getEdgeIDs();
		for (int i = 0; i < edgeStartNodeIDs.length; i++) {
			AStarNode fromNode = allNodes.get(edgeStartNodeIDs[i]);
			AStarNode toNode = allNodes.get(edgeEndNodeIDs[i]);
			fromNode.addEdge(new AStarEdge(toNode, edgeLenghts[i],
					highwayTypes[i], wayIDs[i], 1, edgeIDs[i]));
			if (!oneways[i])
				toNode.addEdge(new AStarEdge(fromNode, edgeLenghts[i],
						highwayTypes[i], wayIDs[i], 1, edgeIDs[i]));
		}
		setBuildEdgesTime(System.currentTimeMillis() - time);
	}

	

	/**
	 * Find the fastest path between start node and goal node.
	 * 
	 * @param motorwaySpeed average speed on motorway edges
	 * @param primarySpeed average speed on primary edges
	 * @param residentialSpeed average speed on residentialedges
	 * 
	 * @return a Node list representing the found path. First node is goalNode
	 *         startNode is the last Node.
	 */
	@Override
	public LinkedList<Node> findFastestPath(int startNodeID, int goalNodeID,
			int motorwaySpeed, int primarySpeed, int residentialSpeed)
			throws PathNotFoundException {

		int maxSpeed;
		int[] speeds = new int[6];
		speeds[0] = motorwaySpeed;
		speeds[1] = primarySpeed;
		speeds[2] = AStarEdge.SECONDARY_SPEED;
		speeds[3] = residentialSpeed;
		speeds[4] = AStarEdge.ROAD_SPEED;
		speeds[5] = AStarEdge.LIVING_STREET_SPEED;
		maxSpeed = getMax(speeds);
		buildNodes();
		buildEdges(motorwaySpeed, primarySpeed, AStarEdge.SECONDARY_SPEED,
				residentialSpeed, AStarEdge.ROAD_SPEED, AStarEdge.LIVING_STREET_SPEED);
		return aStar(startNodeID, goalNodeID, maxSpeed);
		
	}

	/**
	 * Returns the maximum value of tab.
	 * @param tab
	 * @return int maximum
	 */
	private int getMax(int[] tab) {
		int max = 0;
		for (int i : tab) {
			if (max < i)
				max = i;
		}
		return max;
	}

	/**
	 * Find the shortest path between start node and goal node.
	 * 
	 * @param motorwaySpeed average speed on motorway edges
	 * @param primarySpeed average speed on primary edges
	 * @param residentialSpeed average speed on residentialedges
	 * 
	 * @return a Node list representing the found path. First node is goalNode
	 *         startNode is the last Node.
	 */
	@Override
	public LinkedList<Node> findShortestPath(int startNodeID, int goalNodeID,
			int motorwaySpeed, int primarySpeed, int residentialSpeed)
			throws PathNotFoundException {

		AStarEdge.MOTORWAY_SPEED = motorwaySpeed;
		AStarEdge.PRIMARY_SPEED = primarySpeed;
		AStarEdge.RESIDENTIAL_SPEED = residentialSpeed;
		buildNodes();
		buildEdges();
		int maxSpeed = 1;
		return aStar(startNodeID, goalNodeID, maxSpeed);
	}

}
