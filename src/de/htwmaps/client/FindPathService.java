package de.htwmaps.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.htwmaps.shared.AllPathData;
import de.htwmaps.shared.OptPathData;
import de.htwmaps.shared.PathDescription;
import de.htwmaps.shared.exceptions.MySQLException;
import de.htwmaps.shared.exceptions.NodeNotFoundException;
import de.htwmaps.shared.exceptions.PathNotFoundException;
import de.htwmaps.shared.exceptions.SQLException;

/**
 * RPC Methoden  zum finden und glaetten einer Route.
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 *
 */
@RemoteServiceRelativePath("findPath")
public interface FindPathService extends RemoteService {
	
	OptPathData findShortestPathAStar(	String[] cities,
										String[] streets, 
										int motorwaySpeed,
										int primarySpeed, 
										int residentialSpeed) throws 	NodeNotFoundException, PathNotFoundException, SQLException, MySQLException;

	OptPathData findFastestPathAStar(	String[] cities, 
										String[] streets, 
										int motorwaySpeed,
										int primarySpeed, 
										int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException;

	OptPathData findShortestPathAStarBi(String[] cities,
										String[] streets, 
										int motorwaySpeed,
										int primarySpeed, 
										int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException;

	OptPathData findFastestPathAStarBi(	String[] cities, 
										String[] streets, 
										int motorwaySpeed,
										int primarySpeed, 
										int residentialSpeed) throws NodeNotFoundException, PathNotFoundException, SQLException, MySQLException;
	
	AllPathData buildAllPathData() throws MySQLException, SQLException;
	
	PathDescription buildPathDescription() throws MySQLException, SQLException;
}
