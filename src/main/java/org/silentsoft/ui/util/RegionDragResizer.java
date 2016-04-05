package org.silentsoft.ui.util;

import javafx.scene.layout.Region;

/**
 * @author silentsoft
 */
public class RegionDragResizer extends DragResizer {

	private RegionDragResizer(Region region) {
		super(region);
	}
	
	public static void makeResizable(Region region) {
		new RegionDragResizer(region);
	}
}
