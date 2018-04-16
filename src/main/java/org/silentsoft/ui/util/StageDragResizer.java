package org.silentsoft.ui.util;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * @author silentsoft
 */
public class StageDragResizer extends DragResizer {

	private final Stage stage;
	
	private int shadow = 0;
	
	public static void makeResizable(Stage stage, Region region) {
		new StageDragResizer(stage, region);
	}
	
	public static void makeResizable(Stage stage, Region region, int margin) {
		new StageDragResizer(stage, region, margin);
	}
	
	public static void makeResizable(Stage stage, Region region, int margin, Runnable dragDoneAction) {
		new StageDragResizer(stage, region, margin, dragDoneAction);
	}
	
	public static void makeResizable(Stage stage, Region region, int margin, int shadow, Runnable dragDoneAction) {
		new StageDragResizer(stage, region, margin, shadow, dragDoneAction);
	}
	
	private StageDragResizer(Stage stage, Region region) {
		super(region);
		
		this.stage = stage;
	}
	
	private StageDragResizer(Stage stage, Region region, int margin) {
		super(region, margin);
		
		this.stage = stage;
	}
	
	private StageDragResizer(Stage stage, Region region, int margin, Runnable dragDoneAction) {
		this(stage, region, margin, 0, dragDoneAction);
	}
	
	private StageDragResizer(Stage stage, Region region, int margin, int shadow, Runnable dragDoneAction) {
		super(region, margin, dragDoneAction);
		
		this.stage = stage;
		this.shadow = shadow;
	}
	
	@Override
	protected void drag(MouseEvent mouseEvent, DragMode dragMode, double position, double size) {
		super.drag(mouseEvent, dragMode, position, size);
		
		switch (dragMode) {
    	case WIDTH:
    		stage.setX(position);
    		stage.setWidth(size + shadow);
    		break;
    	case HEIGHT:
    		stage.setY(position);
    		stage.setHeight(size + shadow);
    		break;
    	}
	}
}
