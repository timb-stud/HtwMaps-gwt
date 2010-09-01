package de.htwmaps.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwmaps.client.GUI.StringConstant;
import de.htwmaps.shared.OptPathData;

public class CalcRouteClickHandler implements ClickHandler {
	HtwMaps mainModule;
	
	public CalcRouteClickHandler(HtwMaps module) {
		mainModule = module;
	}

	@Override
	public void onClick(ClickEvent event) {
		mainModule.resetFields();
		mainModule.loadImageOn();
		
		if(mainModule.findPathSvc == null){
			mainModule.findPathSvc = GWT.create(FindPathService.class);
		}
		
		AsyncCallback<OptPathData> callback = new FindPathCallback(mainModule);
		
		String startCity = mainModule.controlsPanel.getLocation().getLocations().get(0).getCitySuggestBox().getText();
		String startStreet = mainModule.controlsPanel.getLocation().getLocations().get(0).getStreetSuggestBox().getText();;
		String destCity = mainModule.controlsPanel.getLocation().getLocations().get(1).getCitySuggestBox().getText();;
		String destStreet = mainModule.controlsPanel.getLocation().getLocations().get(1).getStreetSuggestBox().getText();;
		
		if (mainModule.checkInputLocation(startCity, startStreet, destCity, destStreet)) {
			mainModule.controlsPanel.setCalcRouteButton(true);
		} else {
			if (startStreet.indexOf(",") != -1) {
				startStreet = startStreet.substring(0, startStreet.indexOf(","));
			}
			if (destStreet.indexOf(",") != -1) {
				destStreet = destStreet.substring(0, destStreet.indexOf(","));
			}

			boolean shortestPath = mainModule.controlsPanel.getOptionsPanel().getShortestRadioButton().getValue();
			boolean aStarBi = mainModule.controlsPanel.getOptionsPanel().getaStarBiRadioButton().getValue();

			if (shortestPath) {
				if (aStarBi) {
					mainModule.findPathSvc.findShortestPathAStarBi(startCity, startStreet, destCity, destStreet, callback);
				} else {
					mainModule.findPathSvc.findShortestPathAStar(startCity, startStreet, destCity, destStreet,	callback);
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
						mainModule.findPathSvc.findFastestPathAStarBi(startCity, startStreet, destCity, destStreet, motorwaySpeed, primarySpeed, residentialSpeed, callback);
					} else {
						mainModule.findPathSvc.findFastestPathAStar(startCity, startStreet, destCity, destStreet, motorwaySpeed, primarySpeed, residentialSpeed, callback);
					}
				} else {
					mainModule.controlsPanel.setCalcRouteButton(true);
				}
			}
		}
	}

}
