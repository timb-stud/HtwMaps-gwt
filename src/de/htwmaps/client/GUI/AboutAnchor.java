package de.htwmaps.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AboutAnchor extends Anchor {
	
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button(StringConstant.SCHLIESSEN);

	public AboutAnchor(String text) {
		super(text);
		init();
	}
	
	private void init(){
		dialogBox.setText(StringConstant.UEBER);
		dialogBox.setAnimationEnabled(true);
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.add(new HTML(StringConstant.ABOUT_TEXT));
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialogVPanel.add(closeButton);
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
