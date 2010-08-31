package de.htwmaps.client.GUI;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WayDescriptionPanel extends VerticalPanel {
	
	
	public WayDescriptionPanel() {
		setVisible(false);
	}
	
	public void addItems(String[] items){
		for(String item: items)
			addItem(item);
	}
	
	public void addItem(String item){
		add(new Label(item));
	}
}
