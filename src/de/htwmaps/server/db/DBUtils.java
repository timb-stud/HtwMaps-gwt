package de.htwmaps.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.htwmaps.shared.exceptions.MySQLException;
import de.htwmaps.shared.exceptions.NodeNotFoundException;

public class DBUtils {
	
	private final static String GETNODEID_SELECT = "SELECT startNodeID FROM ways WHERE (cityName = ? OR is_in LIKE ?) AND nameValue = ?";
	private final static String GETCITIESSTARTWITH_SELECT = "SELECT DISTINCT cityName FROM ways WHERE cityName LIKE ?";
	private final static String GETSTREETSSTARTWITH_SELECT = "SELECT DISTINCT nameValue, cityName FROM ways WHERE (cityName = ? OR is_in LIKE ?) AND nameValue LIKE ?";
	
	private DBUtils(){ }
	
	public static int getNodeId(String city, String street) throws SQLException, NodeNotFoundException, MySQLException {
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
	
	public static String[] getCitiesStartsWith(String s) throws SQLException, MySQLException {
		Connection con = DBConnector.getConnection();
		PreparedStatement select = con.prepareStatement(GETCITIESSTARTWITH_SELECT);
		select.setString(1, s + "%");
		ResultSet rs  = select.executeQuery();
		if(!rs.next())
			return null;
		rs.last();
		int tableLength = rs.getRow();
		rs.beforeFirst();
		String[] result = new String[tableLength];
		for(int i=0; rs.next();i++){
			result[i] = rs.getString(1);
		}
		select.close();
		return result;
	}
	
	public static String[] getStreetsStartsWith(String city, String s) throws SQLException, MySQLException {
		Connection con = DBConnector.getConnection();
		PreparedStatement select = con.prepareStatement(GETSTREETSSTARTWITH_SELECT);
		select.setString(1, city);
		select.setString(2, "%" + city + "%");
		select.setString(3, s + "%");
		ResultSet rs  = select.executeQuery();
		if(!rs.next())
			return null;
		rs.last();
		int tableLength = rs.getRow();
		rs.beforeFirst();
		String[] result = new String[tableLength];
		for(int i=0; rs.next();i++){
			result[i] = rs.getString("nameValue") + "," +  rs.getString("cityName");
			select.setString(1, s + "%");
		}
		select.close();
		return result;
	}
}
