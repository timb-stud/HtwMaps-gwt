package de.htwmaps.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwmaps.client.GUI.OptionsPanel;
import de.htwmaps.client.GUI.StringConstant;
import de.htwmaps.client.GUI.exceptions.MotorWaySpeedException;
import de.htwmaps.client.GUI.exceptions.PrimarySpeedException;
import de.htwmaps.client.GUI.exceptions.ResidentialSpeedException;
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
				OptionsPanel optionsPanel = mainModule.controlsPanel.getOptionsPanel();
				
				int motorwaySpeed = 0;
				int primarySpeed = 0;
				int residentialSpeed = 0;

				try{
					motorwaySpeed = optionsPanel.getMotorWaySpeed();
					primarySpeed = optionsPanel.getPrimarySpeed();
					residentialSpeed = optionsPanel.getResidentialSpeed();
					
					if (shortestPath) {
						if (aStarBi) {
							mainModule.findPathSvc.findShortestPathAStarBi(cities, streets, motorwaySpeed, primarySpeed, residentialSpeed, callback);
						} else {
							mainModule.findPathSvc.findShortestPathAStar(cities, streets, motorwaySpeed, primarySpeed, residentialSpeed, callback);
						}
					} else {
						if (aStarBi)
							mainModule.findPathSvc.findFastestPathAStarBi(cities, streets, motorwaySpeed, primarySpeed, residentialSpeed, callback);
						else 
							mainModule.findPathSvc.findFastestPathAStar(cities, streets, motorwaySpeed, primarySpeed, residentialSpeed, callback);
					}
				}catch(MotorWaySpeedException e){
					mainModule.loadImageOff();
					HtwMaps.setTextAndStyle(StringConstant.F_AUTOBAHN, "statusLabelError");
					mainModule.controlsPanel.setCalcRouteButton(true);
				} catch (PrimarySpeedException e) {
					mainModule.loadImageOff();
					HtwMaps.setTextAndStyle(StringConstant.F_LANDSTRASSE, "statusLabelError");
					mainModule.controlsPanel.setCalcRouteButton(true);
				} catch (ResidentialSpeedException e) {
					mainModule.loadImageOff();
					HtwMaps.setTextAndStyle(StringConstant.F_INNERORTS, "statusLabelError");
					mainModule.controlsPanel.setCalcRouteButton(true);
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
