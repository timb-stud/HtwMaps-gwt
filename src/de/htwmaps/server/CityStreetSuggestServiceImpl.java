package de.htwmaps.server;

import java.sql.SQLException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.htwmaps.client.CityStreetSuggestService;
import de.htwmaps.server.db.DBUtils;
import de.htwmaps.shared.exceptions.MySQLException;
import de.htwmaps.shared.exceptions.NoCitiesFoundException;
import de.htwmaps.shared.exceptions.NoStreetsFoundException;

/**
 * Implementation des Suggestion Service.
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 *
 */
public class CityStreetSuggestServiceImpl extends RemoteServiceServlet implements
		CityStreetSuggestService {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @return String[] mit Orten die mit dem String s anfangen.
	 */
	@Override
	public String[] getCitySuggestions(String s) throws NoCitiesFoundException, MySQLException {
		try {
			return DBUtils.getCitiesStartsWith(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @return String[] mit Strassen die mit dem String s anfangen und in dem Ort city liegen.
	 */
	@Override
	public String[] getStreetSuggestions(String city, String s) throws NoStreetsFoundException, MySQLException {
		try{
			return DBUtils.getStreetsStartsWith(city, s);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

}
