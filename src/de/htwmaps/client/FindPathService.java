package de.htwmaps.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.htwmaps.shared.AllPathData;
import de.htwmaps.shared.PathData;
import de.htwmaps.shared.PathDescription;
import de.htwmaps.shared.exceptions.MySQLException;
import de.htwmaps.shared.exceptions.NodeNotFoundException;
import de.htwmaps.shared.exceptions.PathNotFoundException;
import de.htwmaps.shared.exceptions.SQLException;
@RemoteServiceRelativePath("findPath")
public interface FindPathService extends RemoteService {
	PathData findShortestPathAStar(String startCity, String startStreet,
			String destCity, String destStreet) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException;

	PathData findFastestPathAStar(String startCity, String startStreet,
			String destCity, String destStreet, int motorwaySpeed,
			int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException;

	PathData findShortestPathAStarBi(String startCity, String startStreet,
			String destCity, String destStreet) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException;

	PathData findFastestPathAStarBi(String startCity, String startStreet,
			String destCity, String destStreet, int motorwaySpeed,
			int primarySpeed, int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException;
	
	AllPathData buildAllPathData() throws MySQLException, SQLException;
	
	PathDescription buildPathDescription() throws MySQLException, SQLException;
}
