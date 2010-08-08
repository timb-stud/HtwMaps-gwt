package de.htwmaps.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Name: <code>MySQL</code>
 * <p>
 * Klasse dient zum connecten zu einer Datenbank.
 * Beinhaltet ebenfalls Methoden um SQL-Querys auszufuehren
 * </p>
 * <ul>
 * 	 <li>@author YC</li>
 * 	 <li>@coauthor CR</li>
 * 	 <li>@version: 1.1 - 08.06.2010</li>
 * </ul>
 */
public class DBConnector {

	private String host;
	private String username;
	private String password;
	private String driver;
	private static boolean connected = false;
	private Connection con = null;
	private static DBConnector instance = null;

	private DBConnector() {
		driver = DBProperties.getString("DBConnector.driver"); //$NON-NLS-1$
		host = DBProperties.getString("DBConnector.host"); //$NON-NLS-1$
		password = DBProperties.getString("DBConnector.password"); //$NON-NLS-1$
		username = DBProperties.getString("DBConnector.username"); //$NON-NLS-1$
		connected = connect();
	}

	protected void finalize() {
		connected = disconnect();
		instance = null;
	}

	public static boolean isConnected() {
		return connected;
	}

	public static Connection getConnection(){
		if (instance == null) {
			instance = new DBConnector();
		}
		return instance.con;
	}

	private boolean connect() {
			try {
				Class.forName(driver).newInstance();
				con = DriverManager.getConnection(host, username, password);

			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return true;
	}
	
	private boolean disconnect() {
		if (connected) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
