package org.silentsoft.ui.util;

import org.silentsoft.ui.model.Delta;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public abstract class DragResizer {
	
	public enum DragMode {
		WIDTH,
		HEIGHT
	}
    
    /**
     * The margin around the control that a user can click in to start resizing
     * the region.
     */
    private static final int RESIZE_MARGIN = 5;

    private final Region region;

    private Delta delta;
    
    private boolean dragging;
    
    private boolean dragForWidth;
    
    private boolean dragForHeight;
    
    protected DragResizer(Region region) {
        this.region = region;
        delta = new Delta();
        
        region.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePressed(event);
            }
        });
        region.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseDragged(event);
            }
        });
        region.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseOver(event);
            }
        });
        region.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseReleased(event);
            }
        });
    }
    
    protected void drag(MouseEvent mouseEvent, DragMode dragMode, double newSize) {
    	switch (dragMode) {
    	case WIDTH:
    		region.setPrefWidth(newSize);
    		break;
    	case HEIGHT:
    		region.setPrefHeight(newSize);
    		break;
    	}
    }

    protected void mouseReleased(MouseEvent event) {
        dragging = false;
        region.setCursor(Cursor.DEFAULT);
    }

    protected void mouseOver(MouseEvent event) {
    	if (isInDraggableZoneForWidth(event) && isInDraggableZoneForHeight(event)) {
    		region.setCursor(Cursor.SE_RESIZE);
    	} else if (isInDraggableZoneForWidth(event)) {
    		region.setCursor(Cursor.E_RESIZE);
    	} else if (isInDraggableZoneForHeight(event)) {
    		region.setCursor(Cursor.S_RESIZE);
    	}
        else {
            region.setCursor(Cursor.DEFAULT);
        }
    }
    
    protected boolean isInDraggableZoneForWidth(MouseEvent event) {
    	return (event.getX() > (region.getWidth() - RESIZE_MARGIN));
    }
    
    protected boolean isInDraggableZoneForHeight(MouseEvent event) {
    	return (event.getY() > (region.getHeight() - RESIZE_MARGIN));
    }

    protected boolean isInDraggableZone(MouseEvent event) {
         return isInDraggableZoneForWidth(event) || isInDraggableZoneForHeight(event);
    }

    protected void mouseDragged(MouseEvent event) {
        if(!dragging) {
            return;
        }
        
        if (dragForWidth) {
	        double newWidth = region.getPrefWidth() + (event.getX() - delta.getX());
	        if (newWidth > region.getMinWidth()) {
	        	delta.setX(event.getX());
	        	drag(event, DragMode.WIDTH, newWidth);
	        } else {
	        	drag(event, DragMode.WIDTH, region.getMinWidth());
	        }
        }
        
        if (dragForHeight) {
	        double newHeight = region.getPrefHeight() + (event.getY() - delta.getY());
	        if (newHeight > region.getMinHeight()) {
	        	delta.setY(event.getY());
	        	drag(event, DragMode.HEIGHT, newHeight);
	        } else {
	        	drag(event, DragMode.HEIGHT, region.getMinHeight());
	        }
        }
    }

    protected void mousePressed(MouseEvent event) {
        // ignore clicks outside of the draggable margin
        if(!isInDraggableZone(event)) {
            return;
        } else {
        	dragging = true;
        	dragForWidth = false;
        	dragForHeight = false;
        }
        
        if (isInDraggableZoneForWidth(event)) {
        	delta.setX(event.getX());
        	dragForWidth = true;
        }
        
        if (isInDraggableZoneForHeight(event)) {
        	delta.setY(event.getY());
        	dragForHeight = true;
        }
    }
}