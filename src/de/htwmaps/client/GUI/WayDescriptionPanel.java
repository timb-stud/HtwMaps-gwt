package de.htwmaps.client.GUI;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WayDescriptionPanel extends VerticalPanel {
	
	Label wegbeschreibungLabel = new Label("Recht -> links geradeaus?");
	
	public WayDescriptionPanel() {
		setVisible(false);
		wegbeschreibungLabel.setSize("280px", "10px");
		add(wegbeschreibungLabel);
	}
}
