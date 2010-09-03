package de.htwmaps.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwmaps.client.GUI.StringConstant;
import de.htwmaps.shared.OptPathData;

public class CalcRouteClickHandler implements ClickHandler {
	HtwMaps mainModule;
	static long startTime;

	public CalcRouteClickHandler(HtwMaps module) {
		mainModule = module;
	}

	@Override
	public void onClick(ClickEvent event) {
		startTime = System.currentTimeMillis();
		mainModule.resetFields();
		mainModule.loadImageOn();
		
		if(mainModule.findPathSvc == null){
			mainModule.findPathSvc = GWT.create(FindPathService.class);
		}
		
		AsyncCallback<OptPathData> callback = new FindPathCallback(mainModule);
		
		String[] cities = mainModule.controlsPanel.getLocation().getCities();
		String[] streets = mainModule.controlsPanel.getLocation().getStreets();
		int emptyCity = mainModule.findEmptyField(cities);
		int emptyStreet = mainModule.findEmptyField(streets);
		
		if(emptyCity < 0){
			if(emptyStreet < 0){
				boolean shortestPath = mainModule.controlsPanel.getOptionsPanel().getShortestRadioButton().getValue();
				boolean aStarBi = mainModule.controlsPanel.getOptionsPanel().getaStarBiRadioButton().getValue();

				if (shortestPath) {
					if (aStarBi) {
						mainModule.findPathSvc.findShortestPathAStarBi(cities, streets, callback);
					} else {
						mainModule.findPathSvc.findShortestPathAStar(cities, streets, callback);
					}
				} else {
					boolean checkSpeed = true;
					int motorwaySpeed = mainModule.leseIntZahl(mainModule.controlsPanel.getOptionsPanel().getMotorwaySpeedTextBox().getText().trim()); //TODO kuerzer schreiben
					if (motorwaySpeed <= 0) {
						mainModule.loadImageOff();
						HtwMaps.setTextAndStyle(StringConstant.F_AUTOBAHN, "statusLabelError");
						checkSpeed = false;						}
					int primarySpeed = mainModule.leseIntZahl(mainModule.controlsPanel.getOptionsPanel().getPrimarySpeedTextBox().getText().trim());
					if (primarySpeed <= 0 && checkSpeed) {
						mainModule.loadImageOff();
						HtwMaps.setTextAndStyle(StringConstant.F_LANDSTRASSE, "statusLabelError");
						checkSpeed = false;
					}
					int residentialSpeed = mainModule.leseIntZahl(mainModule.controlsPanel.getOptionsPanel().getResidentialSpeedTextBox().getText().trim());
					if (residentialSpeed <= 0 && checkSpeed) {
						mainModule.loadImageOff();
						HtwMaps.setTextAndStyle(StringConstant.F_INNERORTS, "statusLabelError");
						checkSpeed = false;
					}
					if (checkSpeed) {
						if (aStarBi) {
							mainModule.findPathSvc.findFastestPathAStarBi(cities, streets, motorwaySpeed, primarySpeed, residentialSpeed, callback);
						} else {
							mainModule.findPathSvc.findFastestPathAStar(cities, streets, motorwaySpeed, primarySpeed, residentialSpeed, callback);
						}
					} else {
						mainModule.controlsPanel.setCalcRouteButton(true);
					}
				}
			}else{
				mainModule.loadImageOff();
				HtwMaps.setTextAndStyle("Leeres Straßenfeld: " + (emptyStreet + 1), "statusLabelError");
				mainModule.controlsPanel.setCalcRouteButton(true);
			}
		}else{
			mainModule.loadImageOff();
			HtwMaps.setTextAndStyle("Leeres Ortsfeld: " + (emptyCity + 1), "statusLabelError");
			mainModule.controlsPanel.setCalcRouteButton(true);
		}
	}

	public static long getStartTime() {
		return startTime;
	}
}
