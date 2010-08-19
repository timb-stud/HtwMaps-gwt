package de.htwmaps.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.TextBox;

public class CitySuggestionKeyUpHandler implements KeyUpHandler {
	
	CityStreetSuggestServiceAsync cityStreetSuggestSvc;
	MultiWordSuggestOracle oracle;

	public CitySuggestionKeyUpHandler(CityStreetSuggestServiceAsync cityStreetSuggestSvc, MultiWordSuggestOracle oracle) {
		this.cityStreetSuggestSvc = cityStreetSuggestSvc;
		this.oracle = oracle;
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
					caught.getMessage();
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(String[] result) {
					for(String s: result)
						oracle.add(s);
				}
			};
			
			this.cityStreetSuggestSvc.getCitySuggestions(tb.getText(), callback);
		}

	}

}
