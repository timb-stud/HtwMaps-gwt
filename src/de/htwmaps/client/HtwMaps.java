package de.htwmaps.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.htwmaps.client.GUI.AboutAnchor;
import de.htwmaps.client.GUI.ControlsPanel;
import de.htwmaps.shared.PathData;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HtwMaps implements EntryPoint {
	Label statusLabel = new Label("Status: Ready");
	AboutAnchor aboutAnchor = new AboutAnchor("About");
	ControlsPanel controlsPanel = new ControlsPanel();

	private FindPathServiceAsync findPathSvc = GWT.create(FindPathService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		controlsPanel.addStyleName("controlsPanel");
		RootPanel.get("controlsPanel").add(controlsPanel);
		RootPanel.get("statusLabelContainer").add(statusLabel);
		RootPanel.get("aboutAnchorContainer").add(aboutAnchor);
		
		controlsPanel.getCalcRouteButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				resetFields();
				
				if(findPathSvc == null){
					findPathSvc = GWT.create(FindPathService.class);
				}
				
				AsyncCallback<PathData> callback = new AsyncCallback<PathData>() {

					@Override
					public void onFailure(Throwable caught) {
						controlsPanel.setCalcRouteButton(true);
						statusLabel.setText("WARNUNG:" + caught.getMessage());
					}

					@Override
					public void onSuccess(PathData result) {
						String status = "Status: A route was found."
										+ "  Nodes: " + result.getNodesCount()
										+ "  Edges: " + result.getEdgesCount()
										+ "  Select Nodes: " + result.getReceiveNodesTime()
										+ "ms  Select Edges time: " + result.getReceiveEdgesTime()
										+ "ms  Build Nodes: " + result.getBuildNodesTime()
										+ "ms  Build Edges: " + result.getBuildEdgesTime()
										+ "ms  Algorithm: " + result.getAlorithmTime()
										+ "ms  OptToAll: " + result.getOptToAllTime()
										+ "ms";
						statusLabel.setText(status);
						controlsPanel.setCalcRouteButton(true);
						float[] nodeLats = result.getNodeLats();
						float[] nodeLons = result.getNodeLons();
						int i = 0;
						for(i=0;i<nodeLats.length;i++){
							addPoint(nodeLats[i], nodeLons[i]);
						}
						drawPolyLine();
						addMarker(nodeLats[0], nodeLons[0], nodeLats[nodeLats.length - 1], nodeLons[nodeLats.length - 1]);
					}
				};
				
				String startCity = controlsPanel.getLocationsPanel().getStartCityTextBox().getText();
				String startStreet = controlsPanel.getLocationsPanel().getStartStreetTextBox().getText();
				String destCity = controlsPanel.getLocationsPanel().getDestCityTextBox().getText();
				String destStreet = controlsPanel.getLocationsPanel().getDestStreetTextBox().getText();
				
				if (checkInputLocation(startCity, startStreet, destCity, destStreet)) {
					controlsPanel.setCalcRouteButton(true);
				} else {
					if (startStreet.indexOf(",") != -1) {
						startStreet = startStreet.substring(0, startStreet.indexOf(","));
					}
					if (destStreet.indexOf(",") != -1) {
						destStreet = destStreet.substring(0, destStreet.indexOf(","));
					}

					boolean shortestPath = controlsPanel.getOptionsPanel().getShortestRadioButton().getValue();
					boolean aStarBi = controlsPanel.getOptionsPanel().getaStarBiRadioButton().getValue();

					if (shortestPath) {
						if (aStarBi) {
							findPathSvc.findShortestPathAStarBi(startCity, startStreet, destCity, destStreet, callback);
						} else {
							findPathSvc.findShortestPathAStar(startCity, startStreet, destCity, destStreet,	callback);
						}
					} else {
						boolean checkSpeed = true;
						int motorwaySpeed = leseIntZahl(controlsPanel.getOptionsPanel().getMotorwaySpeedTextBox().getText().trim());
						if (motorwaySpeed <= 0) {
							statusLabel.setText("WARNUNG: Falsche Geschwindigkeitsangabe bei Autobahn!");
							checkSpeed = false;
						}
						int primarySpeed = leseIntZahl(controlsPanel.getOptionsPanel().getPrimarySpeedTextBox().getText().trim());
						if (primarySpeed <= 0 && checkSpeed) {
							statusLabel.setText("WARNUNG: Falsche Geschwindigkeitsange bei Landstrasse!");
							checkSpeed = false;
						}
						int residentialSpeed = leseIntZahl(controlsPanel.getOptionsPanel().getResidentialSpeedTextBox().getText().trim());
						if (residentialSpeed <= 0 && checkSpeed) {
							statusLabel.setText("WARNUNG: Falsche Geschwindigkeitsange bei Innerorts!");
							checkSpeed = false;
						}
						if (checkSpeed) {
							if (aStarBi) {
								findPathSvc.findFastestPathAStarBi(startCity, startStreet, destCity, destStreet, motorwaySpeed, primarySpeed, residentialSpeed, callback);
							} else {
								findPathSvc.findFastestPathAStar(startCity, startStreet, destCity, destStreet, motorwaySpeed, primarySpeed, residentialSpeed, callback);
							}
						} else {
							controlsPanel.setCalcRouteButton(true);
						}
					}
				}
			}
		});
	}
	
	private void resetFields() {
		controlsPanel.setCalcRouteButton(false);
		statusLabel.setText("Status: Calculate route!");
		removePolyline();
		removeMarker();
		controlsPanel.getLocationsPanel().getStartCity().clearContent();
		controlsPanel.getLocationsPanel().getStartStreet().clearContent();
		controlsPanel.getLocationsPanel().getDestCity().clearContent();
		controlsPanel.getLocationsPanel().getDestStreet().clearContent();
	}
	
	private boolean checkInputLocation (String startCity, String startStreet, String destCity, String destStreet) {
		boolean check = false;
		if (startCity.equals("") || startCity == null) {
			statusLabel.setText("WARNUNG: Bitte geben Sie einen Startort ein.");
			check = true;
		} else if (startStreet.equals("") || startStreet == null) {
			statusLabel.setText("WARNUNG: Bitte geben Sie eine Startstrasse ein.");
			check = true;
		} else if (destCity.equals("") || destCity == null) {
			statusLabel.setText("WARNUNG: Bitte geben Sie einen Zielort ein.");
			check = true;
		} else if (destStreet.equals("") || destStreet == null) {
			statusLabel.setText("WARNUNG: Bitte geben Sie eine Zielstrasse ein.");
			check = true;
		}
		return check;
	}
	
    private int leseIntZahl(String inData) {
        int eingabe = 0;
        try {
            eingabe = Integer.parseInt(inData);
        } catch (NumberFormatException fehler) {
            return -1;
        }
        return eingabe;
    }
	
	native void alert(String s)/*-{
		$wnd.alert(s);
	}-*/;
	
	native void addPoint(float lat, float lon) /*-{
		$wnd.addPoint(lat, lon);
	}-*/;

	native void drawPolyLine() /*-{
		$wnd.drawPolyLine();
	}-*/;
	
	native void removePolyline() /*-{
		$wnd.removePolyline();
	}-*/;
	
	native void addMarker(float latStart, float lonStart, float latEnde, float lonEnde) /*-{
		$wnd.addMarker(latStart, lonStart, latEnde, lonEnde);
	}-*/;
	
	native void removeMarker() /*-{
		$wnd.removeMarker();
	}-*/;
}
