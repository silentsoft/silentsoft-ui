package org.silentsoft.ui.viewer;

import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractViewer implements Terminatable {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractViewer.class);
	
	private Parent viewer;
	
	private AbstractViewerController controller;
	
	public AbstractViewer(Object... parameters) {
		this(null, parameters);
	}
	
	public AbstractViewer(ResourceBundle resources) {
		this(resources, new Object[]{});
	}
	
	public AbstractViewer(ResourceBundle resources, Object... parameters) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getClass().getSimpleName().concat(".fxml")));
			
			if (resources != null) {
				fxmlLoader.setResources(resources);
			}
			
			viewer = fxmlLoader.load();
			controller = fxmlLoader.getController();
			
			// meaning of the scene property changed is that ready to work for controller after UI rendered.
			viewer.sceneProperty().addListener((observable, oldValue, newValue) -> {
				if (oldValue == null && newValue != null) {
					Platform.runLater(() -> {
						controller.initialize(viewer, parameters);
					});
				} else if (oldValue != null && newValue == null) {
					Platform.runLater(() -> {
						controller.terminate();
						controller = null;
						
						terminate();
						viewer = null;
					});
				}
			});
		} catch (Exception e) {
			LOGGER.error("I got catch an error during initialize the viewer !", e);
		}
	}
	
	/**
	 * <em>NOTE</em> Please override this method when it need to terminate object by manually.
	 */
	@Override
	public void terminate() {
		
	}
	
	public Parent getViewer() {
		return viewer;
	}
}
