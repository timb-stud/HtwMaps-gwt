package de.htwmaps.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import de.htwmaps.server.algorithm.Edge;
import de.htwmaps.server.algorithm.Node;
import de.htwmaps.shared.exceptions.MySQLException;
import de.htwmaps.shared.exceptions.NodeNotFoundException;

public class DBUtils {

	private final static String GETNODEID_SELECT = "SELECT startNodeID FROM ways WHERE (cityName = ? OR is_in LIKE ?) AND nameValue = ?";
	private final static String GETCITIESSTARTWITH_SELECT = "SELECT DISTINCT cityName FROM ways WHERE cityName LIKE ?";
	private final static String GETSTREETSSTARTWITH_SELECT = "SELECT DISTINCT nameValue, cityName FROM ways WHERE (cityName = ? OR is_in LIKE ?) AND nameValue LIKE ?";

	private DBUtils() {
	}

	public static int getNodeId(String city, String street)
			throws SQLException, NodeNotFoundException, MySQLException {
		Connection con = DBConnector.getConnection();
		PreparedStatement select = con.prepareStatement(GETNODEID_SELECT);
		select.setString(1, city);
		select.setString(2, "%" + city + "%");
		select.setString(3, street);
		ResultSet rs = select.executeQuery();
		if(!rs.next()) {
			return -1;
		}
		int nodeID = rs.getInt(1);
		select.close();
		con.close();
		return nodeID;
	}

	public static String[] getCitiesStartsWith(String s) throws SQLException,
			MySQLException {
		Connection con = DBConnector.getConnection();
		PreparedStatement select = con
				.prepareStatement(GETCITIESSTARTWITH_SELECT);
		select.setString(1, s + "%");
		ResultSet rs = select.executeQuery();
		if (!rs.next())
			return null;
		rs.last();
		int tableLength = rs.getRow();
		rs.beforeFirst();
		String[] result = new String[tableLength];
		for (int i = 0; rs.next(); i++) {
			result[i] = rs.getString(1);
		}
		select.close();
		con.close();
		return result;
	}

	public static String[] getStreetsStartsWith(String city, String s)
			throws SQLException, MySQLException {
		if (city.equals("") || city == null) {
			return null;
		}
		Connection con = DBConnector.getConnection();
		PreparedStatement select = con
				.prepareStatement(GETSTREETSSTARTWITH_SELECT);
		select.setString(1, city);
		select.setString(2, "%" + city + "%");
		select.setString(3, s + "%");
		ResultSet rs = select.executeQuery();
		if (!rs.next())
			return null;
		rs.last();
		int tableLength = rs.getRow();
		rs.beforeFirst();
		String[] result = new String[tableLength];
		for (int i = 0; rs.next(); i++) {
			result[i] = rs.getString("nameValue") + ","
					+ rs.getString("cityName");
			select.setString(1, s + "%");
		}
		select.close();
		con.close();
		return result;
	}

	public static float[][] getAllNodeLatLons(Node[] nodes, Edge[] edges) throws SQLException,
			MySQLException {
		String sqlLatLonsAsc 	= "SELECT node1lat, node1lon, node2lat, node2lon, ID FROM edges_all WHERE partOfEdgesOptID = ? ORDER BY 5";
		String sqlLatLonsDesc 	= "SELECT node1lat, node1lon, node2lat, node2lon, ID FROM edges_all WHERE partOfEdgesOptID = ? ORDER BY 5 DESC";
		String sqlDirection 	= "SELECT COUNT(*) FROM edges_opt WHERE node1ID = ? AND node2ID = ?";

		Connection con = DBConnector.getConnection();
		PreparedStatement psLatLonsAsc, psLatLonsDesc, psDirection;
		psLatLonsAsc 	= con.prepareStatement(sqlLatLonsAsc);
		psLatLonsDesc 	= con.prepareStatement(sqlLatLonsDesc);
		psDirection = con.prepareStatement(sqlDirection);
		ResultSet rsLatLons = null;
		ResultSet rsDirection = null;
		
		LinkedList<float[]> latLonList;
		latLonList = new LinkedList<float[]>();

		int myEdgeID = 0;
		int rsCounter = 0;
		int edgeTreffer = 0;
		int dbTreffer = 0;
		long time = 0;
		long timeData = 0;
		long timeDir = 0;
		
		// Variable die speichert ob Strasse vorwaerts oder rueckwaerts
		// durchfahren wird
		boolean inOrder;

		System.out.println("Size Opt: " + nodes.length);
		for (int i = 0; i < nodes.length - 1; i++) {
			inOrder = true;
			if ((i < edges.length - 2) && (edges[i].getWayID() == edges[i+1].getWayID())) {
				if (edges[i].getID() > edges[i+1].getID()) {
					inOrder = false;
				}
				edgeTreffer++;
			} else {
				psDirection.setInt(1, nodes[i].getId());
				psDirection.setInt(2, nodes[i + 1].getId());
				time = System.currentTimeMillis();
				rsDirection = psDirection.executeQuery();
				timeDir = timeDir + (System.currentTimeMillis() - time);
				while (rsDirection.next()) {
					if (rsDirection.getInt(1) == 1) {
						inOrder = true;
					} else {
						inOrder = false;
					}
				}
				dbTreffer++;
			}
			myEdgeID = edges[i].getID();
			psLatLonsAsc.setInt(1, myEdgeID);
			psLatLonsDesc.setInt(1, myEdgeID);
			time = System.currentTimeMillis();
			if (inOrder) {
				rsLatLons = psLatLonsAsc.executeQuery();
			} else {
				rsLatLons = psLatLonsDesc.executeQuery();
			}
			timeData = timeData + (System.currentTimeMillis() - time);
			rsCounter = 0;
			while (rsLatLons.next()) {
				float[] latLon = new float[2];
				latLon[0] = rsLatLons.getFloat(1);
				latLon[1] = rsLatLons.getFloat(2);
				latLonList.add(latLon);
				if (rsLatLons.isLast() && rsCounter >= 1) {
					float[] latLon2 = new float[2];
					latLon2[0] = rsLatLons.getFloat(3);
					latLon2[1] = rsLatLons.getFloat(4);
					latLonList.add(latLon2);
				}
				rsCounter++;
			}

		}
		System.out.println("DB-Abfragen Richtung " + timeDir + "ms");
		System.out.println("DB-Abfragen Daten " + timeData + "ms");
		System.out.println("Edgetreffer " + edgeTreffer + " DBTreffer " + dbTreffer);
		psLatLonsAsc.close();
		psLatLonsDesc.close();
		psDirection.close();
		con.close();
		float[][] latLonArray = new float[2][latLonList.size()];
		int listCount = 0;
		for (float[] latLon : latLonList) {
			latLonArray[0][listCount] = latLon[0];
			latLonArray[1][listCount] = latLon[1];
			listCount++;
		}
		System.out.println("Size All: " + latLonArray[1].length);
		return latLonArray;
	}

}
