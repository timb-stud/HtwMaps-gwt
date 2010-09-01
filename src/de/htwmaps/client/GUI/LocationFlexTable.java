package de.htwmaps.client.GUI;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LocationFlexTable extends VerticalPanel {
	
	static FlexTable flexTable = new FlexTable();
	static ArrayList<LocationsObjects> locations = new ArrayList<LocationsObjects>();

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
	
	private static void refreshNumbersAdd(int row) {
		for (int i = row; i < flexTable.getRowCount(); i++) {
			locations.get(i - 1).setDescriptionLabel(String.valueOf(i));
			locations.get(i - 1).getAddHandler().setRow(i);
			locations.get(i - 1).getAddHandler().setRow(i);
		}
	}
	
	protected static void removeRow(int row) {
		flexTable.removeRow(row);
		locations.remove(row - 1);
		refreshNumberRemove();
		if (flexTable.getRowCount() == 3) {
			setVisibleRemove(false);
		}

	}
	
	private static void refreshNumberRemove() {
		for (int i = 1; i < flexTable.getRowCount(); i++) {
			locations.get(i - 1).setDescriptionLabel(String.valueOf(i));
			locations.get(i - 1).getAddHandler().setRow(i);
			locations.get(i - 1).getRemoveHandler().setRow(i);
		}
	}

	private static void setVisibleRemove(boolean visible) {
		for (int i = 1; i < flexTable.getRowCount(); i++) {
			locations.get(i - 1).setVisibleRemoveButton(visible);
		}
	}

	public ArrayList<LocationsObjects> getLocations() {
		return locations;
	}
}
 
class AddHandler implements ClickHandler {

	int row;

	public AddHandler(int row) {
		this.row = row;	
	}
	
	public void onClick(ClickEvent event) {
		LocationFlexTable.addRow(row);
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}

class RemoveHandler implements ClickHandler {

	int row;

	public RemoveHandler(int row) {
		this.row = row;	
	}
	
	public void onClick(ClickEvent event) {
		LocationFlexTable.removeRow(row);
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}

