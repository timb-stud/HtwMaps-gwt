package de.htwmaps.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Erstellt die About Box, die Informationen über das Projekt beinhaltet
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 */
public class AboutAnchor extends Anchor {
	
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button(StringConstant.SCHLIESSEN);

	/**
	 * Standardkonstruktor des Klasse der seine Oberklasse aufruft und die weiter Initialisierung an die init() Methode weitergibt 
	 * 
	 * @param text Title des Anchor
	 */
	public AboutAnchor(String text) {
		super(text);
		init();
	}
	
	/**
	 * Fügt der Box eine HTML Seite hinzu und setzt Styles
	 */
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
