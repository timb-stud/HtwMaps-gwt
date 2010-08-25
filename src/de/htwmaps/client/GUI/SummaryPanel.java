package de.htwmaps.client.GUI;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SummaryPanel extends VerticalPanel {
	
	HorizontalPanel fahrzeitPanel = new HorizontalPanel();
	HorizontalPanel weglaengePanel = new HorizontalPanel();
	HorizontalPanel autobahnPanel = new HorizontalPanel();
	HorizontalPanel landstrassePanel = new HorizontalPanel();
	HorizontalPanel innerortsPanel = new HorizontalPanel();
	Label fahrzeitLabel = new Label("Fahrzeit:");
	Label fahrzeitResultLabel = new Label("1h23min");
	Label weglaengeLabel = new Label("Weglaenge:");
	Label weglaengeResultLabel = new Label("118,2 km");
	Label autobahnLabel = new Label("-Autobahn:");
	Label autobahnResultLabel = new Label("101,7 km");
	Label landstrasseLabel = new Label("-Landstrasse:");
	Label landstrasseResultLabel = new Label("10,8 km");
	Label innerortsLabel = new Label("-Innerorts:");
	Label innerortsResultLabel = new Label("5,7 km");
	
	public SummaryPanel() {
		fahrzeitLabel.addStyleName("labelStyle");
		setVisible(false);
		fahrzeitPanel.add(fahrzeitLabel);
		fahrzeitPanel.add(fahrzeitResultLabel);
		fahrzeitPanel.setSize("280px", "10px");
		fahrzeitPanel.setCellHorizontalAlignment(fahrzeitResultLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		weglaengePanel.add(weglaengeLabel);
		weglaengePanel.add(weglaengeResultLabel);
		weglaengePanel.setSize("280px", "10px");
		weglaengePanel.setCellHorizontalAlignment(weglaengeResultLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		autobahnPanel.add(autobahnLabel);
		autobahnPanel.add(autobahnResultLabel);
		autobahnPanel.setSize("280px", "10px");
		autobahnPanel.setCellHorizontalAlignment(autobahnResultLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		landstrassePanel.add(landstrasseLabel);
		landstrassePanel.add(landstrasseResultLabel);
		landstrassePanel.setSize("280px", "10px");
		landstrassePanel.setCellHorizontalAlignment(landstrasseResultLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		innerortsPanel.add(innerortsLabel);
		innerortsPanel.add(innerortsResultLabel);
		innerortsPanel.setSize("280px", "10px");
		innerortsPanel.setCellHorizontalAlignment(innerortsResultLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		add(fahrzeitPanel);
		add(weglaengePanel);
		add(autobahnPanel);
		add(landstrassePanel);
		add(innerortsPanel);
	}
}
