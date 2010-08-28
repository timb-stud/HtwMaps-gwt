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
public class DBAdapterParabel{
	private float startNodeLon, startNodeLat, endNodeLon, endNodeLat;
	private float h;
	//Nodes
	private int[] nodeIDs;
	private float[] nodeLons; //x
	private float[] nodeLats; //y
	//Edges
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
	
	public void fillGraphData(int startID, int goalID, float h, int option) throws SQLException, MySQLException{
		this.h = h;
		this.option = option;
		Connection con = DBConnector.getConnection();
		ResultSet resultSet = con.createStatement().executeQuery(buildCoordSelectStatement(startID, goalID));
		resultSet.next();
		startNodeLat = resultSet.getFloat(1);
		startNodeLon = resultSet.getFloat(2);
		resultSet.next();
		endNodeLat = resultSet.getFloat(1);
		endNodeLon = resultSet.getFloat(2);
		con.close();
		
		setParabel();
		long time = System.currentTimeMillis();
		initNodes();
		receiveNodesTime = System.currentTimeMillis() - time;
		time = System.currentTimeMillis();
		initEdges();
		receiveEdgesTime = System.currentTimeMillis() - time;
		nodesCount = nodeIDs.length;
		edgesCount = edgeStartNodeIDs.length;
		gd.build(nodeIDs, nodeLats, nodeLons, wayIDs, edgeStartNodeIDs, edgeEndNodeIDs, edgeLengths, oneways, highwayTypes, edgeIDs);
	}

	private String buildCoordSelectStatement(int node1Id, int node2Id) {
		StringBuilder sb = new StringBuilder(COORD_SELECT);
		sb.append(" (id = ").append(node1Id)
		.append(" OR id = ").append(node2Id).append(")");
		
		return sb.toString();
	}

	private void initNodes() throws SQLException, MySQLException{
		int tableLength;
		float bigh = 0.8f;
		
		Connection con = DBConnector.getConnection();
		PreparedStatement pStmt = con.prepareStatement(NODE_SELECT);
		float m = (startNodeLat - endNodeLat)/(startNodeLon - endNodeLon);
		float mReversed = (startNodeLon - endNodeLon)/(startNodeLat - endNodeLat);
		pStmt.setFloat(1, m);
		pStmt.setFloat(2, startNodeLat);
		pStmt.setFloat(3, m);
		pStmt.setFloat(4, startNodeLon);
		pStmt.setFloat(5, h);
		pStmt.setFloat(6, h);		
		pStmt.setFloat(7, m);
		pStmt.setFloat(8, startNodeLat);
		pStmt.setFloat(9, m);
		pStmt.setFloat(10, startNodeLon);
		pStmt.setFloat(11, h);
		pStmt.setFloat(12, h);
		pStmt.setFloat(13, mReversed);
		pStmt.setFloat(14, startNodeLat);
		pStmt.setFloat(15, mReversed);
		pStmt.setFloat(16, startNodeLon);
		pStmt.setFloat(17, h);
		pStmt.setFloat(18, h);
		pStmt.setFloat(19, mReversed);
		pStmt.setFloat(20, endNodeLat);
		pStmt.setFloat(21, mReversed);
		pStmt.setFloat(22, endNodeLon);
		pStmt.setFloat(23, h);
		pStmt.setFloat(24, h);
		
		//or
		if (option == FindPathServiceImpl.FASTEST) {
			pStmt.setFloat(25, m);
			pStmt.setFloat(26, startNodeLat);
			pStmt.setFloat(27, m);
			pStmt.setFloat(28, startNodeLon);
			pStmt.setFloat(29, bigh);
			pStmt.setFloat(30, bigh);		
			pStmt.setFloat(31, m);
			pStmt.setFloat(32, startNodeLat);
			pStmt.setFloat(33, m);
			pStmt.setFloat(34, startNodeLon);
			pStmt.setFloat(35, bigh);
			pStmt.setFloat(36, bigh);
			pStmt.setFloat(37, mReversed);
			pStmt.setFloat(38, startNodeLat);
			pStmt.setFloat(39, mReversed);
			pStmt.setFloat(40, startNodeLon);
			pStmt.setFloat(41, bigh);
			pStmt.setFloat(42, bigh);
			pStmt.setFloat(43, mReversed);
			pStmt.setFloat(44, endNodeLat);
			pStmt.setFloat(45, mReversed);
			pStmt.setFloat(46, endNodeLon);
			pStmt.setFloat(47, bigh);
			pStmt.setFloat(48, bigh);
		}
		
		ResultSet resultSet = pStmt.executeQuery();
		if (printNodeCoords) {
			while (resultSet.next()) {
				System.out.println(resultSet.getFloat(3)+"\t"+resultSet.getFloat(2)+"\t"+"title\t"+"descr\t"+"rosa_punkt.png\t"+"8,8\t"+"0,0");
			}
		}
		resultSet.last();
		tableLength = resultSet.getRow();
		resultSet.beforeFirst();
		nodeIDs = new int[tableLength];
		nodeLons = new float[tableLength];
		nodeLats = new float[tableLength];
		
		for (int i = 0; resultSet.next(); i++){
			nodeIDs[i] = resultSet.getInt(1);
			nodeLons[i] = resultSet.getFloat(2);
			nodeLats[i] = resultSet.getFloat(3);
		}
		pStmt.close();
		con.close();
	}

	private void initEdges() throws SQLException, MySQLException{
		int tableLength;
		float m = (startNodeLat - endNodeLat)/(startNodeLon - endNodeLon);
		float mReversed = (startNodeLon - endNodeLon)/(startNodeLat - endNodeLat);
		float bigh = 0.8f;
		
		Connection con = DBConnector.getConnection();
		PreparedStatement pStmt = con.prepareStatement(EDGE_SELECT);
		pStmt.setFloat(1, m);
		pStmt.setFloat(2, startNodeLat);
		pStmt.setFloat(3, m);
		pStmt.setFloat(4, startNodeLon);
		pStmt.setFloat(5, h);
		pStmt.setFloat(6, h);		
		pStmt.setFloat(7, m);
		pStmt.setFloat(8, startNodeLat);
		pStmt.setFloat(9, m);
		pStmt.setFloat(10, startNodeLon);
		pStmt.setFloat(11, h);
		pStmt.setFloat(12, h);
		pStmt.setFloat(13, mReversed);
		pStmt.setFloat(14, startNodeLat);
		pStmt.setFloat(15, mReversed);
		pStmt.setFloat(16, startNodeLon);
		pStmt.setFloat(17, h);
		pStmt.setFloat(18, h);
		pStmt.setFloat(19, mReversed);
		pStmt.setFloat(20, endNodeLat);
		pStmt.setFloat(21, mReversed);
		pStmt.setFloat(22, endNodeLon);
		pStmt.setFloat(23, h);
		pStmt.setFloat(24, h);
		pStmt.setFloat(25, m);
		pStmt.setFloat(26, startNodeLat);
		pStmt.setFloat(27, m);
		pStmt.setFloat(28, startNodeLon);
		pStmt.setFloat(29, h);
		pStmt.setFloat(30, h);		
		pStmt.setFloat(31, m);
		pStmt.setFloat(32, startNodeLat);
		pStmt.setFloat(33, m);
		pStmt.setFloat(34, startNodeLon);
		pStmt.setFloat(35, h);
		pStmt.setFloat(36, h);
		pStmt.setFloat(37, mReversed);
		pStmt.setFloat(38, startNodeLat);
		pStmt.setFloat(39, mReversed);
		pStmt.setFloat(40, startNodeLon);
		pStmt.setFloat(41, h);
		pStmt.setFloat(42, h);
		pStmt.setFloat(43, mReversed);
		pStmt.setFloat(44, endNodeLat);
		pStmt.setFloat(45, mReversed);
		pStmt.setFloat(46, endNodeLon);
		pStmt.setFloat(47, h);
		pStmt.setFloat(48, h);
		
		//or
		if (option == FindPathServiceImpl.FASTEST) {
			pStmt.setFloat(49, m);
			pStmt.setFloat(50, startNodeLat);
			pStmt.setFloat(51, m);
			pStmt.setFloat(52, startNodeLon);
			pStmt.setFloat(53, bigh);
			pStmt.setFloat(54, bigh);		
			pStmt.setFloat(55, m);
			pStmt.setFloat(56, startNodeLat);
			pStmt.setFloat(57, m);
			pStmt.setFloat(58, startNodeLon);
			pStmt.setFloat(59, bigh);
			pStmt.setFloat(60, bigh);
			pStmt.setFloat(61, mReversed);
			pStmt.setFloat(62, startNodeLat);
			pStmt.setFloat(63, mReversed);
			pStmt.setFloat(64, startNodeLon);
			pStmt.setFloat(65, bigh);
			pStmt.setFloat(66, bigh);
			pStmt.setFloat(67, mReversed);
			pStmt.setFloat(68, endNodeLat);
			pStmt.setFloat(69, mReversed);
			pStmt.setFloat(70, endNodeLon);
			pStmt.setFloat(71, bigh);
			pStmt.setFloat(72, bigh);
			pStmt.setFloat(73, m);
			pStmt.setFloat(74, startNodeLat);
			pStmt.setFloat(75, m);
			pStmt.setFloat(76, startNodeLon);
			pStmt.setFloat(77, bigh);
			pStmt.setFloat(78, bigh);		
			pStmt.setFloat(79, m);
			pStmt.setFloat(80, startNodeLat);
			pStmt.setFloat(81, m);
			pStmt.setFloat(82, startNodeLon);
			pStmt.setFloat(83, bigh);
			pStmt.setFloat(84, bigh);
			pStmt.setFloat(85, mReversed);
			pStmt.setFloat(86, startNodeLat);
			pStmt.setFloat(87, mReversed);
			pStmt.setFloat(88, startNodeLon);
			pStmt.setFloat(89, bigh);
			pStmt.setFloat(90, bigh);
			pStmt.setFloat(91, mReversed);
			pStmt.setFloat(92, endNodeLat);
			pStmt.setFloat(93, mReversed);
			pStmt.setFloat(94, endNodeLon);
			pStmt.setFloat(95, bigh);
			pStmt.setFloat(96, bigh);
		};
		
		ResultSet resultSet = pStmt.executeQuery();
//		pStmt = null;
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
		
		for (int i = 0; resultSet.next(); i++){
			edgeStartNodeIDs[i] = resultSet.getInt(1);
			edgeEndNodeIDs[i] = resultSet.getInt(2);
			oneways[i] = resultSet.getBoolean(3);
			highwayTypes[i] = resultSet.getInt(4);
			edgeLengths[i] = resultSet.getDouble(5);
			wayIDs[i] = resultSet.getInt(6);	
			edgeIDs[i] = resultSet.getInt(7);
		}
		pStmt.close();
		con.close();
	}

	private void setParabel() {
		if (startNodeLat>endNodeLat && startNodeLon >endNodeLon) {
			NODE_SELECT = "select varNodes.id, varNodes.lon, varNodes.lat from nodes_opt varNodes "
				+ " where "
				+ " (varNodes.lon*?+?-?*(?+?)-? < varNodes.lat "
				+ " and "
				+ " varNodes.lon*?+?-?*(?-?)+? > varNodes.lat "
				+ " and "
				+ " -varNodes.lon*?+?+?*(?+?)+? > varNodes.lat "
				+ " and "
				+ " -varNodes.lon*?+?+?*(?-?)-? < varNodes.lat "
				+ " ) ";
			if (option == FindPathServiceImpl.FASTEST) {
				NODE_SELECT += " or ( "
				+ " varNodes.lon*?+?-?*(?+?)-? < varNodes.lat "
				+ " and "
				+ " varNodes.lon*?+?-?*(?-?)+? > varNodes.lat "
				+ " and "
				+ " -varNodes.lon*?+?+?*(?+?)+? > varNodes.lat "
				+ " and "
				+ " -varNodes.lon*?+?+?*(?-?)-? < varNodes.lat and speedID = 1)";
			}

				
			EDGE_SELECT = "select node1ID, node2ID, isoneway, speedID, length, wayid, id from edges_opt"
				+ " where " 
				+ " (node1lon*?+?-?*(?+?)-? < node1lat "
				+ " and "
				+ " node1lon*?+?-?*(?-?)+? > node1lat "
				+ " and "
				+ " -node1lon*?+?+?*(?+?)+? > node1lat "
				+ " and "
				+ " -node1lon*?+?+?*(?-?)-? < node1lat "
				+ " and "
				+ " node2lon*?+?-?*(?+?)-? < node2lat "
				+ " and "
				+ " node2lon*?+?-?*(?-?)+? > node2lat "
				+ " and "
				+ " -node2lon*?+?+?*(?+?)+? > node2lat "
				+ " and "
				+ " -node2lon*?+?+?*(?-?)-? < node2lat "
				+ " ) ";
			if (option == FindPathServiceImpl.FASTEST) {
				EDGE_SELECT += " or ( "
				+ " node1lon*?+?-?*(?+?)-? < node1lat "
				+ " and "
				+ " node1lon*?+?-?*(?-?)+? > node1lat "
				+ " and "
				+ " -node1lon*?+?+?*(?+?)+? > node1lat "
				+ " and "
				+ " -node1lon*?+?+?*(?-?)-? < node1lat "
				+ " and "
				+ " node2lon*?+?-?*(?+?)-? < node2lat "
				+ " and "
				+ " node2lon*?+?-?*(?-?)+? > node2lat "
				+ " and "
				+ " -node2lon*?+?+?*(?+?)+? > node2lat "
				+ " and "
				+ " -node2lon*?+?+?*(?-?)-? < node2lat and speedID = 1) ";
			}
		} else {
			if (startNodeLat < endNodeLat && startNodeLon < endNodeLon) {
				NODE_SELECT = "select varNodes.id, varNodes.lon, varNodes.lat from nodes_opt varNodes "
					+ " where "
					+ " (varNodes.lon*?+?-?*(?-?)+? > varNodes.lat "
					+ " and "
					+ " varNodes.lon*?+?-?*(?+?)-? < varNodes.lat "
					+ " and "
					+ " -varNodes.lon*?+?+?*(?-?)-? < varNodes.lat "
					+ " and "
					+ " -varNodes.lon*?+?+?*(?+?)+? > varNodes.lat "
					+ " )";
				if (option == FindPathServiceImpl.FASTEST) {
					NODE_SELECT += " or ( "
					+ " varNodes.lon*?+?-?*(?-?)+? > varNodes.lat "
					+ " and "
					+ " varNodes.lon*?+?-?*(?+?)-? < varNodes.lat "
					+ " and "
					+ " -varNodes.lon*?+?+?*(?-?)-? < varNodes.lat "
					+ " and "
					+ " -varNodes.lon*?+?+?*(?+?)+? > varNodes.lat and speedID = 1)";
				}
					
				EDGE_SELECT = "select node1ID, node2ID, isoneway, speedID, length, wayid, id from edges_opt"
					+ " where " 
					+ " (node1lon*?+?-?*(?-?)+? > node1lat "
					+ " and "
					+ " node1lon*?+?-?*(?+?)-? < node1lat "
					+ " and "
					+ " -node1lon*?+?+?*(?-?)-? < node1lat "
					+ " and "
					+ " -node1lon*?+?+?*(?+?)+? > node1lat "
					+ " and "
					+ " node2lon*?+?-?*(?-?)+? > node2lat "
					+ " and "
					+ " node2lon*?+?-?*(?+?)-? < node2lat "
					+ " and "
					+ " -node2lon*?+?+?*(?-?)-? < node2lat "
					+ " and "
					+ " -node2lon*?+?+?*(?+?)+? > node2lat "
					+ " )";
				if (option == FindPathServiceImpl.FASTEST) {
					EDGE_SELECT += " or ( "
					+ " node1lon*?+?-?*(?-?)+? > node1lat "
					+ " and "
					+ " node1lon*?+?-?*(?+?)-? < node1lat "
					+ " and "
					+ " -node1lon*?+?+?*(?-?)-? < node1lat "
					+ " and "
					+ " -node1lon*?+?+?*(?+?)+? > node1lat "
					+ " and "
					+ " node2lon*?+?-?*(?-?)+? > node2lat "
					+ " and "
					+ " node2lon*?+?-?*(?+?)-? < node2lat "
					+ " and "
					+ " -node2lon*?+?+?*(?-?)-? < node2lat "
					+ " and "
					+ " -node2lon*?+?+?*(?+?)+? > node2lat and speedID = 1)";
				}
			} else {
				if (startNodeLat>endNodeLat && startNodeLon < endNodeLon) {
					NODE_SELECT = "select varNodes.id, varNodes.lon, varNodes.lat from nodes_opt varNodes "
						+ " where "
						+ " (varNodes.lon*?+?-?*(?-?)-? < varNodes.lat "
						+ " and "
						+ " varNodes.lon*?+?-?*(?+?)+? > varNodes.lat "
						+ " and "
						+ " -varNodes.lon*?+?+?*(?-?)+? > varNodes.lat "
						+ " and "
						+ " -varNodes.lon*?+?+?*(?+?)-? < varNodes.lat "
						+ " )";
					if (option == FindPathServiceImpl.FASTEST) {
						NODE_SELECT += " or ( "
						+ " varNodes.lon*?+?-?*(?-?)-? < varNodes.lat "
						+ " and "
						+ " varNodes.lon*?+?-?*(?+?)+? > varNodes.lat "
						+ " and "
						+ " -varNodes.lon*?+?+?*(?-?)+? > varNodes.lat "
						+ " and "
						+ " -varNodes.lon*?+?+?*(?+?)-? < varNodes.lat and speedID = 1)";
					}
						
					EDGE_SELECT = "select node1ID, node2ID, isoneway, speedID, length, wayid, id from edges_opt"
						+ " where " 
						+ " (node1lon*?+?-?*(?-?)-? < node1lat "
						+ " and "
						+ " node1lon*?+?-?*(?+?)+? > node1lat "
						+ " and "
						+ " -node1lon*?+?+?*(?-?)+? > node1lat "
						+ " and "
						+ " -node1lon*?+?+?*(?+?)-? < node1lat "
						+ " and "
						+ " node2lon*?+?-?*(?-?)-? < node2lat "
						+ " and "
						+ " node2lon*?+?-?*(?+?)+? > node2lat "
						+ " and "
						+ " -node2lon*?+?+?*(?-?)+? > node2lat "
						+ " and "
						+ " -node2lon*?+?+?*(?+?)-? < node2lat "
						+ " )";
					if (option == FindPathServiceImpl.FASTEST) {
						EDGE_SELECT += " or ( "
						+ " node1lon*?+?-?*(?-?)-? < node1lat "
						+ " and "
						+ " node1lon*?+?-?*(?+?)+? > node1lat "
						+ " and "
						+ " -node1lon*?+?+?*(?-?)+? > node1lat "
						+ " and "
						+ " -node1lon*?+?+?*(?+?)-? < node1lat "
						+ " and "
						+ " node2lon*?+?-?*(?-?)-? < node2lat "
						+ " and "
						+ " node2lon*?+?-?*(?+?)+? > node2lat "
						+ " and "
						+ " -node2lon*?+?+?*(?-?)+? > node2lat "
						+ " and "
						+ " -node2lon*?+?+?*(?+?)-? < node2lat and speedID = 1)";
					}
				} else {
					if (startNodeLat<endNodeLat&&startNodeLon>endNodeLon) {
						NODE_SELECT = "select varNodes.id, varNodes.lon, varNodes.lat from nodes_opt varNodes "
							+ " where "
							+ " (varNodes.lon*?+?-?*(?+?)+? > varNodes.lat "
							+ " and "
							+ " varNodes.lon*?+?-?*(?-?)-? < varNodes.lat "
							+ " and "
							+ " -varNodes.lon*?+?+?*(?+?)-? < varNodes.lat "
							+ " and "
							+ " -varNodes.lon*?+?+?*(?-?)+? > varNodes.lat "
							+ " )";
						if (option == FindPathServiceImpl.FASTEST) {
							NODE_SELECT += " or ( "
							+ " varNodes.lon*?+?-?*(?+?)+? > varNodes.lat "
							+ " and "
							+ " varNodes.lon*?+?-?*(?-?)-? < varNodes.lat "
							+ " and "
							+ " -varNodes.lon*?+?+?*(?+?)-? < varNodes.lat "
							+ " and "
							+ " -varNodes.lon*?+?+?*(?-?)+? > varNodes.lat and speedID = 1)";
						}
							
						EDGE_SELECT = "select node1ID, node2ID, isoneway, speedID, length, wayid, id from edges_opt"
							+ " where " 
							+ " (node1lon*?+?-?*(?+?)+? > node1lat "
							+ " and "
							+ " node1lon*?+?-?*(?-?)-? < node1lat "
							+ " and "
							+ " -node1lon*?+?+?*(?+?)-? < node1lat "
							+ " and "
							+ " -node1lon*?+?+?*(?-?)+? > node1lat "
							+ " and "
							+ " node2lon*?+?-?*(?+?)+? > node2lat "
							+ " and "
							+ " node2lon*?+?-?*(?-?)-? < node2lat "
							+ " and "
							+ " -node2lon*?+?+?*(?+?)-? < node2lat "
							+ " and "
							+ " -node2lon*?+?+?*(?-?)+? > node2lat "
							+ " )";
						if (option == FindPathServiceImpl.FASTEST) {
							EDGE_SELECT += " or ( "
							+ " node1lon*?+?-?*(?+?)+? > node1lat "
							+ " and "
							+ " node1lon*?+?-?*(?-?)-? < node1lat "
							+ " and "
							+ " -node1lon*?+?+?*(?+?)-? < node1lat "
							+ " and "
							+ " -node1lon*?+?+?*(?-?)+? > node1lat "
							+ " and "
							+ " node2lon*?+?-?*(?+?)+? > node2lat "
							+ " and "
							+ " node2lon*?+?-?*(?-?)-? < node2lat "
							+ " and "
							+ " -node2lon*?+?+?*(?+?)-? < node2lat "
							+ " and "
							+ " -node2lon*?+?+?*(?-?)+? > node2lat and speedID = 1)";
						}
					}
				}
			}
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
