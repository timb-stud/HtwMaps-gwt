package de.htwmaps.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.htwmaps.client.GUI.ControlsPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HtwMaps implements EntryPoint {
	Label errorLabel = new Label("Error");
	ControlsPanel controlsPanel = new ControlsPanel();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		controlsPanel.addStyleName("controlsPanel");
		RootPanel.get("controlsPanel").add(controlsPanel);
		RootPanel.get("errorLabelContainer").add(errorLabel);
	}
}
