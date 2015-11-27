package org.silentsoft.ui.component.loadingbar;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
			LOGGER.info("LoadingBar already showed ! So, I'll hide first. and show later !");
			hide();
		}
		
		stage = new Stage();
		
		stage.setResizable(false);
		
		stage.initModality(Modality.NONE);
		stage.initStyle(StageStyle.UNDECORATED);
		
		stage.setScene(new Scene(parent, parent.prefWidth(0), parent.prefHeight(0)));
		
		if (owner == null) {
			LOGGER.debug("owner is null. So, I'll skip to initOwner !");
			
			stage.centerOnScreen();
		} else {
			stage.initOwner(owner);
			
			stage.setX(owner.getX() + (owner.getWidth()/2) - (stage.getScene().getWidth()/2));
			stage.setY(owner.getY() + (owner.getHeight()/2) - (stage.getScene().getHeight()/2));
		}
		
		stage.setAlwaysOnTop(true);
		stage.show();
		
		LOGGER.debug("LoadingBar showed !");
	}
	
	public synchronized static void hide() {
		if (stage != null) {
			stage.hide();
			
			stage = null;
			
			LOGGER.debug("LoadingBar hided !");
		}
	}
}
