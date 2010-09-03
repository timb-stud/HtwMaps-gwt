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

public class InfoAnchor extends Anchor {

	final DialogBox dialogBox = new DialogBox();
	final VerticalPanel dialogVPanel = new VerticalPanel();
	final Button closeButton = new Button(StringConstant.SCHLIESSEN);
	
	private Grid infoTable;
	
	private Label selectedNodesNumber = new Label();
	private Label selectedEdgesNumber = new Label();
	private Label selectNodesRuntime = new Label();
	private Label selectEdgesRuntime = new Label();
	private Label buildNodesRuntime = new Label();
	private Label buildEdgesRuntime = new Label();
	private Label algorithmRuntime = new Label();
	private Label optNodesNumber = new Label();
	private Label allNodesNumber = new Label();
	private Label optToAllRuntime = new Label();
	private Label routeToTextRuntime = new Label();
	private Label completeRuntime = new Label();

	public InfoAnchor(String text) {
		super(text);
		init();
	}
	
	private void init(){
		dialogBox.setText(StringConstant.BERECHNUNGSINFOS);
		dialogBox.setAnimationEnabled(true);
		
		initInfoTable();
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
	
	private void initInfoTable() {
		infoTable = new Grid(12,2);
		
		infoTable.setWidget(0, 0, new Label(StringConstant.NODES));
		infoTable.setWidget(1, 0, new Label(StringConstant.EDGES));
		infoTable.setWidget(2, 0, new Label(StringConstant.SELECT_NODES));
		infoTable.setWidget(3, 0, new Label(StringConstant.SELECT_EDGES));
		infoTable.setWidget(4, 0, new Label(StringConstant.BUILD_NODES));
		infoTable.setWidget(5, 0, new Label(StringConstant.BUILD_EDGES));
		infoTable.setWidget(6, 0, new Label(StringConstant.ALGORITHM));
		infoTable.setWidget(7, 0, new Label(StringConstant.OPT_NODES));
		infoTable.setWidget(8, 0, new Label(StringConstant.ALL_NODES));
		infoTable.setWidget(9, 0, new Label(StringConstant.OPT_TO_ALL));
		infoTable.setWidget(10, 0, new Label(StringConstant.ROUTE_TO_TEXT));
		infoTable.setWidget(11, 0, new Label(StringConstant.TOTAL));
		
		infoTable.setWidget(0, 1, selectedNodesNumber);
		infoTable.setWidget(1, 1, selectedEdgesNumber);
		infoTable.setWidget(2, 1, selectNodesRuntime);
		infoTable.setWidget(3, 1, selectEdgesRuntime);
		infoTable.setWidget(4, 1, buildNodesRuntime);
		infoTable.setWidget(5, 1, buildEdgesRuntime);
		infoTable.setWidget(6, 1, algorithmRuntime);
		infoTable.setWidget(7, 1, optNodesNumber);
		infoTable.setWidget(8, 1, allNodesNumber);
		infoTable.setWidget(9, 1, optToAllRuntime);
		infoTable.setWidget(10, 1, routeToTextRuntime);
		infoTable.setWidget(11, 1, completeRuntime);
	}
	
	private void setHorizontalAlignment() {
		for (int i = 0; i < 12; i++) {
			infoTable.getCellFormatter().setHorizontalAlignment(i, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		}
	}

	public void setSelectedNodesNumber(int n) {
		this.selectedNodesNumber.setText(n + "");
	}

	public void setSelectedEdgesNumber(int n) {
		this.selectedEdgesNumber.setText(n + "");
	}

	public void setSelectNodesRuntime(long runtime) {
		this.selectNodesRuntime.setText(runtime + " ms");
	}

	public void setSelectEdgesRuntime(long runtime) {
		this.selectEdgesRuntime.setText(runtime + " ms");
	}

	public void setBuildNodesRuntime(long runtime) {
		this.buildNodesRuntime.setText(runtime + " ms");
	}

	public void setBuildEdgesRuntime(long runtime) {
		this.buildEdgesRuntime.setText(runtime + " ms");
	}

	public void setAlgorithmRuntime(long runtime) {
		this.algorithmRuntime.setText(runtime + " ms");
	}

	public void setOptNodesNumber(int n) {
		this.optNodesNumber.setText(n + "");
	}

	public void setAllNodesNumber(int n) {
		this.allNodesNumber.setText(n + "");
	}

	public void setOptToAllRuntime(long runtime) {
		this.optToAllRuntime.setText(runtime + " ms");
	}

	public void setRouteToTextRuntime(long runtime) {
		this.routeToTextRuntime.setText(runtime + " ms");
	}

	public void setCompleteRuntime(long runtime) {
		this.completeRuntime.setText(runtime + " sek");
	}
	
	
}
