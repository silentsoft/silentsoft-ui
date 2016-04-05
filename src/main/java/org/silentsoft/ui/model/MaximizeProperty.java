package org.silentsoft.ui.model;

import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Set the maximize property to Stage without taskbar area.
 * @author silentsoft
 *
 */
public class MaximizeProperty {
	
	private boolean isMaximized;
	
	private Delta delta;
	
	public MaximizeProperty(Stage stage) {
		isMaximized = stage.isMaximized();
	
		delta = new Delta();
		delta.setX(stage.getX());
		delta.setY(stage.getY());
		delta.setWidth(stage.getWidth());
		delta.setHeight(stage.getHeight());
	}
	
	public void setMaximized(Stage stage, boolean value) {
		isMaximized = value;
		
		if (isMaximized) {
			ObservableList<Screen> screens = Screen.getScreensForRectangle(new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight()));
			if (screens != null && screens.size() >= 1) {
				delta.setX(stage.getX());
				delta.setY(stage.getY());
				delta.setWidth(stage.getWidth());
				delta.setHeight(stage.getHeight());
				
				Rectangle2D bounds = screens.get(0).getVisualBounds();
				stage.setX(bounds.getMinX());
				stage.setY(bounds.getMinY());
				stage.setWidth(bounds.getWidth());
				stage.setHeight(bounds.getHeight());
			}
		} else {
			stage.setX(delta.getX());
			stage.setY(delta.getY());
			stage.setWidth(delta.getWidth());
			stage.setHeight(delta.getHeight());
		}
	}
	
	public boolean getMaximized() {
		return isMaximized;
	}
}
