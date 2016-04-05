package org.silentsoft.ui.util;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * @author silentsoft
 */
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
	protected void drag(MouseEvent mouseEvent, DragMode dragMode, double position, double size) {
		super.drag(mouseEvent, dragMode, position, size);
		
		switch (dragMode) {
    	case WIDTH:
    		stage.setX(position);
    		stage.setWidth(size);
    		break;
    	case HEIGHT:
    		stage.setY(position);
    		stage.setHeight(size);
    		break;
    	}
	}
}
