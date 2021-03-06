package de.htwmaps.client.GUI; 

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Erstellt Zusammenfassungspanel, das eine kurze Wegzusammenfassung enthält
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 */
public class SummaryPanel extends VerticalPanel {
	
	private Label onMotorWay = new Label();
	private Label onPrimary = new Label();
	private Label onResidential = new Label();
	private Label total = new Label();
	
	Grid summaryTable = new Grid(4,2);
	
	/**
	 * Intitialisiert das Zusammenfassungspanel mit Standardwerte
	 */
	public SummaryPanel() {
		summaryTable.setSize("250px", "15px");
		summaryTable.setWidget(0, 0, new HTML(StringConstant.GESAMT));
		summaryTable.setWidget(1, 0, new Label(StringConstant.AUTOBAHN));
		summaryTable.setWidget(2, 0, new Label(StringConstant.LANDSTRASSE));
		summaryTable.setWidget(3, 0, new Label(StringConstant.INNERORTS));

		summaryTable.setWidget(0, 1, total);
		summaryTable.setWidget(1, 1, onMotorWay);
		summaryTable.setWidget(2, 1, onPrimary);
		summaryTable.setWidget(3, 1, onResidential);
		
		for (int i = 0; i < 4; i++) {
			summaryTable.getCellFormatter().setHorizontalAlignment(i, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		}
		add(summaryTable);
		setVisible(false);
	}
	
	/**
	 * Setzt die Werte f�r die Autobahn
	 * 
	 * @param time Ben�tigte Zeit
	 * @param distance Angabe der Distanz in Kilometer
	 */
	public void setOnMotorWay(String time, String distance){
		onMotorWay.setText(distance + "   -   " + time);
	}
	
	/**
	 * Setzt die Werte f�r die Landstraße
	 * 
	 * @param time Ben�tigte Zeit
	 * @param distance Angabe der Distanz in Kilometer
	 */
	public void setOnPrimary(String time, String distance){
		onPrimary.setText(distance + "   -   " + time);
	}
	
	/**
	 * Setzt die Werte für Innerorts
	 * 
	 * @param time Benötigte Zeit
	 * @param distance Angabe der Distanz in Kilometer
	 */
	public void setOnResidential(String time, String distance){
		onResidential.setText(distance + "   -   " + time);
	}
	
	/**
	 * Setzt die Werte für die komplette Strecke
	 * 
	 * @param time Benötigte Zeit
	 * @param distance Angabe der Distanz in Kilometer
	 */
	public void setTotal(String time, String distance){
		total.setText(distance + "   -   " + time);
	}
	
	/**
	 * Löscht die Werte für alles felder
	 */
	public void setFieldsEmpty() {
		onMotorWay.setText("");
		onPrimary.setText("");
		onResidential.setText("");
		total.setText("");
	}
}
