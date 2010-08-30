package de.htwmaps.client.GUI;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WayDescriptionPanel extends VerticalPanel {
	
	Label wayDescriptionLabel = new Label();
	
	public WayDescriptionPanel() {
		setVisible(false);
		wayDescriptionLabel.setSize("280px", "10px");
		add(wayDescriptionLabel);
	}
	
	public Label getWayDescriptionLabel() {
		return wayDescriptionLabel;
	}
}
