package de.htwmaps.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwmaps.client.GUI.InfoAnchor;
import de.htwmaps.client.GUI.SummaryPanel;
import de.htwmaps.shared.PathDescription;

/**
 * Methoden die aufgerufen werden wenn die Textdarstellung der Route berechnet wurde oder Fehler auftraten.
 * @author Thomas Altmeyer, Tim Bartsch
 *
 */
public class PathDescriptionCallback implements AsyncCallback<PathDescription> {
	
	HtwMaps mainModule;
	
	public PathDescriptionCallback(HtwMaps module) {
		mainModule = module;
	}

	/**
	 * Bei einem Fehler, setzte das Fehler Label.
	 */
	@Override
	public void onFailure(Throwable caught) {
		HtwMaps.setTextAndStyle(caught.getMessage(), "statusLabelError");
	}

	/**
	 * Gibt bei Erfolgreicher Berechnung die Textdarstellung der Route aus.
	 */
	@Override
	public void onSuccess(PathDescription result) {
		SummaryPanel sp = mainModule.controlsPanel.getSummaryPanel();
		sp.setOnMotorWay(result.getTimeOnMotorWay(), result.getDistanceOnMotorWay());
		sp.setOnPrimary(result.getTimeOnPrimary(), result.getDistanceOnPrimary());
		sp.setOnResidential(result.getTimeOnResidential(), result.getDistanceOnResidential());
		sp.setTotal(result.getTimeTotal(), result.getDistanceTotal());
		mainModule.controlsPanel.getWayDescriptionPanel().addItems(result.getWayDescriptions());
		InfoAnchor ia = mainModule.infoAnchor;
		ia.setRouteToTextRuntime(result.getRouteToTextRuntime());
	}

}
