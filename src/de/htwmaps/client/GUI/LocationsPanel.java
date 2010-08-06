package de.htwmaps.client.GUI;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LocationsPanel extends VerticalPanel {
	
	HorizontalPanel startPanel1 = new HorizontalPanel();
	HorizontalPanel startPanel2 = new HorizontalPanel();
	HorizontalPanel endPanel1 = new HorizontalPanel();
	HorizontalPanel endPanel2 = new HorizontalPanel();
	Label startLabel = new Label("Start:");
	Label endeLabel = new Label("Ende:");
	TextBox startCityTextBox = new TextBox();
	TextBox startStreetTextBox = new TextBox();
	TextBox endCityTextBox = new TextBox();
	TextBox endStreetTextBox = new TextBox();
	
	public LocationsPanel() {
		startCityTextBox.setText("Ort");
		startStreetTextBox.setText("Strasse");
		endCityTextBox.setText("Ort");
		endStreetTextBox.setText("Strasse");
		startPanel1.add(startLabel);
		startPanel1.add(startCityTextBox);
		startPanel2.add(startStreetTextBox);
		endPanel1.add(endeLabel);
		endPanel1.add(endCityTextBox);
		endPanel2.add(endStreetTextBox);
		startPanel1.setSize("200px", "10px");
		startPanel1.setCellHorizontalAlignment(startCityTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		startPanel2.setSize("200px", "10px");
		startPanel2.setCellHorizontalAlignment(startStreetTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		endPanel1.setSize("200px", "10px");
		endPanel1.setCellHorizontalAlignment(endCityTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		endPanel2.setSize("200px", "10px");
		endPanel2.setCellHorizontalAlignment(endStreetTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		add(startPanel1);
		add(startPanel2);
		add(endPanel1);
		add(endPanel2);
	}
}
