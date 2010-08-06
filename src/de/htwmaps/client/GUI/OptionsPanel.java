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
	RadioButton schnellste = new RadioButton("weg", "schnellste");
	RadioButton kuerzeste = new RadioButton("weg", "kuerzeste");
	RadioButton beide = new RadioButton("weg", "beide");
	Label speedLabel = new Label("Geschwindigkeit waehlen:");
	HorizontalPanel speedLandPanel = new HorizontalPanel();
	HorizontalPanel speedAutobPanel = new HorizontalPanel();
	Label speedLandLabel = new Label("Landstrassengeschwindigkeit:");
	Label speedAutobLabel = new Label("Autobahngeschwindigkeit:");
	TextBox speedLandTextBox = new TextBox();
	TextBox speedAutobTextBox = new TextBox();
	Label algoLabel = new Label("Routingalgorithmus waehlen:");
	HorizontalPanel algoPanel = new HorizontalPanel();
	RadioButton aStern = new RadioButton("algo", "A*");
	RadioButton aThreaded = new RadioButton("algo", "A* Threaded");

	public OptionsPanel() {
		setVisible(false);		
		
		//Welcher weg?
		wegPanel.add(schnellste);
		wegPanel.add(kuerzeste);
		wegPanel.add(beide);
		schnellste.setValue(true);
		wegPanel.setSize("300px", "10px");
		wegPanel.setCellHorizontalAlignment(schnellste, HasHorizontalAlignment.ALIGN_LEFT);
		wegPanel.setCellHorizontalAlignment(kuerzeste, HasHorizontalAlignment.ALIGN_CENTER);
		wegPanel.setCellHorizontalAlignment(beide, HasHorizontalAlignment.ALIGN_RIGHT);
		add(wegLabel);
		add(wegPanel);
		
		//Spped
		speedLandPanel.add(speedLandLabel);
		speedLandPanel.add(speedLandTextBox);
		speedAutobPanel.add(speedAutobLabel);
		speedAutobPanel.add(speedAutobTextBox);
		speedLandPanel.setSize("250px", "10px");
		speedLandTextBox.setSize("40px", "10px");
		speedLandPanel.setCellHorizontalAlignment(speedLandTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		speedAutobPanel.setSize("250px", "10px");
		speedAutobTextBox.setSize("40px", "10px");
		speedAutobPanel.setCellHorizontalAlignment(speedAutobTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		add(speedLabel);
		add(speedLandPanel);
		add(speedAutobPanel);
		
		//Algo
		algoPanel.add(aStern);
		algoPanel.add(aThreaded);
		aStern.setValue(true);
		algoPanel.setSize("200px", "10px");
		algoPanel.setCellHorizontalAlignment(aStern, HasHorizontalAlignment.ALIGN_LEFT);
		algoPanel.setCellHorizontalAlignment(aThreaded, HasHorizontalAlignment.ALIGN_RIGHT);
		add(algoLabel);
		add(algoPanel);
	}
	
}
