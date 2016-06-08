package org.silentsoft.ui.component.popup;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import org.silentsoft.ui.component.popup.Popup.CloseType;

public class PopupController {

	@FXML
	private AnchorPane main;

	@FXML
	private AnchorPane head;

	@FXML
	private AnchorPane body;

	@FXML
	private Label title;

	@FXML
	private Button closeButton;

	private Parent popup;
	
	private Node backgroundNode;

	private static final String TOP_ANCHOR = "pane-top-anchor";
	private static final String LEFT_ANCHOR = "pane-left-anchor";
	private static final String BOTTOM_ANCHOR = "pane-bottom-anchor";
	private static final String RIGHT_ANCHOR = "pane-right-anchor";

	@SuppressWarnings("static-access")
	protected void initialize(Node backgroundNode, String title, Parent popup, CloseType closeType) {
		this.backgroundNode = backgroundNode;
		this.title.setText(title);
		this.popup = popup;

		switch (closeType) {
		case FOCUS_BASE:
		case PLATFORM_FRAME_BASE:
			closeButton.setVisible(false);
			break;
		case BUTTON_BASE:
			closeButton.setVisible(true);
			break;
		case BUILT_IN_BASE:
			head.setVisible(false);
			body.setLayoutY(0);
			main.setTopAnchor(body, 0.0);
			break;
		}

		/* 2015.11.18 KYJ popup 기본 리사이즈처리가 되기위해 설정하는 값. */
		ObservableMap<Object, Object> prop = popup.getProperties();
		prop.put(TOP_ANCHOR, 0.0);
		prop.put(LEFT_ANCHOR, 0.0);
		prop.put(BOTTOM_ANCHOR, 0.0);
		prop.put(RIGHT_ANCHOR, 0.0);

		body.getChildren().clear();
		body.getChildren().add(popup);
	}

	@FXML
	private void close_OnActionClick() {
		Popup.close(popup, backgroundNode);
	}
}
