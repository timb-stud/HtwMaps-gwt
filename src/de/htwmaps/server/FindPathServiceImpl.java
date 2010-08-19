package de.htwmaps.server;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.htwmaps.client.FindPathService;
import de.htwmaps.server.algorithm.AStar;
import de.htwmaps.server.algorithm.AStarBidirectionalStarter;
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

	
	@Override
	public PathData findShortestPathAStar(String startCity, String startStreet,
			String destCity, String destStreet) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		
		try{
			int startNodeID = DBUtils.getNodeId(startCity, startStreet);
			int goalNodeID = DBUtils.getNodeId(destCity, destStreet);
			GraphData gd = new GraphData();
			ShortestPathAlgorithm as = new AStar(gd);
			float a = 0.9f;
			float h = 0.01f;
			DBAdapterParabel dbap;
			dbap = new DBAdapterParabel(gd);
			Node[] result;
			while(true) {
				dbap.fillGraphData(startNodeID, goalNodeID, a, h);
				try {
					result = as.findShortestPath(startNodeID, goalNodeID);
					break;
				} catch (PathNotFoundException e) {
					a *= 0.5f;
					h += 0.01f;
					System.out.println(a);
					if (a <= 0.01) {
						throw new PathNotFoundException();
					}
				}
			}
			float[] nodeLats = new float[result.length];
			float[] nodeLons = new float[result.length];
			for(int i=0; i<nodeLats.length;i++){
				nodeLats[i] = result[i].getLat();
				nodeLons[i] = result[i].getLon();
			}
			PathData pd = new PathData();
			pd.setNodeLats(nodeLats);
			pd.setNodeLons(nodeLons);
			return pd;
		}catch(java.sql.SQLException e){
			throw new SQLException();
		}
	}

	@Override
	public PathData findFastestPathAStar(String startCity, String startStreet,
			String destCity, String destStreet, int motorwaySpeed,
			int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {

		try{
			int startNodeID = DBUtils.getNodeId(startCity, startStreet);
			int goalNodeID = DBUtils.getNodeId(destCity, destStreet);
			GraphData gd = new GraphData();
			ShortestPathAlgorithm as = new AStar(gd);
			float a = 0.8f;
			float h = 0.01f;
			DBAdapterParabel dbap;
			dbap = new DBAdapterParabel(gd);
			Node[] result;
			while(true) {
				dbap.fillGraphData(startNodeID, goalNodeID, a, h);
				try {
					result = as.findFastestPath(startNodeID, goalNodeID, motorwaySpeed, primarySpeed, residentialSpeed);
					break;
				} catch (PathNotFoundException e) {
					a *= 0.5f;
					h += 0.001f;
					System.out.println(a);
					if (a <= 0.01) {
						throw new PathNotFoundException();
					}
				}
			}
			float[] nodeLats = new float[result.length];
			float[] nodeLons = new float[result.length];
			for(int i=0; i<nodeLats.length;i++){
				nodeLats[i] = result[i].getLat();
				nodeLons[i] = result[i].getLon();
			}
			PathData pd = new PathData();
			pd.setNodeLats(nodeLats);
			pd.setNodeLons(nodeLons);
			return pd;
		}catch(java.sql.SQLException e){
			throw new SQLException();
		}
	}

	@Override
	public PathData findShortestPathAStarBi(String startCity,
			String startStreet, String destCity, String destStreet) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		try{
			int startNodeID = DBUtils.getNodeId(startCity, startStreet);
			int goalNodeID = DBUtils.getNodeId(destCity, destStreet);
			GraphData gd = new GraphData();
			ShortestPathAlgorithm as = new AStarBidirectionalStarter(gd);
			float a = 0.8f;
			float h = 0.01f;
			DBAdapterParabel dbap;
			dbap = new DBAdapterParabel(gd);
			Node[] result;
			while(true) {
				dbap.fillGraphData(startNodeID, goalNodeID, a, h);
				try {
					result = as.findShortestPath(startNodeID, goalNodeID);
					break;
				} catch (PathNotFoundException e) {
					a *= 0.5f;
					h += 0.001f;
					System.out.println(a);
					if (a <= 0.01) {
						throw new PathNotFoundException();
					}
				}
			}
			float[] nodeLats = new float[result.length];
			float[] nodeLons = new float[result.length];
			for(int i=0; i<nodeLats.length;i++){
				nodeLats[i] = result[i].getLat();
				nodeLons[i] = result[i].getLon();
			}
			PathData pd = new PathData();
			pd.setNodeLats(nodeLats);
			pd.setNodeLons(nodeLons);
			return pd;
		}catch(java.sql.SQLException e){
			throw new SQLException();
		}
	}

	@Override
	public PathData findFastestPathAStarBi(String startCity,
			String startStreet, String destCity, String destStreet,
			int motorwaySpeed, int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		
		try{
			int startNodeID = DBUtils.getNodeId(startCity, startStreet);
			int goalNodeID = DBUtils.getNodeId(destCity, destStreet);
			GraphData gd = new GraphData();
			ShortestPathAlgorithm as = new AStarBidirectionalStarter(gd);
			float a = 0.8f;
			float h = 0.01f;
			DBAdapterParabel dbap;
			dbap = new DBAdapterParabel(gd);
			Node[] result;
			while(true) {
				dbap.fillGraphData(startNodeID, goalNodeID, a, h);
				try {
					result = as.findFastestPath(startNodeID, goalNodeID, motorwaySpeed, primarySpeed, residentialSpeed);
					break;
				} catch (PathNotFoundException e) {
					a *= 0.5f;
					h += 0.001f;
					System.out.println(a);
					if (a <= 0.01) {
						throw new PathNotFoundException();
					}
				}
			}
			float[] nodeLats = new float[result.length];
			float[] nodeLons = new float[result.length];
			for(int i=0; i<nodeLats.length;i++){
				nodeLats[i] = result[i].getLat();
				nodeLons[i] = result[i].getLon();
			}
			PathData pd = new PathData();
			pd.setNodeLats(nodeLats);
			pd.setNodeLons(nodeLons);
			return pd;
		}catch(java.sql.SQLException e){
			throw new SQLException();
		}
	}

}
