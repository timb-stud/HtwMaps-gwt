package de.htwmaps.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.htwmaps.shared.OptPathData;

public class InfoAnchor extends Anchor {

	final DialogBox dialogBox = new DialogBox();
	final VerticalPanel dialogVPanel = new VerticalPanel();
	final Button closeButton = new Button(StringConstant.SCHLIESSEN);
	
	private Grid infoTable = new Grid(12,2);

	public InfoAnchor(String text) {
		super(text);
		init();
	}
	
	private void init(){
		dialogBox.setText(StringConstant.BERECHNUNGSINFOS);
		dialogBox.setAnimationEnabled(true);
		
		setInfoText();
		setHorizontalAlignment();
		dialogVPanel.add(infoTable);
		dialogVPanel.add(closeButton);
		dialogVPanel.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
		dialogBox.setWidget(dialogVPanel);
		
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
		
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.show();
				dialogBox.center();
				closeButton.setFocus(true);
			}
		});
	}
	
	private void setInfoText() {
		infoTable.setWidget(0, 0, new Label(StringConstant.NODES));
		infoTable.setWidget(1, 0, new Label(StringConstant.EDGES));
		infoTable.setWidget(2, 0, new Label(StringConstant.SELECT_NODES));
		infoTable.setWidget(3, 0, new Label(StringConstant.SELECT_EDGES));
		infoTable.setWidget(4, 0, new Label(StringConstant.BUILD_NODES));
		infoTable.setWidget(5, 0, new Label(StringConstant.BUILD_EDGES));
		infoTable.setWidget(6, 0, new Label(StringConstant.ALGORITHM));
		infoTable.setWidget(7, 0, new Label(StringConstant.OPT_TO_ALL));
		infoTable.setWidget(8, 0, new Label(StringConstant.OPT_NODES_RESULT));
		infoTable.setWidget(9, 0, new Label(StringConstant.ALL_NODES_RESULT));
		infoTable.setWidget(10, 0, new Label(StringConstant.ROUTE_TO_TEXT));
		infoTable.setWidget(11, 0, new Label(StringConstant.ZEIT_INSGESAMT));
	}
	
	private void setHorizontalAlignment() {
		for (int i = 0; i < 12; i++) {
			infoTable.getCellFormatter().setHorizontalAlignment(i, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		}
	}
	
	public void setInfoResultText(OptPathData result, long time) {
		infoTable.setWidget(0, 1, new Label(result.getNodesCount() + ""));
		infoTable.setWidget(1, 1, new Label(result.getEdgesCount() + ""));
		infoTable.setWidget(2, 1, new Label(result.getReceiveNodesTime() + " ms"));
		infoTable.setWidget(3, 1, new Label(result.getReceiveEdgesTime() + " ms"));
		infoTable.setWidget(4, 1, new Label(result.getBuildNodesTime() + " ms"));
		infoTable.setWidget(5, 1, new Label(result.getBuildEdgesTime() + " ms"));
		infoTable.setWidget(6, 1, new Label(result.getAlorithmTime() + " ms"));
//		infoTable.setWidget(7, 1, new Label(result.getOptToAllTime() + " ms"));
		infoTable.setWidget(8, 1, new Label(result.getOptNodesResultCount() + ""));
//		infoTable.setWidget(9, 1, new Label(result.getAllNodesResultCount() + ""));
		infoTable.setWidget(10, 1, new Label(result.getRouteToTextTime() + " ms"));
		infoTable.setWidget(11, 1, new Label(time + " sec"));
	}
}
