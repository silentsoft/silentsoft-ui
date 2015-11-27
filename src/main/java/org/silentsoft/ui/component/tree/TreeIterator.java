package org.silentsoft.ui.component.tree;

import java.util.Iterator;
import java.util.Stack;

import javafx.scene.control.TreeItem;

public class TreeIterator<T> implements Iterator<TreeItem<T>> {

	private Stack<TreeItem<T>> stack = new Stack<>();

    public TreeIterator(TreeItem<T> root) {
        stack.push(root);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public TreeItem<T> next() {
        TreeItem<T> nextItem = stack.pop();
        nextItem.getChildren().forEach(stack::push);

        return nextItem;
    }

}