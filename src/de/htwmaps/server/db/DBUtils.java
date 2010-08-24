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
		return rs.getInt(1);
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
		return result;
	}

	public static String[] getStreetsStartsWith(String city, String s)
			throws SQLException, MySQLException {
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
		return result;
	}

	public static float[][] getAllNodeLatLons(Node[] nodes, Edge[] edges) throws SQLException,
			MySQLException {
		String sql1 = "SELECT node1lat, node1lon, node2lat, node2lon, ID FROM edges_all WHERE partOfEdgesOptID = ? ORDER BY 5";
		String sql2 = "SELECT node1lat, node1lon, node2lat, node2lon, ID FROM edges_all WHERE partOfEdgesOptID = ? ORDER BY 5 DESC";
		String sql3 = "SELECT COUNT(*) FROM edges_opt WHERE node1ID = ? AND node2ID = ?";

		PreparedStatement ps1, ps2, ps3;
		LinkedList<float[]> latLonList;
		Connection con = DBConnector.getConnection();

		int myEdgeID = 0;
		int rsCounter = 0;

		long time = 0;
		long timesum = 0;
		long timesum2 = 0;
		// Variable die speichert ob Strasse vorwaerts oder rueckwaerts
		// durchfahren wird
		boolean inOrder = true;

		ps1 = con.prepareStatement(sql1);
		ps2 = con.prepareStatement(sql2);
		ps3 = con.prepareStatement(sql3);
		latLonList = new LinkedList<float[]>();

		ResultSet rs1 = null;
		ResultSet rs2 = null;
		System.out.println("Size Opt: " + nodes.length);
		for (int i = 0; i < nodes.length - 1; i++) {
			ps3.setInt(1, nodes[i].getId());
			ps3.setInt(2, nodes[i + 1].getId());
			time = System.currentTimeMillis();
			rs2 = ps3.executeQuery();
			timesum2 = timesum2 + (System.currentTimeMillis() - time);
			while (rs2.next()) {
				if (rs2.getInt(1) == 1) {
					inOrder = true;
				} else {
					inOrder = false;
				}
			}
			myEdgeID = edges[i].getID();
			ps1.setInt(1, myEdgeID);
			ps2.setInt(1, myEdgeID);
			time = System.currentTimeMillis();
			if (inOrder) {
				rs1 = ps1.executeQuery();
			} else {
				rs1 = ps2.executeQuery();
			}
			timesum = timesum + (System.currentTimeMillis() - time);
			rsCounter = 0;
			while (rs1.next()) {
				float[] latLon = new float[2];
				latLon[0] = rs1.getFloat(1);
				latLon[1] = rs1.getFloat(2);
				latLonList.add(latLon);
				if (rs1.isLast() && rsCounter >= 1) {
					float[] latLon2 = new float[2];
					latLon2[0] = rs1.getFloat(3);
					latLon2[1] = rs1.getFloat(4);
					latLonList.add(latLon2);
				}
				rsCounter++;
			}

		}
		System.out.println("DB-Abfragen Richtung " + timesum2 + "ms");
		System.out.println("DB-Abfragen Daten " + timesum + "ms");
		ps1.close();
		ps2.close();
		ps3.close();
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
