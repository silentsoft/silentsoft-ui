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
 * UTF-8 based control for supports internationalization message.
 * This class will not throw {@link LoadException} even if the proper message key isn't exists.
 * <p>
 * <em>
 * Special thanks to DemkaAge and KYJ.
 * </em>
 * </p>
 * 
 * @see Control
 * @see <a href="https://gist.github.com/DemkaAge/8999236">https://gist.github.com/DemkaAge/8999236</a>
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
