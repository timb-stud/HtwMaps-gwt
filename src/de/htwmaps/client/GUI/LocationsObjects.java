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

/**
 * Erstellt die verschiedenen Objekte für die flexible Tablle für das Routing
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 */
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

	/**
	 * Standardkonstrukto erstellt eine neue Zeile mit allen Objekten
	 * 
	 * @param label Name der Tabellenzeile
	 */
	public LocationsObjects(String label) {
		
		this.descriptionLabel.setText(label);
		
		cityHandler = new CitySuggestionKeyUpHandler(cityStreetSuggestSvc, citySuggestOracle);
		streetHandler = new StreetSuggestionKeyUpHandler(cityStreetSuggestSvc, streetSuggestOracle, citySuggestBox);
		citySuggestBox.getTextBox().addKeyUpHandler(cityHandler);
		streetSuggestBox.getTextBox().addKeyUpHandler(streetHandler);
		citySuggestBox.setLimit(20);
		streetSuggestBox.setLimit(20);
		
		citySuggestBox.setSize("80px", "15px");
		streetSuggestBox.setSize("120px", "15px");
		addHandler = new AddHandler(Integer.parseInt(label));
		addButton.setSize("25px", "25px");
		addButton.addClickHandler(addHandler);
		removeHandler = new RemoveHandler(Integer.parseInt(label));
		removeButton.setSize("25px", "25px");
		removeButton.addClickHandler(removeHandler);
	}
	
	/**
	 * Macht den Button sichtbar bzw. unsichtbar
	 * 
	 * @param visible true -> Button wird sichtbar
	 * 				  false -> Button wird unsichtbar
	 */
	public void setVisibleRemoveButton(boolean visible) {
		removeButton.setVisible(visible);
	}
	
	/**
	 * @return Gibt den Löschen Handler zurück
	 */
	public RemoveHandler getRemoveHandler() {
		return removeHandler;
	}

	/**
	 * @return Gibt den Hinzufügen Handler zurück
	 */
	public AddHandler getAddHandler() {
		return addHandler;
	}

	/**
	 * @return Gibt den Hinzufügen Button zurück
	 */
	public Button getAddButton() {
		return addButton;
	}

	/**
	 * @return Gibt das Beschreibungsfeld zurück
	 */
	public Label getDescriptionLabel() {
		return descriptionLabel;
	}

	/**
	 * Setzt den Wert des Beschreibungfeldes neu
	 * 
	 * @param descriptionLabel Neuer Wert für das Label
	 */
	public void setDescriptionLabel(String descriptionLabel) {
		this.descriptionLabel.setText(descriptionLabel);
	}
	
	/**
	 * @return Gibt die Stadt-Box zurück
	 */
	public SuggestBox getCitySuggestBox() {
		return citySuggestBox;
	}

	/**
	 * @return Gibt die Straßen-Box zurück
	 */
	public SuggestBox getStreetSuggestBox() {
		return streetSuggestBox;
	}

	/**
	 * @return Gibt die Löschen Button zurück
	 */
	public Button getRemoveButton() {
		return removeButton;
	}
	
	/**
	 * @return Gibt den Stadt Handler zurück
	 */
	public CitySuggestionKeyUpHandler getCityHandler() {
		return cityHandler;
	}

	/**
	 * @return Gibt den Straßen Handler zurück
	 */
	public StreetSuggestionKeyUpHandler getStreetHandler() {
		return streetHandler;
	}
}