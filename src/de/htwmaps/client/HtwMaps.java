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
 * Entry point Klasse
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 */
public class HtwMaps implements EntryPoint {
	
	static Label statusLabel = new Label(StringConstant.BEREIT);
	AboutAnchor aboutAnchor = new AboutAnchor(StringConstant.UEBER);
	InfoAnchor infoAnchor = new InfoAnchor(StringConstant.BERECHNUNGSINFOS);
	ControlsPanel controlsPanel = new ControlsPanel();

	FindPathServiceAsync findPathSvc = GWT.create(FindPathService.class);

	/**
	 * Entry point methode.
	 */
	public void onModuleLoad() {
		RootPanel.get("controlsPanel").add(controlsPanel);
		RootPanel.get("statusLabelContainer").add(statusLabel);
		RootPanel.get("aboutAnchorContainer").add(aboutAnchor); 
		RootPanel.get("infoAnchorContainer").add(infoAnchor);

		controlsPanel.addStyleName("controlsPanel");
		controlsPanel.getCalcRouteButton().addClickHandler(new CalcRouteClickHandler(this));
	}
	
	/**
	 * Setzt alle Eingabefelder und die Map in ihren Anfangszustand
	 */
	@SuppressWarnings("static-access")
	void resetFields() {
		controlsPanel.setCalcRouteButton(false);
		setTextAndStyle(StringConstant.BERECHNE, "statusLabelNormal");
		removePolylineAndMarker();
		controlsPanel.getSummaryPanel().setFieldsEmpty();
		controlsPanel.getWayDescriptionPanel().clear();
		for (int i = 0; i < controlsPanel.getLocation().getLocations().size(); i++) {
			controlsPanel.getLocation().getLocations().get(i).getCityHandler().clearContent();
			controlsPanel.getLocation().getLocations().get(i).getStreetHandler().clearContent();
		}
	}
	
	/**
	 * Sucht nach leeren Eingabefeldern
	 * 
	 * @param tab Array mit allen momentanen Werten der Eingabefelder
	 * @return Zahl wenn kein leeree Eingabefeld gefunden, wenn nicht -1
	 */
	int findEmptyField (String[] tab) {
		for(int i=0; i< tab.length; i++){
			if(tab[i] == null || tab[i].isEmpty()){
				return i;
			}
		}
		return -1;
	}

	
    /**
     * Ersetzt das Statuslabel mit neuem Text und Style
     * 
     * @param text Text der angezeigt werden soll
     * @param style CSS- Style Name
     */
    public static void setTextAndStyle(String text, String style) {
    	statusLabel.setText(text);
    	statusLabel.setStyleName(style);
    }
	
	/**
	 * Ruft die JavaScipt Methode addPoint(float, float) auf, die geometrische Punkte hinzufügt
	 * 
	 * @param lat Lat- Wert des Punktes
	 * @param lon Lon- Wert des Punktes
	 */
	native void addPoint(float lat, float lon) /*-{
		$wnd.addPoint(lat, lon);
	}-*/;

	/**
	 * Ruft die JavaScipt Methode drawPolylin() auf, die die Route zeichnet
	 */
	native void drawPolyLine() /*-{
		$wnd.drawPolyLine();
	}-*/;
	
	/**
	 * Ruft die JavaScipt Methode addMarker(float, float, String) auf, die ein Marker auf der Map hinzufügt
	 * 
	 * @param lat Lat- Wert für den Marker
	 * @param lon Lon- Wert für den Marker
	 * @param text Beschreibungstext für den Marker
	 */
	native void addMarker(float lat, float lon, String text) /*-{
		$wnd.addMarker(lat, lon, text);
	}-*/;
	
	/**
	 * Ruft die JavaScipt Methode removePolylineAndMarker() auf, die die Polyline und alle Marker von der Map löscht
	 */
	native void removePolylineAndMarker() /*-{
		$wnd.removePolylineAndMarker();
	}-*/;
	
	/**
	 * Ruft die JavaScipt Methode removePolyline() auf, die die Polyline auf der Map löscht
	 */
	native void removePolyline() /*-{
		$wnd.removePolyline();
	}-*/;
	
	/**
	 * Ruft die JavaScipt Methode autoCenterAndZoom() auf, die automatisch so zoomt, dass alle Marker angezeigt werden
	 */
	native void autoCenterAndZoom() /*-{
		$wnd.autoCenterAndZoom();
	}-*/;
	
	/**
	 * Ruft die JavaScipt Methode loadImageOn() auf, die das Lagebild sichtbar macht
	 */
	native void loadImageOn() /*-{
		$wnd.loadImageOn();
	}-*/;
	
	/**
	 * Ruft die JavaScipt Methode loadImageOff() auf, die das Ladebild unsichtbar macht
	 */
	native void loadImageOff() /*-{
		$wnd.loadImageOff();
	}-*/;
}
