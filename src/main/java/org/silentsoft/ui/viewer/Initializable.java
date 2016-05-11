package org.silentsoft.ui.viewer;

import javafx.scene.Parent;

/**
 * Controller initialization interface.
 * @author silentsoft
 *
 */
public interface Initializable {

	/**
	 * Called when to initialize a controller after its scene property has been created.
	 * 
	 * @param viewer
	 * @param parameters
	 */
	public void initialize(Parent viewer, Object... parameters);
	
}
