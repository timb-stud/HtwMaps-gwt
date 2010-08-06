package de.htwmaps.client;


import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwmaps.shared.PathData;

public interface FindPathServiceAsync {

	void findFastestPathAStar(String startCity, String startStreet,
			String destCity, String destStreet, int motorwaySpeed,
			int primarySpeed, int residentialSpeed,
			AsyncCallback<PathData> callback);

	void findFastestPathAStarBi(String startCity, String startStreet,
			String destCity, String destStreet, int motorwaySpeed,
			int primarySpeed, int residentialSpeed,
			AsyncCallback<PathData> callback);

	void findShortestPathAStar(String startCity, String startStreet,
			String destCity, String destStreet, AsyncCallback<PathData> callback);

	void findShortestPathAStarBi(String startCity, String startStreet,
			String destCity, String destStreet, AsyncCallback<PathData> callback);

}
