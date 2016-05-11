package org.silentsoft.ui.viewer;


public abstract class AbstractViewerController implements Initializable, Terminatable {
	
	/**
	 * <em>NOTE</em> Please override this method when it need to terminate object by manually.
	 */
	@Override
	public void terminate() {
		
	}
	
}
