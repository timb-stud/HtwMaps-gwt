package de.htwmaps.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwmaps.client.GUI.InfoAnchor;
import de.htwmaps.client.GUI.SummaryPanel;
import de.htwmaps.shared.PathDescription;

public class PathDescriptionCallback implements AsyncCallback<PathDescription> {
	
	HtwMaps mainModule;
	
	public PathDescriptionCallback(HtwMaps module) {
		mainModule = module;
	}

	@Override
	public void onFailure(Throwable caught) {
		HtwMaps.setTextAndStyle(caught.getMessage(), "statusLabelError");
	}

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
