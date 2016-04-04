package org.silentsoft.ui.viewer;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractViewer {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractViewer.class);
	
	private Parent viewer;
	
	private AbstractViewerController controller;
	
	public AbstractViewer(Object... parameters) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getClass().getSimpleName().concat(".fxml")));
			viewer = fxmlLoader.load();
			controller = fxmlLoader.getController();
			
			// meaning of the scene property changed is that ready to work for controller after UI rendered.
			viewer.sceneProperty().addListener((observable, oldValue, newValue) -> {
				if (oldValue == null && newValue != null) {
					Platform.runLater(() -> {
						controller.initialize(viewer, parameters);
					});
				}
			});
		} catch (Exception e) {
			LOGGER.error("I got catch an error during initialize the viewer !", e);
		}
	}
	
	public Parent getViewer() {
		return viewer;
	}
}
