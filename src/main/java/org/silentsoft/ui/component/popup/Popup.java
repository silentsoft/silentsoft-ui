package org.silentsoft.ui.component.popup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Popup {

	private static final Logger LOGGER = LoggerFactory.getLogger(Popup.class);

	/**
	 * FOCUS_BASE
	 * 	: close when lost focus</p>
	 * BUTTON_BASE
	 * 	: close when user click close button that default designed</p>
	 * BUILT_IN_BASE
	 *  : close when user click built in designed close button(= User custom design.fxml)</p>
	 * PLATFORM_FRAME_BASE
	 *  : close when user click platform framed close button(= StageStyle.DECORATED)</p>
	 *    
	 * @author Silentsoft
	 *
	 */
	public enum CloseType {
		FOCUS_BASE,
		BUTTON_BASE,
		BUILT_IN_BASE,
		PLATFORM_FRAME_BASE,
	};

	private static Map<Parent, Map<Stage, Effect>> popupMap = new HashMap<Parent, Map<Stage, Effect>>();

	public static void show(Window owner, Node backgroundNode, String title, Parent popup, CloseType closeType, boolean isModal, boolean showColorAdjustEffect) {
		if (popup == null) {
			return;
		}

		Parent parent = null;
		PopupController controller = null;

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Popup.class.getResource(Popup.class.getSimpleName().concat(".fxml")));
			parent = fxmlLoader.load();
			controller = fxmlLoader.getController();
			controller.initialize(backgroundNode, title, popup, closeType);
		} catch (IOException e) {
			LOGGER.error("Popup show failure !");
			LOGGER.error(e.toString());
		}

		if (parent == null || controller == null) {
			return;
		}

		Stage stage = new Stage();
		
		stage.initOwner(owner);
		if (isModal) {
			stage.initModality(Modality.APPLICATION_MODAL);
		} else {
			stage.initModality(Modality.NONE);
		}
		
		if (closeType == CloseType.PLATFORM_FRAME_BASE) {
			stage.initStyle(StageStyle.DECORATED);
		} else {
			stage.initStyle(StageStyle.TRANSPARENT);
		}
		stage.setTitle(title);

		Map<Stage, Effect> value = new HashMap<Stage, Effect>();
		value.put(stage, backgroundNode.getEffect());

		popupMap.put(popup, value);

		stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!oldValue && newValue) {
					if (backgroundNode != null) {
						// if got focus
						if (showColorAdjustEffect) {
							backgroundNode.setEffect(new ColorAdjust(0, 0, -0.5, 0));
						}
						backgroundNode.setDisable(true);
					}
				} else if (oldValue && !newValue) {
					// if lost focus
					if (closeType == CloseType.FOCUS_BASE) {
						close(popup, backgroundNode);
					}
				}
			}
		});

		double prefWidth = parent.prefWidth(0);
		double prefHeight = parent.prefHeight(0);
		Scene scene = new Scene(parent, prefWidth, prefHeight);

		stage.setScene(scene);
		stage.setX(owner.getX() + (owner.getWidth() / 2) - (scene.getWidth() / 2));
		stage.setY(owner.getY() + (owner.getHeight() / 2) - (scene.getHeight() / 2));
		
		if (isModal) {
			stage.showAndWait();
		} else {
			stage.show();
		}
	}
	
	public static void close(Parent popup, Node backgroundNode) {
		if (popup == null) {
			return;
		}

		for (Entry<Stage, Effect> entrySet : popupMap.get(popup).entrySet()) {
			Stage stage = entrySet.getKey();
			if (stage != null) {
				stage.close();

				if (backgroundNode != null) {
					backgroundNode.setEffect(entrySet.getValue());
					backgroundNode.setDisable(false);
				}

				popupMap.remove(popup);
			}

			break;
		}
	}
}
