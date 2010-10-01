package de.htwmaps.client.GUI;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 * Erstellt das Wegbeschreibungspanel, in dem die Wegbeschreibung ausgegeben wird
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 */
public class WayDescriptionPanel extends ScrollPanel {
	
	
	/**
	 * Standardkonstruktor fuer die Klasse
	 */
	public WayDescriptionPanel() {
		setSize("280px", "580px");
		setVisible(false);
	}
	
	/**
	 * Fügt eine Wegebschreibung hinzu
	 * 
	 * @param items Array mit dem Inhalt der Wegbeschreibung
	 */
	public void addItems(String[] items){
		StringBuilder sb = new StringBuilder();
		for(String item: items) {
			sb.append(item)
			  .append("<br>--------------------------------------<br>");
		}
		HTML htmlItem = new HTML(sb.toString());
		add(htmlItem);
	}
	
	public void setNewSize(int height) {
		if (getOffsetHeight() > 30) {
			setSize("280px", (getOffsetHeight() + height) + "px");
		}
	}
}
