package de.htwmaps.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwmaps.client.GUI.InfoAnchor;
import de.htwmaps.shared.AllPathData;

/**
 * Beinhaltet methoden die aufgerufen werden wenn die geglaettete Route berechnet wurde oder Fehler auftraten.
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 *
 */
public class AllPathDataCallback implements AsyncCallback<AllPathData> {

	HtwMaps mainModule;
	
	public AllPathDataCallback(HtwMaps module) {
		mainModule = module;
	}
	
	/**
	 * Bei einem Fehler, setze Fehlermelungs Label
	 */
	@Override
	public void onFailure(Throwable caught) {
		HtwMaps.setTextAndStyle(caught.getMessage(), "statusLabelError");
	}

	/**
	 * Zeichnet bei Erfolgreicher Berechnung die geglaettete Route und setze die Laufzeit Informationen.
	 */
	@Override
	public void onSuccess(AllPathData result) {
		mainModule.removePolyline();
		float[] nodeLats = result.getLats();
		float[] nodeLons = result.getLons();
		int i = 0;
		for(i=0;i<nodeLats.length;i++){
			mainModule.addPoint(nodeLats[i], nodeLons[i]);
		}
		mainModule.drawPolyLine();
		
		InfoAnchor ia = mainModule.infoAnchor;
		ia.setAllNodesNumber(result.getAllNodesNumber());
		ia.setOptToAllRuntime(result.getOptToAllRuntime());
	}

}
