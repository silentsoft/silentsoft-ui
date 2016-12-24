package org.silentsoft.ui.util;

import javafx.scene.layout.Region;

/**
 * @author silentsoft
 */
public class RegionDragResizer extends DragResizer {

	public static void makeResizable(Region region) {
		new RegionDragResizer(region);
	}
	
	public static void makeResizable(Region region, int margin) {
		new RegionDragResizer(region, margin);
	}
	
	public static void makeResizable(Region region, int margin, Runnable dragDoneAction) {
		new RegionDragResizer(region, margin, dragDoneAction);
	}
	
	private RegionDragResizer(Region region) {
		super(region);
	}
	
	private RegionDragResizer(Region region, int margin) {
		super(region, margin);
	}
	
	private RegionDragResizer(Region region, int margin, Runnable dragDoneAction) {
		super(region, margin, dragDoneAction);
	}
	
}
