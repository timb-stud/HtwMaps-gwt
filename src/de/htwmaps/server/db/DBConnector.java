package de.htwmaps.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.htwmaps.shared.exceptions.MySQLException;

/**
 * Name: <code>MySQL</code>
 * <p>
 * Klasse gibt eine SQL Connection zurueck.
 * </p>
 * <ul>
 * 	 <li>@author Yassir Klos, Christian Rech, Tim Bartsch</li>
 * </ul>
 */
public class DBConnector {

	private static final String host = DBProperties.getString("DBConnector.host");
	private static final String username = DBProperties.getString("DBConnector.username");
	private static final String password = DBProperties.getString("DBConnector.password");
	private static final String driver = DBProperties.getString("DBConnector.driver");

	public static Connection getConnection() throws SQLException, MySQLException{
		try {
			Class.forName(driver).newInstance();
			return DriverManager.getConnection(host, username, password);
		} catch (InstantiationException e) {
			throw new MySQLException(e);
		} catch (IllegalAccessException e) {
			throw new MySQLException(e);
		} catch (ClassNotFoundException e) {
			throw new MySQLException(e);
		}
	}
	
}
