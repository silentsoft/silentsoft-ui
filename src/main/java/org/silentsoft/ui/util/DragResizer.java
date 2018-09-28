package org.silentsoft.ui.util;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import org.silentsoft.ui.model.Delta;

/**
 * @author silentsoft
 */
public abstract class DragResizer {
	
	public enum DragMode {
		WIDTH,
		HEIGHT
	}

    private final Region region;
    
    private final int margin;
    
    private final Runnable dragDoneAction;

    private Delta delta;
    
    /**
     * below variables are very important for natural control.
     */
    private boolean isDragging;
    
    private boolean isDragForEast;
    
    private boolean isDragForWest;
    
    private boolean isDragForSouth;
    
    private boolean isDragForNorth;
    
    protected DragResizer(Region region) {
        this(region, 0);
    }
    
    protected DragResizer(Region region, int margin) {
    	this(region, margin, null);
    }
    
    protected DragResizer(Region region, int margin, Runnable dragDoneAction) {
    	this.region = region;
    	this.margin = margin;
    	this.dragDoneAction = dragDoneAction;
    	
        delta = new Delta();

		region.addEventFilter(MouseEvent.MOUSE_MOVED, (event) -> {
			mouseMoved(event);
		});
        region.addEventFilter(MouseEvent.MOUSE_PRESSED, (event) -> {
        	mousePressed(event);
        });
		region.addEventFilter(MouseEvent.MOUSE_DRAGGED, (event) -> {
        	mouseDragged(event);
        });
		region.addEventFilter(MouseEvent.MOUSE_RELEASED, (event) -> {
			mouseReleased(event);
		});
    }

    protected void mouseMoved(MouseEvent event) {
    	if (isInDraggableZoneForNorth(event) && isInDraggableZoneForWest(event)) {
    		region.setCursor(Cursor.NW_RESIZE);
    	} else if (isInDraggableZoneForNorth(event) && isInDraggableZoneForEast(event)) {
    		region.setCursor(Cursor.NE_RESIZE);
    	} else if (isInDraggableZoneForSouth(event) && isInDraggableZoneForEast(event)) {
    		region.setCursor(Cursor.SE_RESIZE);
    	} else if (isInDraggableZoneForSouth(event) && isInDraggableZoneForWest(event)) {
    		region.setCursor(Cursor.SW_RESIZE);
    	} else if (isInDraggableZoneForEast(event)) {
    		region.setCursor(Cursor.E_RESIZE);
    	} else if (isInDraggableZoneForWest(event)) {
    		region.setCursor(Cursor.W_RESIZE);
    	} else if (isInDraggableZoneForSouth(event)) {
    		region.setCursor(Cursor.S_RESIZE);
    	} else if (isInDraggableZoneForNorth(event)) {
    		region.setCursor(Cursor.N_RESIZE);
    	} else {
            region.setCursor(Cursor.DEFAULT);
        }
    }
    
    protected void mousePressed(MouseEvent event) {
    	if (isInDraggableZone(event) == false) {
    		return;
    	}
    	
    	isDragging = true;
    	
    	if ((isDragForEast = isInDraggableZoneForEast(event))) {
    		delta.setWidth(event.getX());
    	}
    	
    	if ((isDragForWest = isInDraggableZoneForWest(event))) {
    		delta.setX(event.getScreenX());
    	}
    	
    	if ((isDragForSouth = isInDraggableZoneForSouth(event))) {
    		delta.setHeight(event.getY());
    	}
    	
    	if ((isDragForNorth = isInDraggableZoneForNorth(event))) {
    		delta.setY(event.getScreenY());
    	}
    }

    protected void mouseDragged(MouseEvent event) {
    	if (isDragging == false) {
    		return;
    	}
    	
    	if (isDragForEast) {
    		double size = region.getPrefWidth() + (event.getX() - delta.getWidth());
    		if (size > region.getMinWidth()) {
    			delta.setWidth(event.getX());
    			drag(event, DragMode.WIDTH, region.getScene().getWindow().getX(), size);
    		} else {
    			drag(event, DragMode.WIDTH, region.getScene().getWindow().getX(), region.getMinWidth());
    		}
    	}
    	
    	if (isDragForWest) {
    		double size = region.getPrefWidth() + (delta.getX() - event.getScreenX());
    		if (size > region.getMinWidth()) {
    			delta.setX(event.getScreenX());
    			drag(event, DragMode.WIDTH, event.getScreenX(), size);
    		} else {
    			drag(event, DragMode.WIDTH, region.getScene().getWindow().getX(), region.getMinWidth());
    		}
    	}
    	
    	if (isDragForSouth) {
    		double size = region.getPrefHeight() + (event.getY() - delta.getHeight());
    		if (size > region.getMinHeight()) {
    			delta.setHeight(event.getY());
    			drag(event, DragMode.HEIGHT, region.getScene().getWindow().getY(), size);
    		} else {
    			drag(event, DragMode.HEIGHT, region.getScene().getWindow().getY(), region.getMinHeight());
    		}
    	}
    	
    	if (isDragForNorth) {
    		double size = region.getPrefHeight() + (delta.getY() - event.getScreenY());
    		if (size > region.getMinHeight()) {
    			delta.setY(event.getScreenY());
    			drag(event, DragMode.HEIGHT, event.getScreenY(), size);
    		} else {
    			drag(event, DragMode.HEIGHT, region.getScene().getWindow().getY(), region.getMinHeight());
    		}
    	}
    }
    
    protected void mouseReleased(MouseEvent event) {
    	isDragging = false;
    	region.setCursor(Cursor.DEFAULT);
    	
    	if (isDragForEast || isDragForWest || isDragForSouth || isDragForNorth) {
    		if (dragDoneAction != null) {
    			dragDoneAction.run();
    		}
    	}
    }
    
    protected boolean isInDraggableZoneForEast(MouseEvent event) {
    	return (event.getSceneX() + margin > region.getWidth());
    }
    
    protected boolean isInDraggableZoneForWest(MouseEvent event) {
    	return (event.getSceneX() - margin < 0);
    }
    
    protected boolean isInDraggableZoneForSouth(MouseEvent event) {
    	return (event.getSceneY() + margin > region.getHeight());
    }
    
    protected boolean isInDraggableZoneForNorth(MouseEvent event) {
    	return (event.getSceneY() < margin);
    }

    protected boolean isInDraggableZone(MouseEvent event) {
    	return isInDraggableZoneForEast(event) || isInDraggableZoneForWest(event) || isInDraggableZoneForSouth(event) || isInDraggableZoneForNorth(event);
    }
    
    protected void drag(MouseEvent mouseEvent, DragMode dragMode, double position, double size) {
    	switch (dragMode) {
    	case WIDTH:
    		region.setPrefWidth(size);
    		break;
    	case HEIGHT:
    		region.setPrefHeight(size);
    		break;
    	}
    }
}