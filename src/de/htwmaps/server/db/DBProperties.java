package de.htwmaps.server.db;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DBProperties {
	private static final String BUNDLE_NAME = "de.htwmaps.server.db.db"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private DBProperties() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
