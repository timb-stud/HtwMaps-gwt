package de.htwmaps.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HtwMaps implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
//		HorizontalSplitPanel mainPanel = new HorizontalSplitPanel();
		VerticalPanel vPanel = new VerticalPanel();
		Label error = new Label("Error");
		error.addStyleName("errorLabel");
		
		HorizontalPanel startPanel = new HorizontalPanel();
		HorizontalPanel endPanel = new HorizontalPanel();
		Label startLabel = new Label("Start:");
		Label endeLabel = new Label("Ende:");
		TextBox startTextBox = new TextBox();
		TextBox endTextBox = new TextBox();
		Button calc = new Button("calc");
		
		HorizontalPanel wegPanel = new HorizontalPanel();
		RadioButton schnellste = new RadioButton("weg", "schnellste");
		RadioButton kuerzeste = new RadioButton("weg", "kuerzeste");
		
		HorizontalPanel speedLandPanel = new HorizontalPanel();
		HorizontalPanel speedAutobPanel = new HorizontalPanel();
		Label speedLandLabel = new Label("Landstrassengeschwindigkeit:");
		Label speedAutobLabel = new Label("Autobahngeschwindigkeit:");
		TextBox speedLandTextBox = new TextBox();
		TextBox speedAutobTextBox = new TextBox();
		
		HorizontalPanel algoPanel = new HorizontalPanel();
		RadioButton aStern = new RadioButton("algo", "A*");
		RadioButton aThreaded = new RadioButton("algo", "A* Threaded");
		
		StackPanel stackPanel = new StackPanel();
		VerticalPanel zusammenfassungPanel = new VerticalPanel();
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
		
		VerticalPanel wegbeschreibungPanel = new VerticalPanel();
		Label wegbeschreibungLabel = new Label("Recht -> links geradeaus?");
		
		
		//Star|Ende
		startTextBox.setText("Stadt, Strasse");
		endTextBox.setText("Stadt, Strasse");
		startPanel.add(startLabel);
		startPanel.add(startTextBox);
		endPanel.add(endeLabel);
		endPanel.add(endTextBox);
		startPanel.setSize("250px", "10px");
		startPanel.setCellHorizontalAlignment(startTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		endPanel.setSize("250px", "10px");
		endPanel.setCellHorizontalAlignment(endTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		vPanel.add(startPanel);
		vPanel.add(endPanel);
		
		//Button Calc
		vPanel.add(calc);
		vPanel.setCellHorizontalAlignment(calc, HasHorizontalAlignment.ALIGN_RIGHT);
		
		//Welcher weg?
		wegPanel.add(schnellste);
		wegPanel.add(kuerzeste);
		schnellste.setValue(true);
		wegPanel.setSize("200px", "10px");
		wegPanel.setCellHorizontalAlignment(schnellste, HasHorizontalAlignment.ALIGN_LEFT);
		wegPanel.setCellHorizontalAlignment(kuerzeste, HasHorizontalAlignment.ALIGN_RIGHT);
		vPanel.add(wegPanel);
		
		//Spped
		speedLandPanel.add(speedLandLabel);
		speedLandPanel.add(speedLandTextBox);
		speedAutobPanel.add(speedAutobLabel);
		speedAutobPanel.add(speedAutobTextBox);
		speedLandPanel.setSize("350px", "10px");
		speedLandPanel.setCellHorizontalAlignment(speedLandTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		speedAutobPanel.setSize("350px", "10px");
		speedAutobPanel.setCellHorizontalAlignment(speedAutobTextBox, HasHorizontalAlignment.ALIGN_RIGHT);
		vPanel.add(speedLandPanel);
		vPanel.add(speedAutobPanel);
		
		//Algo
		algoPanel.add(aStern);
		algoPanel.add(aThreaded);
		aStern.setValue(true);
		algoPanel.setSize("200px", "10px");
		algoPanel.setCellHorizontalAlignment(aStern, HasHorizontalAlignment.ALIGN_LEFT);
		algoPanel.setCellHorizontalAlignment(aThreaded, HasHorizontalAlignment.ALIGN_RIGHT);
		vPanel.add(algoPanel);
		
		//StackPanel		
		fahrzeitPanel.add(fahrzeitLabel);
		fahrzeitPanel.add(fahrzeitResultLabel);
		fahrzeitPanel.setSize("300px", "10px");
		fahrzeitPanel.setCellHorizontalAlignment(fahrzeitResultLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		weglaengePanel.add(weglaengeLabel);
		weglaengePanel.add(weglaengeResultLabel);
		weglaengePanel.setSize("300px", "10px");
		weglaengePanel.setCellHorizontalAlignment(weglaengeResultLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		autobahnPanel.add(autobahnLabel);
		autobahnPanel.add(autobahnResultLabel);
		autobahnPanel.setSize("300px", "10px");
		autobahnPanel.setCellHorizontalAlignment(autobahnResultLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		landstrassePanel.add(landstrasseLabel);
		landstrassePanel.add(landstrasseResultLabel);
		landstrassePanel.setSize("300px", "10px");
		landstrassePanel.setCellHorizontalAlignment(landstrasseResultLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		innerortsPanel.add(innerortsLabel);
		innerortsPanel.add(innerortsResultLabel);
		innerortsPanel.setSize("300px", "10px");
		innerortsPanel.setCellHorizontalAlignment(innerortsResultLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		zusammenfassungPanel.add(fahrzeitPanel);
		zusammenfassungPanel.add(weglaengePanel);
		zusammenfassungPanel.add(autobahnPanel);
		zusammenfassungPanel.add(landstrassePanel);
		zusammenfassungPanel.add(innerortsPanel);
		
		wegbeschreibungLabel.setSize("300px", "10px");
		wegbeschreibungPanel.add(wegbeschreibungLabel);
		
		stackPanel.add(zusammenfassungPanel, "Zusammenfassung");
		stackPanel.add(wegbeschreibungPanel, "Wegbeschreibung");
		
		
		stackPanel.setSize("350px", "10px");
		vPanel.add(stackPanel);
		
		vPanel.setSize("300px", "10px");
//		mainPanel.add(vPanel);
//		mainPanel.add(new HTML("Map"));
//
//		mainPanel.setSplitPosition("27em");
//		mainPanel.setSize("100%", "38em");
//		RootPanel.get("controlsPanel").add(mainPanel);
		vPanel.addStyleName("controlsPanel");
		RootPanel.get("controlsPanel").add(vPanel);
		RootPanel.get("errorLabelContainer").add(error);
	}
}
