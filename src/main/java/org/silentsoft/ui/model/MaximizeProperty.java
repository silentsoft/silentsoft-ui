package org.silentsoft.ui.model;

import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Stage의 최대화를 설정하거나 해제합니다. 이때, 작업 표시줄은 최대화 영역에서 제외합니다.
 * @author Silentsoft
 *
 */
public class MaximizeProperty {
	
	private boolean isMaximized;
	
	private double prevX;
	private double prevY;
	private double prevWidth;
	private double prevHeight;
	
	public MaximizeProperty(Stage stage) {
		isMaximized = stage.isMaximized();
		
		prevX = stage.getX();
		prevY = stage.getY();
		prevWidth = stage.getWidth();
		prevHeight = stage.getHeight();
	}
	
	public void setMaximized(Stage stage, boolean value) {
		isMaximized = value;
		
		if (isMaximized) {
			ObservableList<Screen> screens = Screen.getScreensForRectangle(new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight()));
			if (screens != null && screens.size() >= 1) {
				prevX = stage.getX();
				prevY = stage.getY();
				prevWidth = stage.getWidth();
				prevHeight = stage.getHeight();
				
				Rectangle2D bounds = screens.get(0).getVisualBounds();
				stage.setX(bounds.getMinX());
				stage.setY(bounds.getMinY());
				stage.setWidth(bounds.getWidth());
				stage.setHeight(bounds.getHeight());
			}
		} else {
			stage.setX(prevX);
			stage.setY(prevY);
			stage.setWidth(prevWidth);
			stage.setHeight(prevHeight);
		}
	}
	
	public boolean getMaximized() {
		return isMaximized;
	}
}
