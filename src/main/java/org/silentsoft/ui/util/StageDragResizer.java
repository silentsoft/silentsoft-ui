package org.silentsoft.ui.util;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class StageDragResizer extends DragResizer {

	private final Stage stage;
	
	private StageDragResizer(Stage stage, Region region) {
		super(region);
		
		this.stage = stage;
	}
	
	public static void makeResizable(Stage stage, Region region) {
		new StageDragResizer(stage, region);
	}
	
	@Override
	protected void drag(MouseEvent mouseEvent, DragMode dragMode, double newSize) {
		super.drag(mouseEvent, dragMode, newSize);
		
		switch (dragMode) {
		case WIDTH:
			stage.setWidth(newSize);
			break;
		case HEIGHT:
			stage.setHeight(newSize);
			break;
		}
	}
}
