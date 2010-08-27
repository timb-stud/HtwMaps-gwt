package de.htwmaps.server.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.htwmaps.shared.exceptions.MySQLException;


public class DBAdapterRouteToText {

	public static ResultSet getStreetnameRS(int wayID) {
		PreparedStatement pStmt;
		ResultSet resultSet = null;
		
		String streetQuery = "SELECT nameValue, cityname, is_in, ref FROM ways WHERE ID = ? ;";
		
		try {
			pStmt = DBConnector.getConnection().prepareStatement(streetQuery);
			pStmt.setInt(1, wayID);
			resultSet =  pStmt.executeQuery();
			pStmt = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MySQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}


	
}
