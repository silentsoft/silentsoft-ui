package org.silentsoft.ui.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import javafx.fxml.LoadException;

/**
 * Custom UTF-8 based control that support internationalization message.
 * If the message were not found on language file, it will be appear original key without {@link LoadException}.
 * </p>
 * <em>The source code for this class is customized based on https://gist.github.com/DemkaAge/8999236</em>
 * </p>
 * <em>Special thanks to DemkaAge and KYJ.</em>
 * 
 * @see Control
 */
public class UTF8Control extends Control {

	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
		// The below is a copy of the default implementation.
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        ResourceBundle bundle = null;
        InputStream stream = null;
        
        if (reload) {
            URL url = loader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    connection.setUseCaches(false);
                    stream = connection.getInputStream();
                }
            }
        } else {
            stream = loader.getResourceAsStream(resourceName);
        }
        
        if (stream != null) {
            try {
                // Only this line is changed to make it to read properties files as UTF-8.
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8")) {
                	@Override
                	public boolean containsKey(String key) {
                		// WARNING : DO NOT CHANGE BELOW RETURN STATEMENT.
                		return true;
                	}
                	
                	@Override
                	public Object handleGetObject(String key) {
                		if (key == null) {
                			return "";
                		}
                		
                		Object result = null;
                		
                		try {
                			result = super.handleGetObject(key);
                		} catch (Exception e) {
                			;
                		}
                		
                		return (result == null) ? key : result;
                	}
                };
            } finally {
                stream.close();
            }
        }
        
        return bundle;
	}
}
