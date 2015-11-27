package org.silentsoft.ui.component.tree;

import javafx.scene.control.TreeItem;

public class TreePath {
	
	public static TreeItem[] getPath(TreeItem node) {
		return getPathToRoot(node, 0);
	}
	
	protected static TreeItem[] getPathToRoot(TreeItem aNode, int depth) {
		TreeItem[] retNodes;
		
		if (aNode == null) {
			if (depth == 0) {
				return null;
			} else {
				retNodes = new TreeItem[depth];
			}
		} else {
			depth++;
			retNodes = getPathToRoot(aNode.getParent(), depth);
			retNodes[retNodes.length - depth] = aNode;
		}
		
		return retNodes;
	}
	
}
