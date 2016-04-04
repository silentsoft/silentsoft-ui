package org.silentsoft.ui.viewer;

import javafx.scene.Parent;

public abstract class AbstractViewerController {

	protected abstract void initialize(Parent viewer, Object... parameters);
	
}
