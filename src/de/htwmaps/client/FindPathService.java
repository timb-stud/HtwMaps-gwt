package de.htwmaps.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.htwmaps.shared.PathData;
import de.htwmaps.shared.exceptions.NodeNotFoundException;
import de.htwmaps.shared.exceptions.PathNotFoundException;
import de.htwmaps.shared.exceptions.SQLException;
@RemoteServiceRelativePath("findPath")
public interface FindPathService extends RemoteService {
	PathData findShortestPathAStar(String startCity, String startStreet,
			String destCity, String destStreet) throws NodeNotFoundException, PathNotFoundException, SQLException;

	PathData findFastestPathAStar(String startCity, String startStreet,
			String destCity, String destStreet, int motorwaySpeed,
			int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException;

	PathData findShortestPathAStarBi(String startCity, String startStreet,
			String destCity, String destStreet) throws NodeNotFoundException, PathNotFoundException, SQLException;

	PathData findFastestPathAStarBi(String startCity, String startStreet,
			String destCity, String destStreet, int motorwaySpeed,
			int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException;
}