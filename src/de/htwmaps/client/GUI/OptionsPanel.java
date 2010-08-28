package de.htwmaps.client.GUI;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OptionsPanel extends VerticalPanel {
	
	Label wegLabel = new Label(StringConstant.ROUTENART);
	HorizontalPanel wegPanel = new HorizontalPanel();
	RadioButton fastestRadioButton = new RadioButton(StringConstant.WEG, StringConstant.SCHNELLSTE);
	RadioButton shortestRadioButton = new RadioButton(StringConstant.WEG, StringConstant.KUERZESTE);
	Label speedLabel = new Label(StringConstant.SPEED);
	HorizontalPanel speedLandPanel = new HorizontalPanel();
	HorizontalPanel speedAutobPanel = new HorizontalPanel();
	HorizontalPanel residentialSpeedPanel = new HorizontalPanel();
	Label motorwaySpeedLabel = new Label(StringConstant.SPEED_AUTOBAHN);
	Label primarySpeedLabel = new Label(StringConstant.SPEED_LANDSTRASSE);
	Label residentialSpeedLabel = new Label(StringConstant.SPEED_INNERORTS);
	TextBox motorwaySpeedTextBox = new TextBox();
	TextBox primarySpeedTextBox = new TextBox();
	TextBox residentialSpeedTextBox = new TextBox();
	Label algoLabel = new Label(StringConstant.WAEHLE_ALGORITHMUS);
	HorizontalPanel algoPanel = new HorizontalPanel();
	RadioButton aStarRadioButton = new RadioButton(StringConstant.ALGORITHMUS, StringConstant.ASTERN);
	RadioButton aStarBiRadioButton = new RadioButton(StringConstant.ALGORITHMUS, StringConstant.ASTERN_BIDIREKTIONAL);

	public OptionsPanel() {
		setVisible(false);		
		
		//Welcher weg?
		wegPanel.add(fastestRadioButton);
		wegPanel.add(shortestRadioButton);
		fastestRadioButton.setValue(true);
		wegPanel.setSize("300px", "10px");
		wegPanel.setCellHorizontalAlignment(fastestRadioButton, HasHorizontalAlignment.ALIGN_LEFT);
		wegPanel.setCellHorizontalAlignment(shortestRadioButton, HasHorizontalAlignment.ALIGN_CENTER);
		add(wegLabel);
		add(wegPanel);
		
		//Speed
		speedAutobPanel.setSize("250px", "10px");
		motorwaySpeedTextBox.setSize("40px", "10px");
		motorwaySpeedTextBox.setText("100");
		speedAutobPanel.add(motorwaySpeedLabel);
		speedAutobPanel.add(motorwaySpeedTextBox);
		speedAutobPanel.setCellHorizontalAlignment(motorwaySpeedTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		speedLandPanel.setSize("250px", "10px");
		primarySpeedTextBox.setSize("40px", "10px");
		primarySpeedTextBox.setText("60");
		speedLandPanel.add(primarySpeedLabel);
		speedLandPanel.add(primarySpeedTextBox);
		speedLandPanel.setCellHorizontalAlignment(primarySpeedTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		residentialSpeedTextBox.setSize("40px", "10px");
		residentialSpeedTextBox.setText("40");
		residentialSpeedPanel.setSize("250px", "10px");
		residentialSpeedPanel.add(residentialSpeedLabel);
		residentialSpeedPanel.add(residentialSpeedTextBox);
		residentialSpeedPanel.setCellHorizontalAlignment(residentialSpeedTextBox, HasHorizontalAlignment.ALIGN_RIGHT);

		add(speedLabel);
		add(speedAutobPanel);
		add(speedLandPanel);
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
