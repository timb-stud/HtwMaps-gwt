package de.htwmaps.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
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
	HTML informationHTML = new HTML();
	AboutAnchor aboutAnchor = new AboutAnchor("About");
	ControlsPanel controlsPanel = new ControlsPanel();
	long startZeit = -1;
	long endeZeit = -1;

	private FindPathServiceAsync findPathSvc = GWT.create(FindPathService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		controlsPanel.addStyleName("controlsPanel");
		RootPanel.get("controlsPanel").add(controlsPanel);
		RootPanel.get("statusLabelContainer").add(statusLabel);
		RootPanel.get("aboutAnchorContainer").add(aboutAnchor);
		RootPanel.get("informationHTMLContainer").add(informationHTML);
		
		controlsPanel.getCalcRouteButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				resetFields();
				startZeit = System.currentTimeMillis();
				
				if(findPathSvc == null){
					findPathSvc = GWT.create(FindPathService.class);
				}
				
				AsyncCallback<PathData> callback = new AsyncCallback<PathData>() {

					@Override
					public void onFailure(Throwable caught) {
						controlsPanel.setCalcRouteButton(true);
						statusLabel.setText("WARNUNG:" + caught.getMessage());
						statusLabel.setStyleName("statusLabelError");
					}

					@Override
					public void onSuccess(PathData result) {
						endeZeit = System.currentTimeMillis();
						
						String statusText = "Status: A route was found.";
						String informationText = "<table border=\"1\"><tr><td>Nodes:</td><td>" + result.getNodesCount() + "</td></tr>"
											+ "<tr><td>Edges:</td><td>" + result.getEdgesCount() + "</td></tr>"
											+ "<tr><td>Select Nodes:</td><td>" + result.getReceiveNodesTime() + "ms</td></tr>"
											+ "<tr><td>Select Edges:</td><td>" + result.getReceiveEdgesTime() + "ms</td></tr>"
											+ "<tr><td>Build Nodes:</td><td>" + result.getBuildNodesTime() + "ms</td></tr>"
											+ "<tr><td>Build Edges:</td><td>" + result.getBuildEdgesTime() + "ms</td></tr>"
											+ "<tr><td>Algorithm:</td><td>" + result.getAlorithmTime() + "ms</td></tr>"
											+ "<tr><td>OptToAll:</td><td>" + result.getOptToAllTime() + "ms</td></tr>"
											+ "<tr><td>optNodesResult:</td><td>" + result.getOptNodesResultCount() + "</td></tr>"
											+ "<tr><td>allNodesResult:</td><td>" + result.getAllNodesResultCount() + "</td></tr>"
											+ "<tr><td>Zeit insgesamt:</td><td>" + ((endeZeit - startZeit)/1000)  + "sec</td></tr></table>";
						statusLabel.setText(statusText);
						statusLabel.setStyleName("statusLabelNormal");
						informationHTML.setHTML(informationText);
						controlsPanel.getWayDescriptionPanel().getWayDescriptionLabel().setText(result.getDescription());
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
							statusLabel.setStyleName("statusLabelError");
							checkSpeed = false;						}
						int primarySpeed = leseIntZahl(controlsPanel.getOptionsPanel().getPrimarySpeedTextBox().getText().trim());
						if (primarySpeed <= 0 && checkSpeed) {
							statusLabel.setText("WARNUNG: Falsche Geschwindigkeitsange bei Landstrasse!");
							statusLabel.setStyleName("statusLabelError");
							checkSpeed = false;
						}
						int residentialSpeed = leseIntZahl(controlsPanel.getOptionsPanel().getResidentialSpeedTextBox().getText().trim());
						if (residentialSpeed <= 0 && checkSpeed) {
							statusLabel.setText("WARNUNG: Falsche Geschwindigkeitsange bei Innerorts!");
							statusLabel.setStyleName("statusLabelError");
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
		statusLabel.setStyleName("statusLabelNormal");
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
			statusLabel.setStyleName("statusLabelError");
			check = true;
		} else if (startStreet.equals("") || startStreet == null) {
			statusLabel.setText("WARNUNG: Bitte geben Sie eine Startstrasse ein.");
			statusLabel.setStyleName("statusLabelError");
			check = true;
		} else if (destCity.equals("") || destCity == null) {
			statusLabel.setText("WARNUNG: Bitte geben Sie einen Zielort ein.");
			statusLabel.setStyleName("statusLabelError");
			check = true;
		} else if (destStreet.equals("") || destStreet == null) {
			statusLabel.setText("WARNUNG: Bitte geben Sie eine Zielstrasse ein.");
			statusLabel.setStyleName("statusLabelError");
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
