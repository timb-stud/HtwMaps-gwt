package de.htwmaps.client.GUI; 

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SummaryPanel extends VerticalPanel {
	
	private Label onMotorWay = new Label();
	private Label onPrimary = new Label();
	private Label onResidential = new Label();
	private Label total = new Label();
	
	Grid summaryTable = new Grid(4,2);
	
	public SummaryPanel() {
		summaryTable.setSize("280px", "10px");
		summaryTable.setWidget(0, 0, new Label(StringConstant.GESAMT));
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
	
	public void setOnMotorWay(String time, String distance){
		onMotorWay.setText(distance + "  " + time);
	}
	
	public void setOnPrimary(String time, String distance){
		onPrimary.setText(distance + "  " + time);
	}
	
	public void setOnResidential(String time, String distance){
		onResidential.setText(distance + "  " + time);
	}
	
	public void setTotal(String time, String distance){
		total.setText(distance + "  " + time);
	}
	
	public void setFieldsEmpty() {
		onMotorWay.setText("");
		onPrimary.setText("");
		onResidential.setText("");
		total.setText("");
	}
}
