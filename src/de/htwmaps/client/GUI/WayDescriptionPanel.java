package de.htwmaps.client.GUI;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Erstellt das Wegbeschreibungspanel, in dem die Wegbeschreibung ausgegeben wird
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 */
public class WayDescriptionPanel extends VerticalPanel {
	
	
	/**
	 * Standardkonstruktor für die Klasse
	 */
	public WayDescriptionPanel() {
		setVisible(false);
	}
	
	/**
	 * Fügt eine Wegebschreibung hinzu
	 * 
	 * @param items Array mit dem Inhalt der Wegbeschreibung
	 */
	public void addItems(String[] items){
		for(String item: items)
			addItem(item);
	}
	
	/**
	 * Fügt die einzelnen Teile der Wegbeschreibung ins Panel ein
	 * 
	 * @param item Text der eingefügt wird
	 */
	public void addItem(String item){
		add(new HTML(item));
	}
}
