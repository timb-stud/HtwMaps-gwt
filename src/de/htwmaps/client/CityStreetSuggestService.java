package de.htwmaps.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.htwmaps.shared.exceptions.MySQLException;
import de.htwmaps.shared.exceptions.NoCitiesFoundException;
import de.htwmaps.shared.exceptions.NoStreetsFoundException;

@RemoteServiceRelativePath("cityStreetSuggest")
public interface CityStreetSuggestService extends RemoteService {
	
	String[] getCitySuggestions(String s) throws NoCitiesFoundException, MySQLException;
	String[] getStreetSuggestions(String city, String s) throws NoStreetsFoundException, MySQLException;
	
}
