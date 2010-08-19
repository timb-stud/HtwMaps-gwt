package de.htwmaps.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;

public class StreetSuggestionKeyUpHandler implements KeyUpHandler {

	CityStreetSuggestServiceAsync cityStreetSuggestSvc;
	MultiWordSuggestOracle oracle;
	SuggestBox citySuggestBox;

	public StreetSuggestionKeyUpHandler(CityStreetSuggestServiceAsync cityStreetSuggestSvc, MultiWordSuggestOracle oracle, SuggestBox citySuggestBox) {
		this.cityStreetSuggestSvc = cityStreetSuggestSvc;
		this.oracle = oracle;
		this.citySuggestBox = citySuggestBox;
	}
	
	@Override
	public void onKeyUp(KeyUpEvent event) {
		TextBox tb = (TextBox)event.getSource();
		if(tb.getText().length() > 1){
			if(this.cityStreetSuggestSvc == null)
				this.cityStreetSuggestSvc = GWT.create(CityStreetSuggestService.class);
			
			AsyncCallback<String[]> callback = new AsyncCallback<String[]>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(String[] result) {
					for(String s: result)
						oracle.add(s);
				}
			};
			
			this.cityStreetSuggestSvc.getStreetSuggestions(citySuggestBox.getText(), tb.getText(), callback);
		}

	}

}
