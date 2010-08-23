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
				controlsPanel.setCalcRouteButton(false);
				statusLabel.setText("Status: Calculate route!");
				removePolyline();
				removeMarker();
				controlsPanel.getLocationsPanel().getStartCity().clearContent();
				controlsPanel.getLocationsPanel().getStartStreet().clearContent();
				controlsPanel.getLocationsPanel().getDestCity().clearContent();
				controlsPanel.getLocationsPanel().getDestStreet().clearContent();
				
				if(findPathSvc == null){
					findPathSvc = GWT.create(FindPathService.class);
				}
				
				AsyncCallback<PathData> callback = new AsyncCallback<PathData>() {

					@Override
					public void onFailure(Throwable caught) {
						controlsPanel.setCalcRouteButton(true);
						statusLabel.setText("Status: an error occoured:" + caught.getMessage());
					}

					@Override
					public void onSuccess(PathData result) {
						controlsPanel.setCalcRouteButton(true);
						statusLabel.setText("Status: A route was found.");
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
						findPathSvc.findShortestPathAStar(startCity, startStreet, destCity, destStreet, callback);
					}
				} else {
					int motorwaySpeed = Integer.parseInt(controlsPanel.getOptionsPanel().getMotorwaySpeedTextBox().getText().trim());
					int primarySpeed = Integer.parseInt(controlsPanel.getOptionsPanel().getPrimarySpeedTextBox().getText().trim());
					int residentialSpeed = Integer.parseInt(controlsPanel.getOptionsPanel().getResidentialSpeedTextBox().getText().trim());
					if (aStarBi) {
						findPathSvc.findFastestPathAStarBi(startCity, startStreet, destCity, destStreet, motorwaySpeed, primarySpeed, residentialSpeed, callback);
					} else {
						findPathSvc.findFastestPathAStar(startCity, startStreet, destCity, destStreet, motorwaySpeed, primarySpeed, residentialSpeed, callback);
					}
				}
				
			}
		});
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
