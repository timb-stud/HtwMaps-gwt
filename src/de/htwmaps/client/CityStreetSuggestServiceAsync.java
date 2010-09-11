package de.htwmaps.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * RPC zum erhalten von Straßen und Orten für die SuggestionBox
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 *
 */
public interface CityStreetSuggestServiceAsync {

	void getCitySuggestions(String s, AsyncCallback<String[]> callback);
	void getStreetSuggestions(String city, String s, AsyncCallback<String[]> callback);

}
