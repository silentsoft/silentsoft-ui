package org.silentsoft.ui.component.loadingbar;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadingBar {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoadingBar.class);

	private static Stage stage;
	
	public synchronized static void show(Window owner) {
		show(owner, null);
	}
	
	public synchronized static void show(Window owner, KeyCode hideKeyCode) {
		show(owner, hideKeyCode, null);
	}
	
	public synchronized static void show(Window owner, KeyCode hideKeyCode, Runnable hideKeyTriggerAction) {
		Parent parent = null;
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(LoadingBar.class.getResource(LoadingBar.class.getSimpleName().concat(".fxml")));
			parent = fxmlLoader.load();
			
			if (parent == null) {
				throw new Exception("Can't load LoadingBar.fxml !");
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return;
		}
		
		if (stage != null) {
			LOGGER.info("LoadingBar already shown ! So, I'll hide first. and show later !");
			hide();
		}
		
		stage = new Stage();
		
		if (hideKeyCode != null) {
			stage.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					KeyCode code = event.getCode();
					if (code.isModifierKey() == false && code == hideKeyCode) {
						if (hideKeyTriggerAction != null) {
							try {
								hideKeyTriggerAction.run();
							} catch (Exception e) {
								;
							}
						}
						
						hide();
					}
				}
			});
		}
		
		stage.setResizable(false);
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.TRANSPARENT);
		
		stage.setScene(new Scene(parent, Color.TRANSPARENT));
		
		if (owner == null) {
			LOGGER.debug("owner is null. So, I'll skip to initOwner !");
			
			stage.centerOnScreen();
		} else {
			stage.initOwner(owner);
			
			stage.setX(owner.getX() + (owner.getWidth() / 2) - (parent.prefWidth(0) / 2));
			stage.setY(owner.getY() + (owner.getHeight() / 2) - (parent.prefHeight(0) / 2));
		}
		
		stage.setAlwaysOnTop(true);
		stage.show();
		
		LOGGER.debug("LoadingBar shown !");
	}
	
	public synchronized static void hide() {
		if (stage != null) {
			stage.hide();
			
			stage = null;
			
			LOGGER.debug("LoadingBar hided !");
		}
	}
}
