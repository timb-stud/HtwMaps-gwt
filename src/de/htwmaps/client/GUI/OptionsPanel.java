package de.htwmaps.client.GUI;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.htwmaps.client.GUI.exceptions.MotorWaySpeedException;
import de.htwmaps.client.GUI.exceptions.PrimarySpeedException;
import de.htwmaps.client.GUI.exceptions.ResidentialSpeedException;

/**
 * Erstellt das Optionspanel, damit der Benutzer eigene Routingoptionen wählen kann
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 */
public class OptionsPanel extends VerticalPanel {
	
	HorizontalPanel emptyPanel1 = new HorizontalPanel();
	HorizontalPanel emptyPanel2 = new HorizontalPanel();
	HorizontalPanel emptyPanel3 = new HorizontalPanel();
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

	/**
	 * Intialisiert das Panel mit Standardwerten
	 */
	public OptionsPanel() {
		setVisible(false);		
		
		//Welcher weg?
		emptyPanel1.setSize("280px", "15px");
		wegLabel.addStyleName("optionsLabel");
		wegPanel.add(fastestRadioButton);
		wegPanel.add(shortestRadioButton);
		fastestRadioButton.setValue(true);
		wegPanel.setSize("280px", "15px");
		wegPanel.setCellHorizontalAlignment(fastestRadioButton, HasHorizontalAlignment.ALIGN_LEFT);
		wegPanel.setCellHorizontalAlignment(shortestRadioButton, HasHorizontalAlignment.ALIGN_CENTER);
		add(wegLabel);
		add(wegPanel);
		add(emptyPanel1);
		
		//Speed
		emptyPanel2.setSize("280px", "15px");
		speedLabel.addStyleName("optionsLabel");
		speedAutobPanel.setSize("250px", "15px");
		motorwaySpeedTextBox.setSize("40px", "15px");
		motorwaySpeedTextBox.setText("100");
		speedAutobPanel.add(motorwaySpeedLabel);
		speedAutobPanel.add(motorwaySpeedTextBox);
		speedAutobPanel.setCellHorizontalAlignment(motorwaySpeedTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		speedLandPanel.setSize("250px", "15px");
		primarySpeedTextBox.setSize("40px", "15px");
		primarySpeedTextBox.setText("60");
		speedLandPanel.add(primarySpeedLabel);
		speedLandPanel.add(primarySpeedTextBox);
		speedLandPanel.setCellHorizontalAlignment(primarySpeedTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		residentialSpeedTextBox.setSize("40px", "15px");
		residentialSpeedTextBox.setText("40");
		residentialSpeedPanel.setSize("250px", "15px");
		residentialSpeedPanel.add(residentialSpeedLabel);
		residentialSpeedPanel.add(residentialSpeedTextBox);
		residentialSpeedPanel.setCellHorizontalAlignment(residentialSpeedTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		add(speedLabel);
		add(speedAutobPanel);
		add(speedLandPanel);
		add(residentialSpeedPanel);
		add(emptyPanel2);
		
		//Algo
		emptyPanel3.setSize("280px", "15px");
		algoLabel.addStyleName("optionsLabel");
		algoPanel.add(aStarRadioButton);
		algoPanel.add(aStarBiRadioButton);
		aStarRadioButton.setValue(true);
		algoPanel.setSize("200px", "15px");
		algoPanel.setCellHorizontalAlignment(aStarRadioButton, HasHorizontalAlignment.ALIGN_LEFT);
		algoPanel.setCellHorizontalAlignment(aStarBiRadioButton, HasHorizontalAlignment.ALIGN_RIGHT);
		add(algoLabel);
		add(algoPanel);
		add(emptyPanel3);
	}

	/**
	 * @return Gibt den RadioButton für den schnellsten Weg zurück
	 */
	public RadioButton getFastestRadioButton() {
		return fastestRadioButton;
	}

	/**
	 * @return Gibt den RadioButton für den schnellsten Weg zurück
	 */
	public RadioButton getShortestRadioButton() {
		return shortestRadioButton;
	}

	/**
	 * @return Gibt den RadioButton für den A* Algorithmus zurück
	 */
	public RadioButton getaStarRadioButton() {
		return aStarRadioButton;
	}

	/**
	 * @return Gibt den RadioButton für den A* bidirektional Algorithmus zurück
	 */
	public RadioButton getaStarBiRadioButton() {
		return aStarBiRadioButton;
	}

	/**
	 * @return Gibt die TextBox für die Autobahngeschwindigkeit zurück
	 */
	public TextBox getMotorwaySpeedTextBox() {
		return motorwaySpeedTextBox;
	}

	/**
	 * @return Gibt die TextBox für die Landstraßengeschwindigkeit zurück
	 */
	public TextBox getPrimarySpeedTextBox() {
		return primarySpeedTextBox;
	}

	/**
	 * @return Gibt die TextBox für die Innerortsgeschwindigkeit zurück
	 */
	public TextBox getResidentialSpeedTextBox() {
		return residentialSpeedTextBox;
	}
	
	/**
	 * Liest die Autobahngeschwindigkeits Eingabe des Benutzers ein.
	 * 
	 * @return Autobahngeschwindigkeits Eingabe des Benutzers
	 * @throws MotorWaySpeedException wenn die Eingabe < 1 oder > 1000 ist
	 */
	public int getMotorWaySpeed() throws MotorWaySpeedException{
		try{
			int mws = Integer.parseInt(motorwaySpeedTextBox.getText().trim());
			if(mws < 1 || mws > 1000)
				throw new MotorWaySpeedException();
			return mws;
		} catch(NumberFormatException e){
			throw new MotorWaySpeedException();
		}
	}
	
	/**
	 * Liest die Landstraßengeschwindigkeits Eingabe des Benutzers ein.
	 * @return Landstraßengeschwindigkeits Eingabe des Benutzers
	 * @throws PrimarySpeedException wenn die Eingabe < 1 oder > 1000
	 */
	public int getPrimarySpeed() throws PrimarySpeedException{
		try{
			int ps = Integer.parseInt(primarySpeedTextBox.getText().trim());
			if(ps < 1 || ps > 1000)
				throw new PrimarySpeedException();
			return ps;
		} catch(NumberFormatException e){
			throw new PrimarySpeedException();
		}
	}
	
	/**
	 * Liest die Innerortsgeschwindigkeits Eingabe des Benutzers ein.
	 * @return Innerortsgeschwindigkeits Eingabe des Benutzers
	 * @throws ResidentialSpeedException wenn die Eingabe < 1 oder > 1000
	 */
	public int getResidentialSpeed() throws ResidentialSpeedException{
		try{
			int rs = Integer.parseInt(residentialSpeedTextBox.getText().trim());
			if(rs < 1 || rs > 1000)
				throw new ResidentialSpeedException();
			return rs;
		} catch(NumberFormatException e){
			throw new ResidentialSpeedException();
		}
	}
}
