package de.htwmaps.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HtwMaps implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	@SuppressWarnings("deprecation")
	public void onModuleLoad() {
//		HorizontalSplitPanel mainPanel = new HorizontalSplitPanel();
		VerticalPanel vPanel = new VerticalPanel();
		Label error = new Label("Error");
		error.addStyleName("errorLabel");
	
		
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
		Button calc = new Button("Route berechnen");
		
		
		final Hyperlink optionHyperlink =  new Hyperlink("Optionen anzeigen", "optionenPanel");
		final VerticalPanel optionenPanel = new VerticalPanel();
		
		Label wegLabel = new Label("Routenart waehlen:");
		HorizontalPanel wegPanel = new HorizontalPanel();
		RadioButton schnellste = new RadioButton("weg", "schnellste");
		RadioButton kuerzeste = new RadioButton("weg", "kuerzeste");
		RadioButton beide = new RadioButton("weg", "beide");
		
		
		Label speedLabel = new Label("Geschwinsigkeit waehlen:");
		HorizontalPanel speedLandPanel = new HorizontalPanel();
		HorizontalPanel speedAutobPanel = new HorizontalPanel();
		Label speedLandLabel = new Label("Landstrassengeschwindigkeit:");
		Label speedAutobLabel = new Label("Autobahngeschwindigkeit:");
		TextBox speedLandTextBox = new TextBox();
		TextBox speedAutobTextBox = new TextBox();
		
		Label algoLabel = new Label("Routinglgorithmus waehlen:");
		HorizontalPanel algoPanel = new HorizontalPanel();
		RadioButton aStern = new RadioButton("algo", "A*");
		RadioButton aThreaded = new RadioButton("algo", "A* Threaded");
		
		
		final Hyperlink zusammenfassungHyperlink = new Hyperlink("Routenzusammenfassung anzeigen", "zusammenfassungPanel");
		final VerticalPanel zusammenfassungPanel = new VerticalPanel();
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
		
		
		final Hyperlink wegbeschreibungHyperlink = new Hyperlink("Wegbeschreibung anzeigen", "wegbeschreibungPanel");
		final VerticalPanel wegbeschreibungPanel = new VerticalPanel();
		Label wegbeschreibungLabel = new Label("Recht -> links geradeaus?");
		
		
		//Star|Ende
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
		vPanel.add(startPanel1);
		vPanel.add(startPanel2);
		vPanel.add(endPanel1);
		vPanel.add(endPanel2);
		
		//Button Calc
		vPanel.add(calc);
		vPanel.setCellHorizontalAlignment(calc, HasHorizontalAlignment.ALIGN_RIGHT);
		
		//Option Panel
		optionHyperlink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (optionenPanel.isVisible()) {
					optionHyperlink.setText("Optionen anzeigen");
					optionenPanel.setVisible(false);
				} else {
					optionHyperlink.setText("Optionen ausblenden");
					optionenPanel.setVisible(true);
				}
			}
		});
		
		vPanel.add(optionHyperlink);
		vPanel.add(optionenPanel);
		optionenPanel.setVisible(false);		
		
		//Welcher weg?
		wegPanel.add(schnellste);
		wegPanel.add(kuerzeste);
		wegPanel.add(beide);
		schnellste.setValue(true);
		wegPanel.setSize("300px", "10px");
		wegPanel.setCellHorizontalAlignment(schnellste, HasHorizontalAlignment.ALIGN_LEFT);
		wegPanel.setCellHorizontalAlignment(kuerzeste, HasHorizontalAlignment.ALIGN_CENTER);
		wegPanel.setCellHorizontalAlignment(beide, HasHorizontalAlignment.ALIGN_RIGHT);
		optionenPanel.add(wegLabel);
		optionenPanel.add(wegPanel);
		
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
		optionenPanel.add(speedLabel);
		optionenPanel.add(speedLandPanel);
		optionenPanel.add(speedAutobPanel);
		
		//Algo
		algoPanel.add(aStern);
		algoPanel.add(aThreaded);
		aStern.setValue(true);
		algoPanel.setSize("200px", "10px");
		algoPanel.setCellHorizontalAlignment(aStern, HasHorizontalAlignment.ALIGN_LEFT);
		algoPanel.setCellHorizontalAlignment(aThreaded, HasHorizontalAlignment.ALIGN_RIGHT);
		optionenPanel.add(algoLabel);
		optionenPanel.add(algoPanel);
		
		//Zusammenfassung
		zusammenfassungHyperlink.addStyleName("anzeigeHyperlink");
		zusammenfassungHyperlink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (zusammenfassungPanel.isVisible()) {
					zusammenfassungHyperlink.setText("Routenzusammenfassung anzeigen");
					zusammenfassungPanel.setVisible(false);
				} else {
					zusammenfassungHyperlink.setText("Routenzusammenfassung ausblenden");
					zusammenfassungPanel.setVisible(true);
				}
			}
		});
		vPanel.add(zusammenfassungHyperlink);
		vPanel.add(zusammenfassungPanel);
		zusammenfassungPanel.setVisible(false);
		
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
	
		wegbeschreibungHyperlink.addStyleName("anzeigeHyperlink");
		wegbeschreibungHyperlink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (wegbeschreibungPanel.isVisible()) {
					wegbeschreibungHyperlink.setText("Wegbeschreibung anzeigen");
					wegbeschreibungPanel.setVisible(false);
				} else {
					wegbeschreibungHyperlink.setText("Wegbeschreibung ausblenden");
					wegbeschreibungPanel.setVisible(true);
				}
			}
		});
		vPanel.add(wegbeschreibungHyperlink);
		vPanel.add(wegbeschreibungPanel);
		wegbeschreibungPanel.setVisible(false);
		
		wegbeschreibungLabel.setSize("300px", "10px");
		wegbeschreibungPanel.add(wegbeschreibungLabel);
		
		
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
