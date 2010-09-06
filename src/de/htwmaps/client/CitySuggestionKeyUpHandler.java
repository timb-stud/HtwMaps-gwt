package de.htwmaps.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Handler für die Autovervollständigung bei der Stadteingabe, der das Ergebnis an die SuggestBox zurückgibt
 * 
 * @author Thomas Altmeyer, Tim Bartsch
 */
public class CitySuggestionKeyUpHandler implements KeyUpHandler {
	
	CityStreetSuggestServiceAsync cityStreetSuggestSvc;
	MultiWordSuggestOracle oracle;

	/**
	 * Standardkonstruktor der den RPC intialisiert sowie das MultiWordSuggestOracle
	 * 
	 * @param cityStreetSuggestSvc  RPC der für die Verbindung zum Server gebraucht wird
	 * @param oracle MultiWordSuggestOracle der den Result der Abfrage übernimmt
	 */
	public CitySuggestionKeyUpHandler(CityStreetSuggestServiceAsync cityStreetSuggestSvc, MultiWordSuggestOracle oracle) {
		this.cityStreetSuggestSvc = cityStreetSuggestSvc;
		this.oracle = oracle;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.KeyUpHandler#onKeyUp(com.google.gwt.event.dom.client.KeyUpEvent)
	 */
	@Override
	public void onKeyUp(KeyUpEvent event) {
		TextBox tb = (TextBox) event.getSource();

		if (event.getNativeKeyCode() == 8 || event.getNativeKeyCode() == 46) {
			oracle.clear();
		}
		
		if (tb.getText().length() > 0 && event.getNativeKeyCode() != 53) {
			if (this.cityStreetSuggestSvc == null)
				this.cityStreetSuggestSvc = GWT.create(CityStreetSuggestService.class);

			AsyncCallback<String[]> callback = new AsyncCallback<String[]>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage();
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(String[] result) {
					if (result != null) {
						for (String s : result) {
							oracle.add(s);
						}
					}
				}
			};

			this.cityStreetSuggestSvc.getCitySuggestions(tb.getText(), callback);
		}
	}

	/**
	 * Löscht den Inhalt des oracle
	 */
	public void clearContent() {
		this.oracle.clear();
	}
}
