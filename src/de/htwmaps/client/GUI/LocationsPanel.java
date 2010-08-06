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
	Label destLabel = new Label("Ende:");
	TextBox startCityTextBox = new TextBox();
	TextBox startStreetTextBox = new TextBox();
	TextBox destCityTextBox = new TextBox();
	TextBox destStreetTextBox = new TextBox();
	
	public LocationsPanel() {
		startCityTextBox.setText("Saarlouis");
		startStreetTextBox.setText("Grubenweg");
		destCityTextBox.setText("Dillingen");
		destStreetTextBox.setText("Am Hainbach");
		startPanel1.add(startLabel);
		startPanel1.add(startCityTextBox);
		startPanel2.add(startStreetTextBox);
		endPanel1.add(destLabel);
		endPanel1.add(destCityTextBox);
		endPanel2.add(destStreetTextBox);
		startPanel1.setSize("200px", "10px");
		startPanel1.setCellHorizontalAlignment(startCityTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		startPanel2.setSize("200px", "10px");
		startPanel2.setCellHorizontalAlignment(startStreetTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		endPanel1.setSize("200px", "10px");
		endPanel1.setCellHorizontalAlignment(destCityTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		endPanel2.setSize("200px", "10px");
		endPanel2.setCellHorizontalAlignment(destStreetTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		add(startPanel1);
		add(startPanel2);
		add(endPanel1);
		add(endPanel2);
	}

	public TextBox getStartCityTextBox() {
		return startCityTextBox;
	}

	public TextBox getStartStreetTextBox() {
		return startStreetTextBox;
	}

	public TextBox getDestCityTextBox() {
		return destCityTextBox;
	}

	public TextBox getDestStreetTextBox() {
		return destStreetTextBox;
	}
	
	
}
