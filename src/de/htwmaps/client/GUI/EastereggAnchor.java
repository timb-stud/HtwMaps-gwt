package de.htwmaps.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EastereggAnchor extends Anchor{
	
	final DialogBox dialogBox = new DialogBox();
	final VerticalPanel dialogVPanel = new VerticalPanel();
	final Button closeButton = new Button(StringConstant.SCHLIESSEN);
	
	public EastereggAnchor(String text) {
		super(text);
		init();
	}

	private void init() {
		dialogBox.setText("Easteregg");
		dialogBox.setAnimationEnabled(true);
		
		Frame frame = new Frame();
		frame.setUrl("./easteregg/index.html");
		dialogVPanel.add(frame);
		dialogVPanel.add(closeButton);
//		dialogVPanel.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
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

}
