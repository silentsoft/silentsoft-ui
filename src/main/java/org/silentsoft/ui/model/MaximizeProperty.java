package org.silentsoft.ui.model;

import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
				
				double stageDistance = 0.0;
				Shape stageRectangle = new Rectangle(delta.getX(), delta.getY(), delta.getWidth(), delta.getHeight());
				for (Screen screen : screens) {
					Rectangle2D screenVisualBounds = screen.getVisualBounds();
					Shape shape = Rectangle.intersect(stageRectangle, new Rectangle(screenVisualBounds.getMinX(), screenVisualBounds.getMinY(), screenVisualBounds.getWidth(), screenVisualBounds.getHeight()));
					
					double shapeDistance = shape.getLayoutBounds().getWidth() + shape.getLayoutBounds().getHeight();
					if (stageDistance <= shapeDistance) {
						stage.setX(screenVisualBounds.getMinX());
						stage.setY(screenVisualBounds.getMinY());
						stage.setWidth(screenVisualBounds.getWidth());
						stage.setHeight(screenVisualBounds.getHeight());
						
						stageDistance = shapeDistance;
					}
				}
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
