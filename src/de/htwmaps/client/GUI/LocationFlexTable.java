package de.htwmaps.client.GUI;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Erstellt die Flexible Tabelle für das Routing
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 */
public class LocationFlexTable extends VerticalPanel {
	
	static FlexTable flexTable = new FlexTable();
	static ArrayList<LocationsObjects> locations = new ArrayList<LocationsObjects>();

	/**
	 * Standardkonstruktor der die Tabelle initalisiert mit Standardwerten
	 */
	public LocationFlexTable() {			
		flexTable.setWidget(0, 1, new Label(StringConstant.STADT));
		flexTable.setWidget(0, 2, new Label(StringConstant.STRASSE));
		
		locations.add(new LocationsObjects(String.valueOf(flexTable.getRowCount())));
		flexTable.setWidget(1, 0, locations.get(0).getDescriptionLabel());
		flexTable.setWidget(1, 1, locations.get(0).getCitySuggestBox());
		flexTable.setWidget(1, 2, locations.get(0).getStreetSuggestBox());
		flexTable.setWidget(1, 3, locations.get(0).getAddButton());
		flexTable.setWidget(1, 4, locations.get(0).getRemoveButton());
		
		locations.add(new LocationsObjects(String.valueOf(flexTable.getRowCount())));
		flexTable.setWidget(2, 0, locations.get(1).getDescriptionLabel());
		flexTable.setWidget(2, 1, locations.get(1).getCitySuggestBox());
		flexTable.setWidget(2, 2, locations.get(1).getStreetSuggestBox());
		flexTable.setWidget(2, 3, locations.get(1).getAddButton());
		flexTable.setWidget(2, 4, locations.get(1).getRemoveButton());
		
		setVisibleRemove(false);
		
		add(flexTable);
	}

	//TODO wiso statisch?
	/**
	 * Fügt eine neue Spalte in die Tabelle ein
	 * 
	 * @param row Stelle an der eine neue Row hinzu kommen soll
	 */
	protected static void addRow(int row) {
		locations.add(row, new LocationsObjects(String.valueOf(row + 1)));
		if (row == flexTable.getRowCount() - 1) {
			flexTable.setWidget(row + 1, 0, locations.get(row).getDescriptionLabel());
			flexTable.setWidget(row + 1, 1, locations.get(row).getCitySuggestBox());
			flexTable.setWidget(row + 1, 2, locations.get(row).getStreetSuggestBox());
			flexTable.setWidget(row + 1, 3, locations.get(row).getAddButton());
			flexTable.setWidget(row + 1, 4, locations.get(row).getRemoveButton());
		} else {
			flexTable.insertRow(row + 1);
			flexTable.setWidget(row + 1, 0, locations.get(row).getDescriptionLabel());
			flexTable.setWidget(row + 1, 1, locations.get(row).getCitySuggestBox());
			flexTable.setWidget(row + 1, 2, locations.get(row).getStreetSuggestBox());
			flexTable.setWidget(row + 1, 3, locations.get(row).getAddButton());
			flexTable.setWidget(row + 1, 4, locations.get(row).getRemoveButton());
			refreshNumbersAdd(row + 1);
		}
		setVisibleRemove(true);
	}
	
	/** 
	 * @return Liefert alle Städte zurück
	 */
	public String[] getCities(){
		String[] cities = new String[locations.size()];
		for(int i=0; i< cities.length; i++){
			cities[i] = locations.get(i).getCitySuggestBox().getText().trim();
		}
		return cities;
	}
	
	/**
	 * @return Liefert alle Straßen zurück
	 */
	public String[] getStreets(){
		String[] streets = new String[locations.size()];
		for(int i=0; i< streets.length;i++){
			streets[i] = locations.get(i).getStreetSuggestBox().getText().trim();
		}
		return streets;
	}
	
	/**
	 * Löscht leere Element aus der ArrayList locations
	 */
	public void removeEmptyLocations(){
		for(int i=0; i<locations.size(); i++){
			String city = locations.get(i).getCitySuggestBox().getText().trim();
			String street = locations.get(i).getStreetSuggestBox().getText().trim();
			if(city.isEmpty() || street.isEmpty()){
				locations.remove(i);
			}
		}
	}
	
	/**
	 * Aktualisiert die Spaltennumer für die Tabelle beim Hinzufügen
	 * 
	 * @param row Aktualisiert ab der Zeile
	 */
	private static void refreshNumbersAdd(int row) {
		for (int i = row; i < flexTable.getRowCount(); i++) {
			locations.get(i - 1).setDescriptionLabel(String.valueOf(i));
			locations.get(i - 1).getAddHandler().setRow(i);
			locations.get(i - 1).getAddHandler().setRow(i);
		}
	}
	
	/**
	 * Löscht eine Zeile aus der Tabelle
	 * 
	 * @param row Löscht eine Zeile an der bestimmten Stelle
	 */
	protected static void removeRow(int row) {
		flexTable.removeRow(row);
		locations.remove(row - 1);
		refreshNumberRemove();
		if (flexTable.getRowCount() == 3) {
			setVisibleRemove(false);
		}

	}
	
	/**
	 * Aktualisiert die Spaltennumer für die Tabelle beim Löschen
	 */
	private static void refreshNumberRemove() {
		for (int i = 1; i < flexTable.getRowCount(); i++) {
			locations.get(i - 1).setDescriptionLabel(String.valueOf(i));
			locations.get(i - 1).getAddHandler().setRow(i);
			locations.get(i - 1).getRemoveHandler().setRow(i);
		}
	}

	/**
	 * Methode um den Remove Button sichtbar bzw. unsichtbar zu schalten
	 * 
	 * @param visible true -> RemoveButton ist sichtbar
	 * 				  false -> RemoveButton ist nicht sichtbar
	 */
	private static void setVisibleRemove(boolean visible) {
		for (int i = 1; i < flexTable.getRowCount(); i++) {
			locations.get(i - 1).setVisibleRemoveButton(visible);
		}
	}

	/**
	 * @return Gibt die ArrayList zurück
	 */
	public ArrayList<LocationsObjects> getLocations() {
		return locations;
	}
}
 
/**
 * Handler der beim drücken des AddButton eine neue Zeile hinzufügt
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 */
class AddHandler implements ClickHandler {

	int row;

	/**
	 * Standardkonstruktor der den Händler mit der Zeilennummer initalisiert
	 * 
	 * @param row Zeile des Button
	 */
	public AddHandler(int row) {
		this.row = row;	
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	public void onClick(ClickEvent event) {
		LocationFlexTable.addRow(row);
	}
	
	/**
	 * @return Gibt die Zeile zurück
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Setzt den Wert für die Zeile neu
	 * 
	 * @param row Neue Zeilennummer
	 */
	public void setRow(int row) {
		this.row = row;
	}
}

/**
 * Handler der beim drücken des RemoveButton eine Zeile löscht
 * 
 * @author altmeyer
 *
 */
/**
 * @author altmeyer
 *
 */
class RemoveHandler implements ClickHandler {

	int row;

	/**
	 * Standardkonstruktor der den Handler mit der Zeilennummer initalisiert
	 * 
	 * @param row Zeile des Button
	 */
	public RemoveHandler(int row) {
		this.row = row;	
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	public void onClick(ClickEvent event) {
		LocationFlexTable.removeRow(row);
	}
	
	/**
	 * @return Gibt die Zeile rurück
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Setzt den Wert für die Zeile neu
	 * 
	 * @param row Neue Zeilennummer
	 */
	public void setRow(int row) {
		this.row = row;
	}
}

