/**
 * Erstellt die schriftliche Ausgabe zu einer Route, 
 * sowie eine Statistik zur gefahrenen Strecke
 * 
 * @author Christian Rech, Yassir Klos
 */

package de.htwmaps.server.db;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import de.htwmaps.server.algorithm.AStarEdge;
import de.htwmaps.server.algorithm.Node;
import de.htwmaps.shared.exceptions.MySQLException;

public class RouteToText {

	private double totallength = 0.0;
	private double totaltime = 0;

	private double autobahn = 0.0;
	private double landstrasse = 0.0;
	private double innerOrts = 0.0;

	private double autobahnTime = 0;
	private double landstrasseTime = 0;
	private double innerOrtstime = 0;

	private DecimalFormat df_0_00 = new DecimalFormat("0.00");
	private DecimalFormat df_0 = new DecimalFormat("0");

	private ArrayList<StreetDetails> info = null;

	/**
	 * Konstruktor: ruft Methode auf welche Text erstellt
	 * 
	 * @param route
	 *            Array mit Knoten welche besucht werden
	 * @param edges
	 *            Array mit Kanten welche besucht werden
	 * @throws MySQLException 
	 * @throws SQLException 
	 */
	public RouteToText(Node[] route, AStarEdge[] edges) throws MySQLException, SQLException {
			createInfo(route, edges);
	}

	/**
	 * Methode gruppiert alle befahrenen Straßen
	 * 
	 * @param route
	 *            route Array mit Knoten welche besucht werden
	 * @param edges
	 *            Array mit Kanten welche besucht werden
	 * @throws SQLException
	 *             Moegliche Fehler beim beschaffen der Daten (Strassennamen
	 *             etc.)
	 * @throws MySQLException 
	 */
	private void createInfo(Node[] route, AStarEdge[] edge) throws SQLException, MySQLException {
		PreparedStatement pStmt;
		String streetQuery = "SELECT nameValue, cityname, is_in, ref FROM ways WHERE ID = ? ;";
		LinkedList<AStarEdge> edgeList = new LinkedList<AStarEdge>();
		info = new ArrayList<StreetDetails>();
		double dist = 0;
		ResultSet resultSet = null;
		String preview = null, current = null;
		String city = null, state = null, addition = null, selectedAdditon, direction = null;

		for (int i = route.length - 1; i > 0; i--) {
			totallength += edge[i-1].getLenght();

			Connection con = DBConnector.getConnection();
			pStmt = con.prepareStatement(streetQuery);
			pStmt.setInt(1, edge[i-1].getWayID());
			resultSet =  pStmt.executeQuery();
			resultSet.first();

			// Bestimmt ob Straßennamen oder Straßenbezeichnung (L123)
			if (!(i == 1) && (!resultSet.getString(4).isEmpty())) {
				current = resultSet.getString(4);
				selectedAdditon = resultSet.getString(1);
			} else {
				current = resultSet.getString(1);
				selectedAdditon = resultSet.getString(4);
			}

			// erstellt Statistik
			fillDriveOn(edge[i-1]);

			// nur beim ersten Durchlauf
			if (i == route.length - 1) {
				preview = current;
				addition = selectedAdditon;
				city = resultSet.getString(2);
				state = resultSet.getString(3);
			}

			// prueft ob aktuelle Strasse noch selbe Strasse ist wie Durchlauf
			// vorher
			if (preview.equals(current)) {
				edgeList.add(edge[i-1]);
				dist += edge[i-1].getLenght();
			} else {
				direction = getNextDirectionByConditions(route[i + 1],route[i], route[i - 1]);
				StreetDetails ti = new StreetDetails(preview, addition, city, state, dist, edgeList, direction);
				info.add(ti);
				ti = null;
				edgeList.clear();
				edgeList.add(edge[i-1]);
				dist = edge[i-1].getLenght();
			}

			// nur bei letzten Durchlauf
			if (i == 1) {
				edgeList.add(edge[i-1]);
				// direction = getNextDirectionByConditions(route[i+1], route[i], route[i-1]);
				StreetDetails ti = new StreetDetails(current, selectedAdditon, city, state, dist, edgeList, direction);
//				System.out.println("Ziel: " + ti);
				info.add(ti);
				ti = null;
				edgeList.clear();
			}

			preview = current;
			addition = selectedAdditon;
			city = resultSet.getString(2);
			state = resultSet.getString(3);
			con.close();
			pStmt.close();
		}
	}

	/**
	 * Erstellt die Statistik der befahrenen Straßen
	 * 
	 * @param e
	 *            Kante welche befahren wird.
	 */
	private void fillDriveOn(AStarEdge e) {
		// Autobahn 1
		// Landstraße 5 ,7
		// Innerorts 10,11,13

		double length = e.getLenght();
		double time = length / 1000 / e.getSpeed() * 60 * 60;
		totaltime += time;

		switch (e.getHighwayType()) {
		case 1:
			autobahn += length;
			autobahnTime += time;
			break;
		case 5:
		case 7:
			landstrasse += length;
			landstrasseTime += time;
			break;
		case 10:
		case 11:
		case 13:
			innerOrts += length;
			innerOrtstime += time;
			break;
		default:
			break;
		}
	}

	/**
	 * Erstellt aus gebildeter Gruppierung einen Text
	 * 
	 * @return Liste mit Anweisungen wir gefahren wird
	 */
	public LinkedList<String> buildRouteInfo() {
		LinkedList<String> routeText = new LinkedList<String>();
		StringBuffer sb = new StringBuffer();

		routeText.add("Sie starten in folgdender Straße: <b>"
				+ info.get(0).getName() +  "</b>");
		for (int i = 0; i < info.size() - 1; i++) {
			if (info.get(i).getEdgeList().getLast().getHighwayType() != 1
					&& info.get(i + 1).getEdgeList().getLast().getHighwayType() == 1) {
				sb.append("Fahren Sie nach <b>").append(info.get(i).getName()).append("</b>");
				sb.append(" auf die Autobahn ").append(info.get(i + 1).getName());
			} else {
				if (!info.get(i).getName().trim().equals(""))
					sb.append("Nach ").append(info.get(i).getName()).append(" ");
				else
					sb.append("Dann ");
				sb.append(info.get(i).getDirection());
				if (!info.get(i + 1).getName().trim().equals(""))
					sb.append(" in " + info.get(i + 1).getName());
				else
					sb.append(" in die nächste Straße");
				sb.append(" abbiegen.");
			}
			routeText.add(sb.toString());
			sb.setLength(0);
		}
		routeText.add("Sie haben Ihr Ziel erreicht");

		return routeText;
	}

	/**
	 * Stellt fest in welche Richtung abgebogen werden muss
	 * 
	 * @param fromNode
	 *            Knoten von wo man kommt
	 * @param switchNode
	 *            Knoten an welchem abgebogen wird
	 * @param toNode
	 *            Knoten welcher als naechstes besucht wird
	 * @return
	 */
	private String getNextDirectionByConditions(Node fromNode, Node switchNode, Node toNode) {
		Point2D.Double f = new Point2D.Double(fromNode.getLon(), fromNode.getLat());
		Point2D.Double s = new Point2D.Double(switchNode.getLon(), switchNode.getLat());
		Point2D.Double t = new Point2D.Double(toNode.getLon(), toNode.getLat());

		double steigungFS = Math.abs(getSlope(f, s));
		double steigungST = Math.abs(getSlope(s, t));

		if (Math.abs(steigungFS - steigungST) < (0.25)) {
			return "geradeaus";
		} else {
			Line2D.Double l = new Line2D.Double(f, s);
			switch (l.relativeCCW(t)) {
			case 1:
				return "rechts";
			case -1:
				return "links";
			default:
				return " ";
			}
		}
	}

	/**
	 * Gibt die Steigung zwischen 2 punkten zurueck
	 */
	public double getSlope(Point2D startPunkt, Point2D endPunkt) {
		return (endPunkt.getY() - startPunkt.getY()) / (endPunkt.getX() - startPunkt.getX());
	}

	/**
	 * Formatiert Millisekunden in Stunden, Minuten, Sekunden
	 * 
	 * @param lTime
	 * @return
	 */
	private String genarateTime(double lTime) {
		DecimalFormat df = new DecimalFormat("00");
		int hours = (int) (lTime / (60 * 60));
		int minutes = (int) (lTime / 60 - (hours * 60));
//		int seconds = (int) (lTime % 60);
//		return (df.format(hours) + ":" + df.format(minutes) + ":" + df.format(seconds));
		return (df.format(hours) + ":" + df.format(minutes));
	}

	/**
	 * toString Methode: nur fuer interne Tests
	 */
	@Override
	public String toString() {
		int i = 0;

		StringBuilder sb = new StringBuilder();
		Iterator<StreetDetails> tInfo = info.iterator();
		sb.append("Distance: " + "\t Strasse: " + "\t\t Additional: "
				+ "\t\t Ort/Stadt: " + "\t\t Bundesland: " + "\n");
		while (tInfo.hasNext()) {
			sb.append(tInfo.next().toString() + "\n");
			i++;
		}

		sb.append("\nAnzahl Strassen: " + i + " Gesamt Entfernung: "
				+ df_0_00.format((totallength / 1000)) + " km " + " Gesamt Dauer: "
				+ genarateTime(totaltime) + "\n\n");
		sb.append("Autobahn: ").append(df_0_00.format(autobahn / 1000)).append(
				" km Dauer: ").append(genarateTime(autobahnTime)).append("\n");
		sb.append("Landstraße: ").append(df_0_00.format(landstrasse / 1000)).append(
				" km Dauer: ").append(genarateTime(landstrasseTime)).append("\n");
		sb.append("Innerorts: ").append(df_0_00.format(innerOrts / 1000)).append(
				" km Dauer: ").append(genarateTime(innerOrtstime)).append("\n");

		return sb.toString();
	}

	// ------[ Getter & Setter ]----->

	public double getTotallength() {
		return totallength;
	}

	public String getTotallengthString() {
		return (totallength > 1000) ? df_0_00.format((totallength / 1000)) + " km" : df_0.format(totallength) + " m";
	}

	public double getAutobahn() {
		return autobahn;
	}

	public String getAutobahnString() {
		return (autobahn > 1000) ? df_0_00.format((autobahn / 1000)) + " km" : df_0.format(autobahn)+ " m";
	}

	public double getLandstrasse() {
		return landstrasse;
	}

	public String getLandstrasseString() {
		return (landstrasse > 1000) ? df_0_00.format((landstrasse / 1000)) + " km" : df_0.format(landstrasse) + " m";
	}

	public double getInnerOrts() {
		return innerOrts;
	}

	public String getInnerOrtsString() {
		return (innerOrts > 1000) ? df_0_00.format((innerOrts / 1000)) + " km" : df_0.format(innerOrts) + " m";
	}

	public String getTotaltime() {
		return genarateTime(totaltime);
	}

	public String getAutobahnTime() {
		return genarateTime(autobahnTime);
	}

	public String getLandstrasseTime() {
		return genarateTime(landstrasseTime);
	}

	public String getInnerOrtstime() {
		return genarateTime(innerOrtstime);
	}

	public ArrayList<StreetDetails> getInfo() {
		return info;
	}

}