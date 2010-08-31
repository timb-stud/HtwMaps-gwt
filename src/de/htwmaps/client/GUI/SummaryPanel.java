package de.htwmaps.client.GUI;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SummaryPanel extends VerticalPanel {
	
	Grid summaryTable = new Grid(5,2);
	
	public SummaryPanel() {
		summaryTable.setSize("280px", "10px");
		summaryTable.setWidget(0, 0, new Label(StringConstant.FAHRZEIT));
		summaryTable.setWidget(1, 0, new Label(StringConstant.WEGLAENGE));
		summaryTable.setWidget(2, 0, new Label(StringConstant.AUTOBAHN));
		summaryTable.setWidget(3, 0, new Label(StringConstant.LANDSTRASSE));
		summaryTable.setWidget(4, 0, new Label(StringConstant.INNERORTS));

		for (int i = 0; i < 5; i++) {
			summaryTable.getCellFormatter().setHorizontalAlignment(i, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		}
		add(summaryTable);
		setVisible(false);
	}
	
	public void setSummaryResult() {
		summaryTable.setWidget(0, 1, new Label(""));
		summaryTable.setWidget(1, 1, new Label(""));
		summaryTable.setWidget(2, 1, new Label(""));
		summaryTable.setWidget(3, 1, new Label(""));
		summaryTable.setWidget(4, 1, new Label(""));
	}
}
