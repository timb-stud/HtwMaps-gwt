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
import de.htwmaps.client.GUI.InfoAnchor;
import de.htwmaps.client.GUI.LocationFlexTable;
import de.htwmaps.client.GUI.StringConstant;
import de.htwmaps.shared.OptPathData;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HtwMaps implements EntryPoint {
	
	static Label statusLabel = new Label(StringConstant.BEREIT);
	AboutAnchor aboutAnchor = new AboutAnchor(StringConstant.UEBER);
	InfoAnchor infoAnchor = new InfoAnchor(StringConstant.BERECHNUNGSINFOS);
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
		RootPanel.get("infoAnchorContainer").add(infoAnchor);
		
		controlsPanel.getCalcRouteButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				startZeit = System.currentTimeMillis();
				resetFields();
				loadImageOn();
				
				if(findPathSvc == null){
					findPathSvc = GWT.create(FindPathService.class);
				}
				
				AsyncCallback<OptPathData> callback = new AsyncCallback<OptPathData>() {

					@Override
					public void onFailure(Throwable caught) {
						loadImageOff();
						controlsPanel.setCalcRouteButton(true);
						setTextAndStyle(caught.getMessage(), "statusLabelError");
					}

					@Override
					public void onSuccess(OptPathData result) {
						endeZeit = System.currentTimeMillis();
						loadImageOff();
						setTextAndStyle(StringConstant.ROUTE_GEFUNDEN, "statusLabelNormal");
						infoAnchor.setInfoResultText(result, ((endeZeit - startZeit)/1000));
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
				
				String startCity = LocationFlexTable.getLocations().get(0).getCitySuggestBox().getText();
				String startStreet = LocationFlexTable.getLocations().get(0).getStreetSuggestBox().getText();;
				String destCity = LocationFlexTable.getLocations().get(1).getCitySuggestBox().getText();;
				String destStreet = LocationFlexTable.getLocations().get(1).getStreetSuggestBox().getText();;
				
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
							loadImageOff();
							setTextAndStyle(StringConstant.F_AUTOBAHN, "statusLabelError");
							checkSpeed = false;						}
						int primarySpeed = leseIntZahl(controlsPanel.getOptionsPanel().getPrimarySpeedTextBox().getText().trim());
						if (primarySpeed <= 0 && checkSpeed) {
							loadImageOff();
							setTextAndStyle(StringConstant.F_LANDSTRASSE, "statusLabelError");
							checkSpeed = false;
						}
						int residentialSpeed = leseIntZahl(controlsPanel.getOptionsPanel().getResidentialSpeedTextBox().getText().trim());
						if (residentialSpeed <= 0 && checkSpeed) {
							loadImageOff();
							setTextAndStyle(StringConstant.F_INNERORTS, "statusLabelError");
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
		setTextAndStyle(StringConstant.BERECHNE, "statusLabelNormal");
		removePolyline();
		removeMarker();
		controlsPanel.getLocation();
		LocationFlexTable.getLocations().get(0).getCityHandler().clearContent();
		LocationFlexTable.getLocations().get(0).getStreetHandler().clearContent();;
		LocationFlexTable.getLocations().get(0).getCityHandler().clearContent();;
		LocationFlexTable.getLocations().get(0).getStreetHandler().clearContent();;
	}
	
	private boolean checkInputLocation (String startCity, String startStreet, String destCity, String destStreet) {
		boolean check = false;
		if (startCity.equals("") || startCity == null) {
			loadImageOff();
			setTextAndStyle(StringConstant.STARTORT, "statusLabelError");
			check = true;
		} else if (startStreet.equals("") || startStreet == null) {
			loadImageOff();
			setTextAndStyle(StringConstant.STARTSTRASSE, "statusLabelError");
			check = true;
		} else if (destCity.equals("") || destCity == null) {
			loadImageOff();
			setTextAndStyle(StringConstant.ZIELORT, "statusLabelError");
			check = true;
		} else if (destStreet.equals("") || destStreet == null) {	
			loadImageOff();
			setTextAndStyle(StringConstant.ZIELSTRASSE, "statusLabelError");
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
    
    public static void setTextAndStyle(String text, String style) {
    	statusLabel.setText(text);
    	statusLabel.setStyleName(style);
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
	
	native void loadImageOn() /*-{
		$wnd.loadImageOn();
	}-*/;
	
	native void loadImageOff() /*-{
		$wnd.loadImageOff();
	}-*/;
}
