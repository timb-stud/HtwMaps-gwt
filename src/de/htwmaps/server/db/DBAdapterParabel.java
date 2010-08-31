package de.htwmaps.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.htwmaps.server.FindPathServiceImpl;
import de.htwmaps.server.algorithm.GraphData;
import de.htwmaps.shared.exceptions.MySQLException;

/**
 *
 * @author Stanislaw Tartakowski
 *
 * Diese Klasse stellt dem Suchalgorithmus Knoten aus der Datenbank bereit,
 * die in einer von 2 Parabeln begrenzter Fl�che liegen. Die Form aehnelt einer Ellipse, die Implementierung
 * ist jedoch performanter
 *
 */
/**
 * @author bline
 * 
 */
public class DBAdapterParabel {
	private float startNodeLon, startNodeLat, endNodeLon, endNodeLat;
	private float h;
	private float a;
	// Nodes
	private int[] nodeIDs;
	private float[] nodeLons; // x
	private float[] nodeLats; // y
	// Edges
	private int[] edgeStartNodeIDs;
	private int[] edgeEndNodeIDs;
	private double[] edgeLengths;
	private boolean[] oneways;
	private int[] highwayTypes;
	private int[] wayIDs;
	private int[] edgeIDs;

	private int edgesCount;
	private int nodesCount;
	private long receiveNodesTime;
	private long receiveEdgesTime;

	private String NODE_SELECT;
	private String EDGE_SELECT;

	private GraphData gd;

	private final static String COORD_SELECT = "SELECT lat, lon FROM nodes_opt WHERE ";

	private boolean printNodeCoords;

	private int option;

	public DBAdapterParabel(GraphData gd) {
		if (gd == null) {
			throw new IllegalArgumentException("Graph data must not be null");
		}
		this.gd = gd;
	}

	public void fillGraphData(int startID, int goalID, float a, float h,
			int option) throws SQLException, MySQLException {
		this.a = a;
		this.h = h;
		this.option = option;
		Connection con = DBConnector.getConnection();
		ResultSet resultSet = con.createStatement().executeQuery(
				buildCoordSelectStatement(startID, goalID));
		resultSet.next();
		startNodeLat = resultSet.getFloat(1);
		startNodeLon = resultSet.getFloat(2);
		resultSet.next();
		endNodeLat = resultSet.getFloat(1);
		endNodeLon = resultSet.getFloat(2);

		setParabel();
		long time = System.currentTimeMillis();
		initNodes();
		receiveNodesTime = System.currentTimeMillis() - time;
		time = System.currentTimeMillis();
		initEdges();
		receiveEdgesTime = System.currentTimeMillis() - time;
		nodesCount = nodeIDs.length;
		edgesCount = edgeStartNodeIDs.length;
		gd.build(nodeIDs, nodeLats, nodeLons, wayIDs, edgeStartNodeIDs,
				edgeEndNodeIDs, edgeLengths, oneways, highwayTypes, edgeIDs);
	}

	private String buildCoordSelectStatement(int node1Id, int node2Id) {
		StringBuilder sb = new StringBuilder(COORD_SELECT);
		sb.append(" (id = ").append(node1Id).append(" OR id = ")
				.append(node2Id).append(")");

		return sb.toString();
	}

	private void initNodes() throws SQLException, MySQLException {
		int tableLength;
		Connection con = DBConnector.getConnection();
		PreparedStatement pStmt = con.prepareStatement(NODE_SELECT);
		pStmt.setFloat(1, a);
		pStmt.setFloat(2, (endNodeLat - startNodeLat));
		pStmt.setFloat(3, endNodeLon - startNodeLon);
		pStmt.setFloat(4, startNodeLon);
		pStmt.setFloat(5, startNodeLat);
		pStmt.setFloat(6, h);
		pStmt.setFloat(7, a);
		pStmt.setFloat(8, (startNodeLat - endNodeLat));
		pStmt.setFloat(9, startNodeLon - endNodeLon);
		pStmt.setFloat(10, endNodeLon);
		pStmt.setFloat(11, endNodeLat);
		pStmt.setFloat(12, h);

		ResultSet resultSet = pStmt.executeQuery();
		if (printNodeCoords) {
			while (resultSet.next()) {
				System.out.println(resultSet.getFloat(3) + "\t"
						+ resultSet.getFloat(2) + "\t" + "title\t" + "descr\t"
						+ "rosa_punkt.png\t" + "8,8\t" + "0,0");
			}
		}
		resultSet.last();
		tableLength = resultSet.getRow();
		resultSet.beforeFirst();
		nodeIDs = new int[tableLength];
		nodeLons = new float[tableLength];
		nodeLats = new float[tableLength];

		for (int i = 0; resultSet.next(); i++) {
			nodeIDs[i] = resultSet.getInt(1);
			nodeLons[i] = resultSet.getFloat(2);
			nodeLats[i] = resultSet.getFloat(3);
		}
	}

	private void initEdges() throws SQLException, MySQLException {
		int tableLength;
		Connection con = DBConnector.getConnection();
		PreparedStatement pStmt = con.prepareStatement(EDGE_SELECT);
		pStmt.setFloat(1, a);
		pStmt.setFloat(2, (endNodeLat - startNodeLat));
		pStmt.setFloat(3, endNodeLon - startNodeLon);
		pStmt.setFloat(4, startNodeLon);
		pStmt.setFloat(5, startNodeLat);
		pStmt.setFloat(6, h);
		pStmt.setFloat(7, a);
		pStmt.setFloat(8, (startNodeLat - endNodeLat));
		pStmt.setFloat(9, startNodeLon - endNodeLon);
		pStmt.setFloat(10, endNodeLon);
		pStmt.setFloat(11, endNodeLat);
		pStmt.setFloat(12, h);
		pStmt.setFloat(13, a);
		pStmt.setFloat(14, (endNodeLat - startNodeLat));
		pStmt.setFloat(15, endNodeLon - startNodeLon);
		pStmt.setFloat(16, startNodeLon);
		pStmt.setFloat(17, startNodeLat);
		pStmt.setFloat(18, h);
		pStmt.setFloat(19, a);
		pStmt.setFloat(20, (startNodeLat - endNodeLat));
		pStmt.setFloat(21, startNodeLon - endNodeLon);
		pStmt.setFloat(22, endNodeLon);
		pStmt.setFloat(23, endNodeLat);
		pStmt.setFloat(24, h);
		ResultSet resultSet = pStmt.executeQuery();
		pStmt = null;
		resultSet.last();
		tableLength = resultSet.getRow();
		resultSet.beforeFirst();
		edgeStartNodeIDs = new int[tableLength];
		edgeEndNodeIDs = new int[tableLength];
		edgeLengths = new double[tableLength];
		oneways = new boolean[tableLength];
		highwayTypes = new int[tableLength];
		wayIDs = new int[tableLength];
		edgeIDs = new int[tableLength];

		for (int i = 0; resultSet.next(); i++) {
			edgeStartNodeIDs[i] = resultSet.getInt(1);
			edgeEndNodeIDs[i] = resultSet.getInt(2);
			oneways[i] = resultSet.getBoolean(3);
			highwayTypes[i] = resultSet.getInt(4);
			edgeLengths[i] = resultSet.getDouble(5);
			wayIDs[i] = resultSet.getInt(6);
			edgeIDs[i] = resultSet.getInt(7);
		}
	}

	private void setParabel() {
		String speedID = "";
		if (option == FindPathServiceImpl.FASTEST) {
			speedID = " or speedID < 6 ";
		}
		if (startNodeLat < endNodeLat) {
			// ps(x) = a (ey - sy) / (ex - sx)� (x - sx)� + sy - h
			// pe(x) = a (sy - ey) / (sx - ex)� (x - ex)� + ey + h
			NODE_SELECT = "select varNodes.id, varNodes.lon, varNodes.lat from nodes_opt varNodes "
					+ " where "
					+ " ? *(?/POW((?),2))*POW((varNodes.lon - ?),2) + ? - ? <= varNodes.lat "
					+ " and "
					+ " ? *(?/POW((?),2))*POW((varNodes.lon - ?),2) + ? + ? >= varNodes.lat"
					+ speedID;
			EDGE_SELECT = "select node1ID, node2ID, isoneway, speedID, length, wayid, id from edges_opt"
					+ " where"
					+ " ?*((?)/POW((?),2))*POW((node1lon - ?),2) + ? - ? <= node1lat"
					+ " and"
					+ " ?*((?)/POW((?),2))*POW((node1lon - ?),2) + ? + ? >= node1lat"
					+ " and"
					+ " ?*((?)/POW((?),2))*POW((node2lon - ?),2) + ? - ? <= node2lat"
					+ " and"
					+ " ?*((?)/POW((?),2))*POW((node2lon - ?),2) + ? + ? >= node2lat"
					+ speedID;
		} else {
			// ps(x) = a (ey - sy) / (ex - sx)� (x - sx)� + sy + h
			// pe(x) = a (sy - ey) / (sx - ex)� (x - ex)� + ey - h
			NODE_SELECT = "select varNodes.id, varNodes.lon, varNodes.lat from nodes_opt varNodes "
					+ " where "
					+ " ? *(?/POW((?),2))*POW((varNodes.lon - ?),2) + ? + ? >= varNodes.lat "
					+ " and "
					+ " ? *(?/POW((?),2))*POW((varNodes.lon - ?),2) + ? - ? <= varNodes.lat"
					+ speedID;
			EDGE_SELECT = "select node1ID, node2ID, isoneway, speedID, length, wayid, id from edges_opt"
					+ " where"
					+ " ?*((?)/POW((?),2))*POW((node1lon - ?),2) + ? + ? >= node1lat"
					+ " and"
					+ " ?*((?)/POW((?),2))*POW((node1lon - ?),2) + ? - ? <= node1lat"
					+ " and"
					+ " ?*((?)/POW((?),2))*POW((node2lon - ?),2) + ? + ? >= node2lat"
					+ " and"
					+ " ?*((?)/POW((?),2))*POW((node2lon - ?),2) + ? - ? <= node2lat"
					+ speedID;
		}
	}

	public void printNodes() {
		this.printNodeCoords = true;
	}

	public int getEdgesCount() {
		return edgesCount;
	}

	public void setEdgesCount(int edgesCount) {
		this.edgesCount = edgesCount;
	}

	public int getNodesCount() {
		return nodesCount;
	}

	public void setNodesCount(int nodesCount) {
		this.nodesCount = nodesCount;
	}

	public long getReceiveNodesTime() {
		return receiveNodesTime;
	}

	public void setReceiveNodesTime(long receiveNodesTime) {
		this.receiveNodesTime = receiveNodesTime;
	}

	public long getReceiveEdgesTime() {
		return receiveEdgesTime;
	}

	public void setReceiveEdgesTime(long receiveEdgesTime) {
		this.receiveEdgesTime = receiveEdgesTime;
	}
}