package de.htwmaps.server;


import java.util.LinkedList;

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

/**
 * Implementation der Routenberechnung.
 * 
 * @author Stanislaw Tartakowski, Thomas Altmeyer, Tim Bartsch
 *
 */
public class FindPathServiceImpl extends RemoteServiceServlet implements
		FindPathService {

	private static final long serialVersionUID = 1L;
	public static final int SHORTEST = 0;
	public static final int FASTEST = 1;
	
	private Node[] nodes;
	private AStarEdge[] edges;
	private String startStreet;
	private String goalStreet;

	
	/**
	 * Sucht den kuerzesten Weg zwischen start und ziel node mit dem A Star Algorithmus.
	 * 
	 * @param motorwaySpeed durchschnitts Autobahngeschwindigkeit
	 * @param primarySpeed durchschnitts Landstrassengeschwindigkeit
	 * @param residentialSpeed durchschnitts Innerortsgeschwindigkeit
	 * 
	 * @return ein {@link OptPathData} Objekt mit allen Informationen der Berechnung.
	 */
	@Override
	public OptPathData findShortestPathAStar(String[] cities, String[] streets, int motorwaySpeed,
			int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		GraphData gd = new GraphData();
		ShortestPathAlgorithm spa = new AStar(gd);
		return executeSearch(spa, gd, SHORTEST, cities, streets, motorwaySpeed, primarySpeed, residentialSpeed);
	}

	/**
	 * Sucht den schnellsten Weg zwischen start und ziel node mit dem A Star Algorithmus.
	 * 
	 * @param motorwaySpeed durchschnitts Autobahngeschwindigkeit
	 * @param primarySpeed durchschnitts Landstrassengeschwindigkeit
	 * @param residentialSpeed durchschnitts Innerortsgeschwindigkeit
	 * 
	 * @return ein {@link OptPathData} Objekt mit allen Informationen der Berechnung.
	 */
	@Override
	public OptPathData findFastestPathAStar(String[] cities, String[] streets, int motorwaySpeed,
			int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		GraphData gd = new GraphData();
		ShortestPathAlgorithm spa = new AStar(gd);
		return executeSearch(spa, gd, FASTEST, cities, streets, motorwaySpeed, primarySpeed, residentialSpeed);
	}

	/**
	 * Sucht den kuerzesten Weg zwischen start und ziel node mit dem bidirektionalen A Star Algorithmus.
	 * 
	 * @param motorwaySpeed durchschnitts Autobahngeschwindigkeit
	 * @param primarySpeed durchschnitts Landstrassengeschwindigkeit
	 * @param residentialSpeed durchschnitts Innerortsgeschwindigkeit
	 * 
	 * @return ein {@link OptPathData} Objekt mit allen Informationen der Berechnung.
	 */
	@Override
	public OptPathData findShortestPathAStarBi(String[] cities, String[] streets, int motorwaySpeed,
			int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		GraphData gd = new GraphData();
		ShortestPathAlgorithm spa = new AStarBiStarter(gd);
		return executeSearch(spa, gd, SHORTEST, cities, streets, motorwaySpeed, primarySpeed, residentialSpeed);
	}

	/**
	 * Sucht den schnellsten Weg zwischen start und ziel node mit dem bidirektionalen A Star Algorithmus.
	 * 
	 * @param motorwaySpeed durchschnitts Autobahngeschwindigkeit
	 * @param primarySpeed durchschnitts Landstrassengeschwindigkeit
	 * @param residentialSpeed durchschnitts Innerortsgeschwindigkeit
	 * 
	 * @return ein {@link OptPathData} Objekt mit allen Informationen der Berechnung.
	 */
	@Override
	public OptPathData findFastestPathAStarBi(String[] cities, String[] streets,
			int motorwaySpeed, int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException {
		GraphData gd = new GraphData();
		ShortestPathAlgorithm spa = new AStarBiStarter(gd);
		return executeSearch(spa, gd, FASTEST, cities, streets, motorwaySpeed, primarySpeed, residentialSpeed);
	}

	/**
	 * Fuehrt die Suche aus und schreibt das Ergebnis in ein {@link OptPathData} Objekt.
	 * 
	 * @param spa
	 * @param gd
	 * @param option
	 * @param cities
	 * @param streets
	 * @param motorwaySpeed
	 * @param primarySpeed
	 * @param residentialSpeed
	 * @return
	 * @throws NodeNotFoundException
	 * @throws MySQLException
	 * @throws PathNotFoundException
	 * @throws SQLException
	 */
	private OptPathData executeSearch(ShortestPathAlgorithm spa,
			GraphData gd, int option, String[] cities, String[] streets, int motorwaySpeed,
			int primarySpeed, int residentialSpeed) throws NodeNotFoundException, MySQLException, PathNotFoundException, SQLException {
		
		startStreet = streets[0];
		goalStreet = streets[streets.length - 1];
		LinkedList<Node> nodeList = new LinkedList<Node>();
		LinkedList<Node> result = new LinkedList<Node>();
		String[] destinations = new String[cities.length];
		DBAdapterRotativeRectangle dbap = null;
		int i = -1;
		int goalNodeID = -1;
		dbap = new DBAdapterRotativeRectangle(gd);
		for(i=0; i < cities.length -1; i++){
			try{
				int startNodeID = DBUtils.getNodeId(cities[i], streets[i]);
				if (startNodeID == -1) {
					throw new NodeNotFoundException("Falsche Angaben in Zeile: " + (i + 1));
				}
				destinations[i] = DBUtils.getLatLon(startNodeID);
				goalNodeID = DBUtils.getNodeId(cities[i+1], streets[i+1]);
				if (goalNodeID == -1) {
					throw new NodeNotFoundException("Falsche Angaben in Zeile: " + (i + 2));
				}
				float h = 0.15f; //30 km dicke
				dbap.fillGraphData(startNodeID, goalNodeID, h);
				try {
					switch (option) {
					case FASTEST: result = spa.findFastestPath(startNodeID, goalNodeID, motorwaySpeed, primarySpeed, residentialSpeed); break;
					case SHORTEST: result = spa.findShortestPath(startNodeID, goalNodeID, motorwaySpeed, primarySpeed, residentialSpeed); break;
					}
				} catch (PathNotFoundException e) {
					System.out.print("2. versuch");
					dbap.fillGraphData(startNodeID, goalNodeID, h + 0.2f);
					try {
						switch (option) {
						case FASTEST: result = spa.findFastestPath(startNodeID, goalNodeID, motorwaySpeed, primarySpeed, residentialSpeed); break;
						case SHORTEST: result = spa.findShortestPath(startNodeID, goalNodeID, motorwaySpeed, primarySpeed, residentialSpeed); break;
						}
					} catch (PathNotFoundException ex) {
						System.out.println(" fehlgeschlagen");
						throw new PathNotFoundException("Es tut uns Leid. Dieser Weg kann nicht erfasst werden.");
					}
				}
				if(i > 0)
					result.removeLast();
				nodeList.addAll(0, result);
			}catch(java.sql.SQLException e){
				throw new SQLException();
			}
		}
		try {
			destinations[i] = DBUtils.getLatLon(goalNodeID);
		} catch (java.sql.SQLException e1) {
			throw new SQLException();
		}
		nodes = nodeList.toArray(new Node[0]);
		edges = ShortestPathAlgorithm.getResultEdges(nodes);
		OptPathData opd = null;
		try {
			opd = buildOptPathData(spa, dbap, destinations);
		} catch (java.sql.SQLException e) {
			throw new SQLException();
		}
		return opd;
	}
	
	/**
	 * Erstellt ein {@link OptPathData} Object.
	 * @param spa
	 * @param dbap
	 * @param destinations
	 * @return
	 * @throws java.sql.SQLException
	 * @throws MySQLException
	 */
	private OptPathData buildOptPathData(ShortestPathAlgorithm spa, DBAdapterRotativeRectangle dbap, String[] destinations) throws java.sql.SQLException, MySQLException{
		float[] lats = new float[nodes.length];
		float[] lons = new float[nodes.length];
		for(int i=0; i<nodes.length;i++){
			lats[i] = nodes[i].getLat();
			lons[i] = nodes[i].getLon();
		}
		OptPathData opd = new OptPathData();
		opd.setNodeLats(lats);
		opd.setNodeLons(lons);
		opd.setDestinations(destinations);
		opd.setOptNodesNumber(nodes.length);
		opd.setAlgorithmRuntime(spa.getAlorithmTime());
		opd.setBuildEdgesRuntime(spa.getBuildEdgesTime());
		opd.setBuildNodesRuntime(spa.getBuildNodesTime());
		opd.setSelectedEdgesNumber(dbap.getEdgesCount());
		opd.setSelectedNodesNumber(dbap.getNodesCount());
		opd.setSelectEdgesRuntime(dbap.getReceiveEdgesTime()); //TODO fertig refactoren
		opd.setSelectNodesRuntime(dbap.getReceiveNodesTime());
		return opd;
	}

	/**
	 * Erstellt ein {@link AllPathData} Object mit der geglaetteten route.
	 */
	@Override
	public AllPathData buildAllPathData() throws MySQLException, SQLException {
		if(nodes == null || edges == null)
			throw new RuntimeException("Call one of the findFastestPath methods first!");
		
		AllPathData apd = new AllPathData();
		long time = System.currentTimeMillis();
		float[][] latLons;
		try {
			latLons = DBUtils.getAllNodeLatLons(nodes, edges);
		} catch (java.sql.SQLException e) {
			throw new SQLException();
		}
		apd.setOptToAllRuntime(System.currentTimeMillis() - time);
		apd.setAllNodesNumber(latLons[0].length);
		apd.setLats(latLons[0]);
		apd.setLons(latLons[1]);
		return apd;
	}

	/**
	 * Erstellt ein {@link PathDescription} Object mit der Textdarstellung der Route.
	 */
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
		pd.setWayDescriptions(rtt.buildRouteInfo(startStreet, goalStreet).toArray(new String[0])); //TODO direkt string array
		pd.setTimeTotal(rtt.getTotaltime());
		pd.setTimeOnMotorWay(rtt.getAutobahnTime());	//TODO englische namen
		pd.setTimeOnPrimary(rtt.getLandstrasseTime());
		pd.setTimeOnResidential(rtt.getInnerOrtstime()); // TODO schreibfehler
		pd.setDistanceOnMotorWay(rtt.getAutobahnString());
		pd.setDistanceOnPrimary(rtt.getLandstrasseString());
		pd.setDistanceOnResidential(rtt.getInnerOrtsString());
		pd.setDistanceTotal(rtt.getTotallengthString());
		pd.setRouteToTextRuntime(System.currentTimeMillis() - time);
		return pd;
	}
}
