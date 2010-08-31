package de.htwmaps.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwmaps.client.GUI.InfoAnchor;
import de.htwmaps.client.GUI.StringConstant;
import de.htwmaps.shared.AllPathData;
import de.htwmaps.shared.OptPathData;
import de.htwmaps.shared.PathDescription;

public class FindPathCallback implements AsyncCallback<OptPathData> {

	HtwMaps mainModule;
	
	public FindPathCallback(HtwMaps module) {
		mainModule = module;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		mainModule.loadImageOff();
		mainModule.controlsPanel.setCalcRouteButton(true);
		HtwMaps.setTextAndStyle(caught.getMessage(), "statusLabelError");
	}

	@Override
	public void onSuccess(OptPathData result) {
		mainModule.loadImageOff();
		HtwMaps.setTextAndStyle(StringConstant.ROUTE_GEFUNDEN, "statusLabelNormal");
		mainModule.controlsPanel.setCalcRouteButton(true);
		float[] nodeLats = result.getNodeLats();
		float[] nodeLons = result.getNodeLons();
		int i = 0;
		for(i=0;i<nodeLats.length;i++){
			mainModule.addPoint(nodeLats[i], nodeLons[i]);
		}
		mainModule.drawPolyLine();
		mainModule.addMarker(nodeLats[0], nodeLons[0], nodeLats[nodeLats.length - 1], nodeLons[nodeLats.length - 1]);
		
		InfoAnchor ia = mainModule.infoAnchor;
		ia.setSelectedNodesNumber(result.getSelectedNodesNumber());
		ia.setSelectedEdgesNumber(result.getSelectedEdgesNumber());
		ia.setSelectNodesRuntime(result.getSelectNodesRuntime());
		ia.setSelectEdgesRuntime(result.getSelectEdgesRuntime());
		ia.setBuildNodesRuntime(result.getBuildNodesRuntime());
		ia.setBuildEdgesRuntime(result.getBuildEdgesRuntime());
		ia.setAlgorithmRuntime(result.getAlgorithmRuntime());
		ia.setOptNodesNumber(result.getOptNodesNumber());
		
		AsyncCallback<AllPathData> allPathDataCallback = new AllPathDataCallback(mainModule);
		mainModule.findPathSvc.buildAllPathData(allPathDataCallback);
		
		AsyncCallback<PathDescription> pathDescriptionCallback = new PathDescriptionCallback(mainModule);
		mainModule.findPathSvc.buildPathDescription(pathDescriptionCallback);
	}

}
