package de.htwmaps.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.htwmaps.client.GUI.AboutAnchor;
import de.htwmaps.client.GUI.ControlsPanel;
import de.htwmaps.client.GUI.InfoAnchor;
import de.htwmaps.client.GUI.StringConstant;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HtwMaps implements EntryPoint {
	
	static Label statusLabel = new Label(StringConstant.BEREIT);
	AboutAnchor aboutAnchor = new AboutAnchor(StringConstant.UEBER);
	InfoAnchor infoAnchor = new InfoAnchor(StringConstant.BERECHNUNGSINFOS);
	ControlsPanel controlsPanel = new ControlsPanel();

	FindPathServiceAsync findPathSvc = GWT.create(FindPathService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		controlsPanel.addStyleName("controlsPanel");
		RootPanel.get("controlsPanel").add(controlsPanel);
		RootPanel.get("statusLabelContainer").add(statusLabel);
		RootPanel.get("aboutAnchorContainer").add(aboutAnchor);
		RootPanel.get("infoAnchorContainer").add(infoAnchor);
		
		controlsPanel.getCalcRouteButton().addClickHandler(new CalcRouteClickHandler(this));
	}
	
	void resetFields() {
		controlsPanel.setCalcRouteButton(false);
		setTextAndStyle(StringConstant.BERECHNE, "statusLabelNormal");
		removePolyline();
		removeMarker();
		controlsPanel.getSummaryPanel().setFieldsEmpty();
		controlsPanel.getWayDescriptionPanel().clear();
		controlsPanel.getLocation();
		controlsPanel.getLocation().getLocations().get(0).getCityHandler().clearContent();
		controlsPanel.getLocation().getLocations().get(0).getStreetHandler().clearContent();;
		controlsPanel.getLocation().getLocations().get(1).getCityHandler().clearContent();;
		controlsPanel.getLocation().getLocations().get(1).getStreetHandler().clearContent();;
	}
	
	boolean checkInputLocation (String startCity, String startStreet, String destCity, String destStreet) {
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
	
    int leseIntZahl(String inData) {
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
