package de.htwmaps.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Erstellt die Eingabefelder fuer Start/ und Zieldaten, Optionspanel und die Ausgabepanel f�r die Zusammenfassung und Routenbeschreibung 
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 */
public class ControlsPanel extends VerticalPanel{
	
	LocationFlexTable location = new LocationFlexTable();
	OptionsPanel optionsPanel = new OptionsPanel();
	SummaryPanel summaryPanel = new SummaryPanel();
	WayDescriptionPanel wayDescriptionPanel = new WayDescriptionPanel();
	Button calcRouteButton = new Button(StringConstant.ROUTE_BERECHNEN);
	final Anchor optionsAnchor = new Anchor(StringConstant.OPTIONEN_ANZEIGEN);
	final Anchor summaryAnchor = new Anchor(StringConstant.ZUSAMMENFASSUNG_ANZEIGEN);
	final Anchor wayDescriptionAnchor = new Anchor(StringConstant.WEGBESCHREIBUNG_ANZEIGEN);
	
	/**
	 * Standardkonstruktor der Klasse, der die Initialisierung an die init() Methode weitergibt
	 */
	public ControlsPanel() {
		init();
	}

	/**
	 * Initialisiert die verschiedenen Panels für das Objekt
	 */
	private void init(){
		setSize("300px", "15px");
		add(location);
		add(calcRouteButton);
		setCellHorizontalAlignment(calcRouteButton, HasHorizontalAlignment.ALIGN_RIGHT);
		add(optionsAnchor);
		add(optionsPanel);
		add(summaryAnchor);
		add(summaryPanel);
		add(wayDescriptionAnchor);
		add(wayDescriptionPanel);
		
		optionsPanel.addStyleName("controlsPanel");
		optionsAnchor.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(optionsPanel.isVisible()){
					optionsAnchor.setText(StringConstant.OPTIONEN_ANZEIGEN);
					optionsPanel.setVisible(false);
				}else{
					optionsAnchor.setText(StringConstant.OPTIONEN_AUSBLENDEN);
					optionsPanel.setVisible(true);
				}
			}
		});
		
		summaryPanel.addStyleName("controlsPanel");
		summaryAnchor.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (summaryPanel.isVisible()) {
					summaryAnchor.setText(StringConstant.ZUSAMMENFASSUNG_ANZEIGEN);
					summaryPanel.setVisible(false);
				} else {
					summaryAnchor.setText(StringConstant.ZUSAMMENFASSUNG_AUSBLENDEN);
					summaryPanel.setVisible(true);
				}
			}
		});

		wayDescriptionPanel.addStyleName("controlsPanel");
		wayDescriptionAnchor.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (wayDescriptionPanel.isVisible()) {
					wayDescriptionAnchor.setText(StringConstant.WEGBESCHREIBUNG_ANZEIGEN);
					wayDescriptionPanel.setVisible(false);
				} else {
					wayDescriptionAnchor.setText(StringConstant.WEGBESCHREIBUNG_AUSBLENDEN);
					wayDescriptionPanel.setVisible(true);
				}
			}
		});
	}
	
	/**
	 * 
	 * @return Gibt den Berechnen Button zurück 
	 */
	public Button getCalcRouteButton() {
		return calcRouteButton;
	}
	
	/**
	 * Methode um den CalcRouteButton zu aktivieren oder deaktivieren
	 * 
	 * @param active true -> aktiviert den Button
	 * 		  		 false -> deaktiviert den Button
	 */
	public void setCalcRouteButton(boolean active) {
		this.calcRouteButton.setEnabled(active);
	}
	
	/**
	 * @return Liefert die zu Routende Daten zurück
	 */
	public LocationFlexTable getLocation() {
		return location;
	}

	/**
	 * @return Gibt das Optionspanel zurück
	 */
	public OptionsPanel getOptionsPanel() {
		return optionsPanel;
	}

	/**
	 * @return Gibt das Wegbeschreibungspanel zurück
	 */
	public WayDescriptionPanel getWayDescriptionPanel() {
		return wayDescriptionPanel;
	}
	
	/**
	 * @return Gibt das Zusammenfassungspanel zurück
	 */
	public SummaryPanel getSummaryPanel() {
		return summaryPanel;
	}
}
