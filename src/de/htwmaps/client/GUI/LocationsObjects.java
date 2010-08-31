package de.htwmaps.client.GUI;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;

import de.htwmaps.client.CityStreetSuggestService;
import de.htwmaps.client.CityStreetSuggestServiceAsync;
import de.htwmaps.client.CitySuggestionKeyUpHandler;
import de.htwmaps.client.StreetSuggestionKeyUpHandler;

public class LocationsObjects {

	Label descriptionLabel = new Label();
	MultiWordSuggestOracle citySuggestOracle = new MultiWordSuggestOracle();
	MultiWordSuggestOracle streetSuggestOracle = new MultiWordSuggestOracle();
	SuggestBox citySuggestBox = new SuggestBox(citySuggestOracle);
	SuggestBox streetSuggestBox = new SuggestBox(streetSuggestOracle);
	CitySuggestionKeyUpHandler cityHandler = null;
	StreetSuggestionKeyUpHandler streetHandler = null;
	Button addButton = new Button("+");
	AddHandler addHandler = null;
	Button removeButton = new Button("-");
	RemoveHandler removeHandler = null;
	
	CityStreetSuggestServiceAsync cityStreetSuggestSvc = GWT.create(CityStreetSuggestService.class);

	public LocationsObjects(String label) {
		
		this.descriptionLabel.setText(label);
		
		cityHandler = new CitySuggestionKeyUpHandler(cityStreetSuggestSvc, citySuggestOracle);
		streetHandler = new StreetSuggestionKeyUpHandler(cityStreetSuggestSvc, streetSuggestOracle, citySuggestBox);
		citySuggestBox.getTextBox().addKeyUpHandler(cityHandler);
		citySuggestBox.getTextBox().addKeyUpHandler(streetHandler);
		
		citySuggestBox.setSize("80px", "10px");
		streetSuggestBox.setSize("120px", "10px");
		addHandler = new AddHandler(Integer.parseInt(label));
		addButton.setSize("25px", "25px");
		addButton.addClickHandler(addHandler);
		removeHandler = new RemoveHandler(Integer.parseInt(label));
		removeButton.setSize("25px", "25px");
		removeButton.addClickHandler(removeHandler);
	}
	
	public void setVisibleRemoveButton(boolean visible) {
		removeButton.setVisible(visible);
	}
	
	public RemoveHandler getRemoveHandler() {
		return removeHandler;
	}

	public AddHandler getAddHandler() {
		return addHandler;
	}

	public Button getAddButton() {
		return addButton;
	}

	public Label getDescriptionLabel() {
		return descriptionLabel;
	}

	public void setDescriptionLabel(String descriptionLabel) {
		this.descriptionLabel.setText(descriptionLabel);
	}
	
	public SuggestBox getCitySuggestBox() {
		return citySuggestBox;
	}

	public SuggestBox getStreetSuggestBox() {
		return streetSuggestBox;
	}

	public Button getRemoveButton() {
		return removeButton;
	}
	
	public CitySuggestionKeyUpHandler getCityHandler() {
		return cityHandler;
	}

	public StreetSuggestionKeyUpHandler getStreetHandler() {
		return streetHandler;
	}
}