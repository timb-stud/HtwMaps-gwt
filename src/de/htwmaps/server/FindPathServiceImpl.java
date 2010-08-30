package de.htwmaps.server;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.htwmaps.client.FindPathService;
import de.htwmaps.server.algorithm.AStar;
import de.htwmaps.server.algorithm.AStarBiStarter;
import de.htwmaps.server.algorithm.AStarEdge;
import de.htwmaps.server.algorithm.GraphData;
import de.htwmaps.server.algorithm.Node;
import de.htwmaps.server.algorithm.ShortestPathAlgorithm;
import de.htwmaps.server.db.DBAdapterParabel;
import de.htwmaps.server.db.DBUtils;
import de.htwmaps.shared.PathData;
import de.htwmaps.shared.exceptions.MySQLException;
import de.htwmaps.shared.exceptions.NodeNotFoundException;
import de.htwmaps.shared.exceptions.PathNotFoundException;
import de.htwmaps.shared.exceptions.SQLException;

public class FindPathServiceImpl extends RemoteServiceServlet implements
		FindPathService {

	private static final long serialVersionUID = 1L;
	public static final int SHORTEST = 0;
	public static final int FASTEST = 1;

	
	@Override
	public PathData findShortestPathAStar(String startCity, String startStreet,
			String destCity, String destStreet) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		GraphData gd = new GraphData();
		ShortestPathAlgorithm spa = new AStar(gd);
		return executeSearch(spa, gd, SHORTEST, startCity, startStreet, destCity, destStreet, 0, 0, 0);
	}

	@Override
	public PathData findFastestPathAStar(String startCity, String startStreet,
			String destCity, String destStreet, int motorwaySpeed,
			int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		GraphData gd = new GraphData();
		ShortestPathAlgorithm spa = new AStar(gd);
		return executeSearch(spa, gd, FASTEST, startCity, startStreet, destCity, destStreet, motorwaySpeed, primarySpeed, residentialSpeed);
	}

	@Override
	public PathData findShortestPathAStarBi(String startCity,
			String startStreet, String destCity, String destStreet) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		GraphData gd = new GraphData();
		ShortestPathAlgorithm spa = new AStarBiStarter(gd);
		return executeSearch(spa, gd, SHORTEST, startCity, startStreet, destCity, destStreet, 0, 0, 0);
	}

	@Override
	public PathData findFastestPathAStarBi(String startCity,
			String startStreet, String destCity, String destStreet,
			int motorwaySpeed, int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		GraphData gd = new GraphData();
		ShortestPathAlgorithm spa = new AStarBiStarter(gd);
		return executeSearch(spa, gd, FASTEST, startCity, startStreet, destCity, destStreet, motorwaySpeed, primarySpeed, residentialSpeed);
	}

	
	private PathData executeSearch(ShortestPathAlgorithm spa,
			GraphData gd, int option, String startCity, String startStreet,
			String destCity, String destStreet, int motorwaySpeed,
			int primarySpeed, int residentialSpeed) throws NodeNotFoundException, MySQLException, PathNotFoundException, SQLException {
		try{
			int startNodeID = DBUtils.getNodeId(startCity, startStreet);
			if (startNodeID == -1) {
				throw new NodeNotFoundException("Flasche Startdaten");
			}
			int goalNodeID = DBUtils.getNodeId(destCity, destStreet);
			if (goalNodeID == -1) {
				throw new NodeNotFoundException("Flasche Zieldaten");
			}
			float h = 0.1f; //20 km dicke
			DBAdapterParabel dbap;
			dbap = new DBAdapterParabel(gd);
			Node[] result = null;
			dbap.fillGraphData(startNodeID, goalNodeID, h, option);
			try {
				switch (option) {
				case FASTEST: result = spa.findFastestPath(startNodeID, goalNodeID, motorwaySpeed, primarySpeed, residentialSpeed); break;
				case SHORTEST: result = spa.findShortestPath(startNodeID, goalNodeID); break;
				}
			} catch (PathNotFoundException e) {
				System.out.print("2. versuch");
				dbap.fillGraphData(startNodeID, goalNodeID, h + 1.4f, option);
				try {
					switch (option) {
					case FASTEST: result = spa.findFastestPath(startNodeID, goalNodeID, motorwaySpeed, primarySpeed, residentialSpeed); break;
					case SHORTEST: result = spa.findShortestPath(startNodeID, goalNodeID); break;
					}
				} catch (PathNotFoundException ex) {
					System.out.println(" fehlgeschlagen");
					throw new PathNotFoundException("Es tut uns Leid. Dieser Weg kann nicht erfasst werden.");
				}
			}
			return buildPathData(result, spa, dbap);
		}catch(java.sql.SQLException e){
			throw new SQLException();
		}
	}
	
	private PathData buildPathData(Node[] nodes, ShortestPathAlgorithm spa, DBAdapterParabel dbap) throws java.sql.SQLException, MySQLException{
		PathData pd = new PathData();
		AStarEdge [] edges	= ShortestPathAlgorithm.getResultEdges(nodes);
		long optAllTime = System.currentTimeMillis();
		float [][] latLons = DBUtils.getAllNodeLatLons(nodes, edges);
		pd.setOptToAllTime(System.currentTimeMillis() - optAllTime);
		pd.setOptNodesResultCount(nodes.length);
		pd.setAllNodesResultCount(latLons[0].length);
		pd.setNodeLats(latLons[0]);
		pd.setNodeLons(latLons[1]);
		pd.setAlorithmTime(spa.getAlorithmTime());
		pd.setBuildEdgesTime(spa.getBuildEdgesTime());
		pd.setBuildNodesTime(spa.getBuildNodesTime());
		pd.setEdgesCount(dbap.getEdgesCount());
		pd.setNodesCount(dbap.getNodesCount());
		pd.setReceiveEdgesTime(dbap.getReceiveEdgesTime());
		pd.setReceiveNodesTime(dbap.getReceiveNodesTime());
		return pd;
	}
}
