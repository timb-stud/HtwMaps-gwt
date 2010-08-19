package de.htwmaps.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CityStreetSuggestServiceAsync {

	void getCitySuggestions(String s, AsyncCallback<String[]> callback);
	void getStreetSuggestions(String city, String s, AsyncCallback<String[]> callback);

}
