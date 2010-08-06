package de.htwmaps.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ControlsPanel extends VerticalPanel{
	LocationsPanel locationsPanel = new LocationsPanel();
	OptionsPanel optionsPanel = new OptionsPanel();
	SummaryPanel summaryPanel = new SummaryPanel();
	WayDescriptionPanel wayDescriptionPanel = new WayDescriptionPanel();
	Button calcRouteButton = new Button("Route berechnen");
	final Anchor optionsAnchor = new Anchor("Optionen anzeigen");
	final Anchor summaryAnchor = new Anchor("Zusammenfassung anzeigen");
	final Anchor wayDescriptionAnchor = new Anchor("Wegbeschreibung anzeigen");
	
	public ControlsPanel() {
		init();
	}
	
	private void init(){
		setSize("300px", "10px");
		add(locationsPanel);
		add(calcRouteButton);
		setCellHorizontalAlignment(calcRouteButton, HasHorizontalAlignment.ALIGN_RIGHT);
		add(optionsAnchor);
		add(optionsPanel);
		add(summaryAnchor);
		add(summaryPanel);
		add(wayDescriptionAnchor);
		add(wayDescriptionPanel);
		
		optionsAnchor.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(optionsPanel.isVisible()){
					optionsAnchor.setText("Optionen anzeigen");
					optionsPanel.setVisible(false);
				}else{
					optionsAnchor.setText("Optionen ausblenden");
					optionsPanel.setVisible(true);
				}
				
			}
		});
		
		
		summaryAnchor.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (summaryPanel.isVisible()) {
					summaryAnchor.setText("Routenzusammenfassung anzeigen");
					summaryPanel.setVisible(false);
				} else {
					summaryAnchor.setText("Routenzusammenfassung ausblenden");
					summaryPanel.setVisible(true);
				}
			}
		});

		wayDescriptionAnchor.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (wayDescriptionPanel.isVisible()) {
					wayDescriptionAnchor.setText("Wegbeschreibung anzeigen");
					wayDescriptionPanel.setVisible(false);
				} else {
					wayDescriptionAnchor.setText("Wegbeschreibung ausblenden");
					wayDescriptionPanel.setVisible(true);
				}
			}
		});
	}
}
