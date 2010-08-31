package de.htwmaps.server;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.htwmaps.client.FindPathService;
import de.htwmaps.server.algorithm.AStar;
import de.htwmaps.server.algorithm.AStarBiStarter;
import de.htwmaps.server.algorithm.AStarEdge;
import de.htwmaps.server.algorithm.GraphData;
import de.htwmaps.server.algorithm.Node;
import de.htwmaps.server.algorithm.ShortestPathAlgorithm;
import de.htwmaps.server.db.DBAdapterRotativeRectangle;
import de.htwmaps.server.db.DBUtils;
import de.htwmaps.server.db.RouteToText;
import de.htwmaps.shared.AllPathData;
import de.htwmaps.shared.OptPathData;
import de.htwmaps.shared.PathDescription;
import de.htwmaps.shared.exceptions.MySQLException;
import de.htwmaps.shared.exceptions.NodeNotFoundException;
import de.htwmaps.shared.exceptions.PathNotFoundException;
import de.htwmaps.shared.exceptions.SQLException;

public class FindPathServiceImpl extends RemoteServiceServlet implements
		FindPathService {

	private static final long serialVersionUID = 1L;
	public static final int SHORTEST = 0;
	public static final int FASTEST = 1;
	
	private Node[] nodes;
	private AStarEdge[] edges;

	
	@Override
	public OptPathData findShortestPathAStar(String startCity, String startStreet,
			String destCity, String destStreet) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		GraphData gd = new GraphData();
		ShortestPathAlgorithm spa = new AStar(gd);
		return executeSearch(spa, gd, SHORTEST, startCity, startStreet, destCity, destStreet, 0, 0, 0);
	}

	@Override
	public OptPathData findFastestPathAStar(String startCity, String startStreet,
			String destCity, String destStreet, int motorwaySpeed,
			int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		GraphData gd = new GraphData();
		ShortestPathAlgorithm spa = new AStar(gd);
		return executeSearch(spa, gd, FASTEST, startCity, startStreet, destCity, destStreet, motorwaySpeed, primarySpeed, residentialSpeed);
	}

	@Override
	public OptPathData findShortestPathAStarBi(String startCity,
			String startStreet, String destCity, String destStreet) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		GraphData gd = new GraphData();
		ShortestPathAlgorithm spa = new AStarBiStarter(gd);
		return executeSearch(spa, gd, SHORTEST, startCity, startStreet, destCity, destStreet, 0, 0, 0);
	}

	@Override
	public OptPathData findFastestPathAStarBi(String startCity,
			String startStreet, String destCity, String destStreet,
			int motorwaySpeed, int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		GraphData gd = new GraphData();
		ShortestPathAlgorithm spa = new AStarBiStarter(gd);
		return executeSearch(spa, gd, FASTEST, startCity, startStreet, destCity, destStreet, motorwaySpeed, primarySpeed, residentialSpeed);
	}

	
	private OptPathData executeSearch(ShortestPathAlgorithm spa,
			GraphData gd, int option, String startCity, String startStreet,
			String destCity, String destStreet, int motorwaySpeed,
			int primarySpeed, int residentialSpeed) throws NodeNotFoundException, MySQLException, PathNotFoundException, SQLException {
		try{
			int startNodeID = DBUtils.getNodeId(startCity, startStreet);
			if (startNodeID == -1) {
				throw new NodeNotFoundException("Falsche Startdaten");
			}
			int goalNodeID = DBUtils.getNodeId(destCity, destStreet);
			if (goalNodeID == -1) {
				throw new NodeNotFoundException("Falsche Zieldaten");
			}
			float h = 0.1f; //20 km dicke
			DBAdapterRotativeRectangle dbap;
			dbap = new DBAdapterRotativeRectangle(gd);
			dbap.fillGraphData(startNodeID, goalNodeID, h);
			try {
				switch (option) {
				case FASTEST: nodes = spa.findFastestPath(startNodeID, goalNodeID, motorwaySpeed, primarySpeed, residentialSpeed); break;
				case SHORTEST: nodes = spa.findShortestPath(startNodeID, goalNodeID); break;
				}
			} catch (PathNotFoundException e) {
				System.out.print("2. versuch");
				dbap.fillGraphData(startNodeID, goalNodeID, h + 1.4f);
				try {
					switch (option) {
					case FASTEST: nodes = spa.findFastestPath(startNodeID, goalNodeID, motorwaySpeed, primarySpeed, residentialSpeed); break;
					case SHORTEST: nodes = spa.findShortestPath(startNodeID, goalNodeID); break;
					}
				} catch (PathNotFoundException ex) {
					System.out.println(" fehlgeschlagen");
					throw new PathNotFoundException("Es tut uns Leid. Dieser Weg kann nicht erfasst werden.");
				}
			}
			edges	= ShortestPathAlgorithm.getResultEdges(nodes);
			return buildOptPathData(nodes, spa, dbap);
		}catch(java.sql.SQLException e){
			throw new SQLException();
		}
	}
	
	private OptPathData buildOptPathData(Node[] nodes, ShortestPathAlgorithm spa, DBAdapterRotativeRectangle dbap) throws java.sql.SQLException, MySQLException{
		float[] lats = new float[nodes.length];
		float[] lons = new float[nodes.length];
		for(int i=0; i<nodes.length;i++){
			lats[i] = nodes[i].getLat();
			lons[i] = nodes[i].getLon();
		}
		OptPathData pd = new OptPathData();
		pd.setNodeLats(lats);
		pd.setNodeLons(lons);
		pd.setOptNodesResultCount(nodes.length);
		pd.setAlorithmTime(spa.getAlorithmTime());
		pd.setBuildEdgesTime(spa.getBuildEdgesTime());
		pd.setBuildNodesTime(spa.getBuildNodesTime());
		pd.setEdgesCount(dbap.getEdgesCount());
		pd.setNodesCount(dbap.getNodesCount());
		pd.setReceiveEdgesTime(dbap.getReceiveEdgesTime());
		pd.setReceiveNodesTime(dbap.getReceiveNodesTime());
		return pd;
	}

	@Override
	public AllPathData buildAllPathData() throws MySQLException, SQLException {
		if(nodes == null || edges == null)
			throw new RuntimeException("Call one of the findFastestPath methods first!");
		
		AllPathData allND = new AllPathData();
		long time = System.currentTimeMillis();
		float[][] latLons;
		try {
			latLons = DBUtils.getAllNodeLatLons(nodes, edges);
		} catch (java.sql.SQLException e) {
			throw new SQLException();
		}
		allND.setOptToAllRuntime(System.currentTimeMillis() - time);
		allND.setAllNodesNumber(latLons[0].length);
		allND.setLats(latLons[0]);
		allND.setLons(latLons[1]);
		return allND;
	}

	@Override
	public PathDescription buildPathDescription() throws MySQLException, SQLException {
		if(nodes == null || edges == null)
			throw new RuntimeException("Call one of the findFastestPath methods first!");
		
		PathDescription pd = new PathDescription();
		long time = System.currentTimeMillis();
		RouteToText rtt;
		try {
			rtt = new RouteToText(nodes, edges);
		} catch (java.sql.SQLException e) {
			throw new SQLException();
		}
		pd.setDescription(rtt.buildRouteInfo().toString());
		pd.setRuntime(System.currentTimeMillis() - time);
		return pd;
	}
}
