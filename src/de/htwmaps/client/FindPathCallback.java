package de.htwmaps.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwmaps.client.GUI.InfoAnchor;
import de.htwmaps.client.GUI.StringConstant;
import de.htwmaps.shared.AllPathData;
import de.htwmaps.shared.OptPathData;
import de.htwmaps.shared.PathDescription;

/**
 * Beinhaltet methoden die aufgerufen werden wenn Weg gefunden wurde oder Fehler auftraten.
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 *
 */
public class FindPathCallback implements AsyncCallback<OptPathData> {

	HtwMaps mainModule;
	
	public FindPathCallback(HtwMaps module) {
		mainModule = module;
	}
	
	/**
	 * Wird aufgerufen bei einem Fehler. <br>
	 * Fehlerausgabe und zuruecksetzen des Lade Icons und des Berechnen buttons.
	 */
	@Override
	public void onFailure(Throwable caught) {
		mainModule.loadImageOff();
		mainModule.controlsPanel.setCalcRouteButton(true);
		HtwMaps.setTextAndStyle(caught.getMessage(), "statusLabelError");
	}

	/**
	 * Wird aufgerufen wenn eine Route gefunden wurde.<br>
	 *  Route wird gezeichnet.<br>
	 *  Start/Ende Marker werden gesetzt. <br>
	 *  Laufzeit Informationen werden gesetzt.
	 */
	@Override
	public void onSuccess(OptPathData result) {
		mainModule.loadImageOff();
		HtwMaps.setTextAndStyle(StringConstant.ROUTE_GEFUNDEN, "statusLabelNormal");
		mainModule.controlsPanel.setCalcRouteButton(true);
		float[] nodeLats = result.getNodeLats();
		float[] nodeLons = result.getNodeLons();
		int i = 0;
		for(i=0;i<nodeLats.length;i++){
			mainModule.addPoint(nodeLats[i], nodeLons[i]);
		}
		mainModule.drawPolyLine();
		
		String[] destinations = result.getDestinations();
		float lat;
		float lon;
		String beschreibung;
		for (i = 0; i < destinations.length; i++) {
			lat = Float.parseFloat(destinations[i].substring(0, destinations[i].indexOf("|")));
			lon = Float.parseFloat(destinations[i].substring(destinations[i].indexOf("|") + 1));
			if (i == 0) {
				beschreibung = "Start<br>";
			} else if (i == destinations.length - 1) {
				beschreibung = "Ziel<br>";
			} else {
				beschreibung = "Zwischenziel " + (i) + "<br>";
			}
			beschreibung = beschreibung + mainModule.controlsPanel.getLocation().getLocations().get(i).getStreetSuggestBox().getText();
			mainModule.addMarker(lat, lon, beschreibung);
		}
		mainModule.autoCenterAndZoom();
		
		InfoAnchor ia = mainModule.infoAnchor;
		ia.setSelectedNodesNumber(result.getSelectedNodesNumber());
		ia.setSelectedEdgesNumber(result.getSelectedEdgesNumber());
		ia.setSelectNodesRuntime(result.getSelectNodesRuntime());
		ia.setSelectEdgesRuntime(result.getSelectEdgesRuntime());
		ia.setBuildNodesRuntime(result.getBuildNodesRuntime());
		ia.setBuildEdgesRuntime(result.getBuildEdgesRuntime());
		ia.setAlgorithmRuntime(result.getAlgorithmRuntime());
		ia.setOptNodesNumber(result.getOptNodesNumber());
		ia.setCompleteRuntime((System.currentTimeMillis() - CalcRouteClickHandler.getStartTime()) / 1000);
		
		AsyncCallback<AllPathData> allPathDataCallback = new AllPathDataCallback(mainModule);
		mainModule.findPathSvc.buildAllPathData(allPathDataCallback);
		
		AsyncCallback<PathDescription> pathDescriptionCallback = new PathDescriptionCallback(mainModule);
		mainModule.findPathSvc.buildPathDescription(pathDescriptionCallback);
	}

}
