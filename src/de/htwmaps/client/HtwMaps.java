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
		RootPanel.get("controlsPanel").add(controlsPanel);
		RootPanel.get("statusLabelContainer").add(statusLabel);
		RootPanel.get("aboutAnchorContainer").add(aboutAnchor);
		RootPanel.get("infoAnchorContainer").add(infoAnchor);
		
		controlsPanel.addStyleName("controlsPanel");
		controlsPanel.getCalcRouteButton().addClickHandler(new CalcRouteClickHandler(this));
	}
	
	void resetFields() { //TODO Methode aufsplitten
		controlsPanel.setCalcRouteButton(false);
		setTextAndStyle(StringConstant.BERECHNE, "statusLabelNormal");
		removePolylineAndMarker();
		controlsPanel.getSummaryPanel().setFieldsEmpty();
		controlsPanel.getWayDescriptionPanel().clear();
		controlsPanel.getLocation();
		controlsPanel.getLocation().getLocations().get(0).getCityHandler().clearContent();
		controlsPanel.getLocation().getLocations().get(0).getStreetHandler().clearContent();
		controlsPanel.getLocation().getLocations().get(1).getCityHandler().clearContent();
		controlsPanel.getLocation().getLocations().get(1).getStreetHandler().clearContent();
	}
	
	int findEmptyField (String[] tab) {
		for(int i=0; i< tab.length; i++){
			if(tab[i] == null || tab[i].isEmpty()){
				return i;
			}
		}
		return -1;
	}
	
	//TODO englische namen
	//TODO methode notwendig?
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
	
	native void addMarker(float lat, float lon, String text) /*-{
		$wnd.addMarker(lat, lon, text);
	}-*/;
	
	native void removePolylineAndMarker() /*-{
		$wnd.removePolylineAndMarker();
	}-*/;
	
	native void removePolyline() /*-{
		$wnd.removePolyline();
	}-*/;
	
	native void autoCenterAndZoom() /*-{
		$wnd.autoCenterAndZoom();
	}-*/;
	native void loadImageOn() /*-{
		$wnd.loadImageOn();
	}-*/;
	
	native void loadImageOff() /*-{
		$wnd.loadImageOff();
	}-*/;
}
