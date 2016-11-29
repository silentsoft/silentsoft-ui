package org.silentsoft.ui.util;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;

public class StageUtil {

	private static final ObjectProperty<Stage> currentStage = new SimpleObjectProperty<Stage>();
	
	public static final ObjectProperty<Stage> currentStageProperty() {
		return currentStage;
	}
	
	public static final Stage getCurrentStage() {
		return currentStageProperty().get();
	}
	
	public static final void setCurrentStage(Stage stage) {
		currentStageProperty().set(stage);
	}
	
	public static void registerStage(Stage stage) {
		stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				setCurrentStage(stage);
			} else {
				setCurrentStage(null);
			}
		});
	}
	
}
