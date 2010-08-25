package de.htwmaps.client.GUI;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.htwmaps.client.CityStreetSuggestService;
import de.htwmaps.client.CityStreetSuggestServiceAsync;
import de.htwmaps.client.CitySuggestionKeyUpHandler;
import de.htwmaps.client.StreetSuggestionKeyUpHandler;

public class LocationsPanel extends VerticalPanel {

	HorizontalPanel startPanel1 = new HorizontalPanel();
	HorizontalPanel startPanel2 = new HorizontalPanel();
	HorizontalPanel endPanel1 = new HorizontalPanel();
	HorizontalPanel endPanel2 = new HorizontalPanel();
	Label startLabel = new Label("Start:");
	Label destLabel = new Label("Ende:");
	MultiWordSuggestOracle startCitySuggestOracle = new MultiWordSuggestOracle();
	MultiWordSuggestOracle startStreetSuggestOracle = new MultiWordSuggestOracle();
	MultiWordSuggestOracle destCitySuggestOracle = new MultiWordSuggestOracle();
	MultiWordSuggestOracle destStreetSuggestOracle = new MultiWordSuggestOracle();
	SuggestBox startCitySuggestBox = new SuggestBox(startCitySuggestOracle);
	SuggestBox startStreetSuggestBox = new SuggestBox(startStreetSuggestOracle);
	SuggestBox destCitySuggestBox = new SuggestBox(destCitySuggestOracle);
	SuggestBox destStreetSuggestBox = new SuggestBox(destStreetSuggestOracle);
	CitySuggestionKeyUpHandler startCity = null;
	StreetSuggestionKeyUpHandler startStreet = null;
	CitySuggestionKeyUpHandler destCity = null;
	StreetSuggestionKeyUpHandler destStreet = null;
	
	CityStreetSuggestServiceAsync cityStreetSuggestSvc = GWT.create(CityStreetSuggestService.class);

	public LocationsPanel() {
	
		startCitySuggestBox.setText("Koblenz");
		startStreetSuggestBox.setText("Paradies");
		destCitySuggestBox.setText("Saarburg");
		destStreetSuggestBox.setText("Bachweg");
		
		startCity = new CitySuggestionKeyUpHandler(cityStreetSuggestSvc, startCitySuggestOracle);
		startStreet = new StreetSuggestionKeyUpHandler(cityStreetSuggestSvc, startStreetSuggestOracle, startCitySuggestBox);
		destCity = new CitySuggestionKeyUpHandler(cityStreetSuggestSvc, destCitySuggestOracle);
		destStreet = new StreetSuggestionKeyUpHandler(cityStreetSuggestSvc, destStreetSuggestOracle, destCitySuggestBox);
		startCitySuggestBox.getTextBox().addKeyUpHandler(startCity);
		startStreetSuggestBox.getTextBox().addKeyUpHandler(startStreet);
		destCitySuggestBox.getTextBox().addKeyUpHandler(destCity);
		destStreetSuggestBox.getTextBox().addKeyUpHandler(destStreet);

		startPanel1.add(startLabel);
		startPanel1.add(startCitySuggestBox);
		startPanel2.add(startStreetSuggestBox);
		endPanel1.add(destLabel);
		endPanel1.add(destCitySuggestBox);
		endPanel2.add(destStreetSuggestBox);
		startPanel1.setSize("205px", "10px");
		startPanel1.setCellHorizontalAlignment(startCitySuggestBox, HasHorizontalAlignment.ALIGN_RIGHT);
		startPanel2.setSize("205px", "10px");
		startPanel2.setCellHorizontalAlignment(startStreetSuggestBox, HasHorizontalAlignment.ALIGN_RIGHT);
		endPanel1.setSize("205px", "10px");
		endPanel1.setCellHorizontalAlignment(destCitySuggestBox, HasHorizontalAlignment.ALIGN_RIGHT);
		endPanel2.setSize("205px", "10px");
		endPanel2.setCellHorizontalAlignment(destStreetSuggestBox, HasHorizontalAlignment.ALIGN_RIGHT);
		
		add(startPanel1);
		add(startPanel2);
		add(endPanel1);
		add(endPanel2);
	}

	public CitySuggestionKeyUpHandler getStartCity() {
		return startCity;
	}

	public StreetSuggestionKeyUpHandler getStartStreet() {
		return startStreet;
	}

	public CitySuggestionKeyUpHandler getDestCity() {
		return destCity;
	}

	public StreetSuggestionKeyUpHandler getDestStreet() {
		return destStreet;
	}

	public SuggestBox getStartCityTextBox() {
		return startCitySuggestBox;
	}

	public SuggestBox getStartStreetTextBox() {
		return startStreetSuggestBox;
	}

	public SuggestBox getDestCityTextBox() {
		return destCitySuggestBox;
	}

	public SuggestBox getDestStreetTextBox() {
		return destStreetSuggestBox;
	}


}
