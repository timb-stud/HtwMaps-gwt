package de.htwmaps.client;


import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwmaps.shared.AllPathData;
import de.htwmaps.shared.OptPathData;
import de.htwmaps.shared.PathDescription;

public interface FindPathServiceAsync {

	void findFastestPathAStar(	String[] cities, 
								String[] streets, 
								int motorwaySpeed,
								int primarySpeed, 
								int residentialSpeed,
								AsyncCallback<OptPathData> callback);

	void findFastestPathAStarBi(String[] cities, 
								String[] streets, 
								int motorwaySpeed,
								int primarySpeed, 
								int residentialSpeed,
								AsyncCallback<OptPathData> callback);

	void findShortestPathAStar(	String[] cities, 
								String[] streets, 
								int motorwaySpeed,
								int primarySpeed, 
								int residentialSpeed, 
								AsyncCallback<OptPathData> callback);

	void findShortestPathAStarBi(	String[] cities, 
									String[] streets, 
									int motorwaySpeed,
									int primarySpeed, 
									int residentialSpeed, 
									AsyncCallback<OptPathData> callback);

	void buildAllPathData(AsyncCallback<AllPathData> callback);

	void buildPathDescription(AsyncCallback<PathDescription> callback);

}
