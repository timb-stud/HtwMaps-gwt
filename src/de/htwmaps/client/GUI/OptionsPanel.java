package de.htwmaps.client.GUI;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OptionsPanel extends VerticalPanel {
	
	Label wegLabel = new Label("Routenart waehlen:");
	HorizontalPanel wegPanel = new HorizontalPanel();
	RadioButton fastestRadioButton = new RadioButton("weg", "schnellste");
	RadioButton shortestRadioButton = new RadioButton("weg", "kuerzeste");
	Label speedLabel = new Label("Geschwindigkeit waehlen:");
	HorizontalPanel speedLandPanel = new HorizontalPanel();
	HorizontalPanel speedAutobPanel = new HorizontalPanel();
	HorizontalPanel residentialSpeedPanel = new HorizontalPanel();
	Label motorwaySpeedLabel = new Label("Autobahngeschwindigkeit:");
	Label primarySpeedLabel = new Label("Landstrassengeschwindigkeit:");
	Label residentialSpeedLabel = new Label("Innerorts:");
	TextBox motorwaySpeedTextBox = new TextBox();
	TextBox primarySpeedTextBox = new TextBox();
	TextBox residentialSpeedTextBox = new TextBox();
	Label algoLabel = new Label("Routingalgorithmus waehlen:");
	HorizontalPanel algoPanel = new HorizontalPanel();
	RadioButton aStarRadioButton = new RadioButton("algo", "A*");
	RadioButton aStarBiRadioButton = new RadioButton("algo", "A* Bidirektional");

	public OptionsPanel() {
		setVisible(false);		
		
		//Welcher weg?
		wegPanel.add(fastestRadioButton);
		wegPanel.add(shortestRadioButton);
		shortestRadioButton.setValue(true);
		wegPanel.setSize("300px", "10px");
		wegPanel.setCellHorizontalAlignment(fastestRadioButton, HasHorizontalAlignment.ALIGN_LEFT);
		wegPanel.setCellHorizontalAlignment(shortestRadioButton, HasHorizontalAlignment.ALIGN_CENTER);
		add(wegLabel);
		add(wegPanel);
		
		//Speed
		residentialSpeedPanel.add(residentialSpeedLabel);
		residentialSpeedPanel.add(residentialSpeedTextBox);
		residentialSpeedLabel.setSize("250px", "10px");
		residentialSpeedTextBox.setSize("40px", "10px");
		residentialSpeedTextBox.setText("50");
		speedLandPanel.add(primarySpeedLabel);
		speedLandPanel.add(primarySpeedTextBox);
		speedAutobPanel.add(motorwaySpeedLabel);
		speedAutobPanel.add(motorwaySpeedTextBox);
		speedLandPanel.setSize("250px", "10px");
		primarySpeedTextBox.setSize("40px", "10px");
		primarySpeedTextBox.setText("80");
		speedLandPanel.setCellHorizontalAlignment(primarySpeedTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		speedAutobPanel.setSize("250px", "10px");
		motorwaySpeedTextBox.setSize("40px", "10px");
		motorwaySpeedTextBox.setText("130");
		speedAutobPanel.setCellHorizontalAlignment(motorwaySpeedTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		add(speedLabel);
		add(speedLandPanel);
		add(speedAutobPanel);
		add(residentialSpeedPanel);
		
		//Algo
		algoPanel.add(aStarRadioButton);
		algoPanel.add(aStarBiRadioButton);
		aStarRadioButton.setValue(true);
		algoPanel.setSize("200px", "10px");
		algoPanel.setCellHorizontalAlignment(aStarRadioButton, HasHorizontalAlignment.ALIGN_LEFT);
		algoPanel.setCellHorizontalAlignment(aStarBiRadioButton, HasHorizontalAlignment.ALIGN_RIGHT);
		add(algoLabel);
		add(algoPanel);
	}

	public RadioButton getFastestRadioButton() {
		return fastestRadioButton;
	}

	public RadioButton getShortestRadioButton() {
		return shortestRadioButton;
	}

	public RadioButton getaStarRadioButton() {
		return aStarRadioButton;
	}

	public RadioButton getaStarBiRadioButton() {
		return aStarBiRadioButton;
	}

	public TextBox getMotorwaySpeedTextBox() {
		return motorwaySpeedTextBox;
	}

	public TextBox getPrimarySpeedTextBox() {
		return primarySpeedTextBox;
	}

	public TextBox getResidentialSpeedTextBox() {
		return residentialSpeedTextBox;
	}
	
	
	
}