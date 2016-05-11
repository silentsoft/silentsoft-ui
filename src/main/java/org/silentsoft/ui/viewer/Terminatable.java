package org.silentsoft.ui.viewer;

/**
 * Viewer and controller termination interface.
 * @author silentsoft
 *
 */
public interface Terminatable {
	
	/**
	 * Called when to terminate a viewer and controller after its scene property has been deleted.
	 */
	public void terminate();
	
}
