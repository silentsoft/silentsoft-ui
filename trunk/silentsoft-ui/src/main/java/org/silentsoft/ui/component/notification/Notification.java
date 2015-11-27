package org.silentsoft.ui.component.notification;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import org.controlsfx.control.Notifications;

public final class Notification {

	public enum NotifyType {
		INFORMATION,
		CONFIRM,
		WARNING,
		ERROR
	}
	
	public static void show(String title, String text) {
		show(null, title, text, null, null);
	}
	
	public static void show(String title, String text, EventHandler<ActionEvent> onAction) {
		show(null, title, text, null, onAction);
	}
	
	public static void show(String title, String text, NotifyType notifyType) {
		show(null, title, text, notifyType, null);
	}
	
	public static void show(String title, String text, NotifyType notifyType, EventHandler<ActionEvent> onAction) {
		show(null, title, text, notifyType, onAction);
	}
	
	public static void show(Object owner, String title, String text) {
		show(owner, title, text, null, null);
	}
	
	public static void show(Object owner, String title, String text, NotifyType notifyType) {
		show(owner, title, text, notifyType, null);
	}
	
	public static void show(Object owner, String title, String text, EventHandler<ActionEvent> onAction) {
		show(owner, title, text, null, onAction);
	}
	
	public static void show(Object owner, String title, String text, NotifyType notifyType, EventHandler<ActionEvent> onAction) {
		Platform.runLater(() -> {
			if (notifyType == null) {
				if (owner == null) {
					if (onAction == null) {
						Notifications.create().title(title).text(text).show();
					} else {
						Notifications.create().title(title).text(text).onAction(onAction).show();
					}
				} else {
					if (onAction == null) {
						Notifications.create().owner(owner).title(title).text(text).show();
					} else {
						Notifications.create().owner(owner).title(title).text(text).onAction(onAction).show();
					}
				}
			} else {
				switch (notifyType) {
				case INFORMATION:
					if (owner == null) {
						if (onAction == null) {
							Notifications.create().title(title).text(text).showInformation();
						} else {
							Notifications.create().title(title).text(text).onAction(onAction).showInformation();
						}
					} else {
						if (onAction == null) {
							Notifications.create().owner(owner).title(title).text(text).showInformation();
						} else {
							Notifications.create().owner(owner).title(title).text(text).onAction(onAction).showInformation();
						}
					}
					break;
				case CONFIRM:
					if (owner == null) {
						if (onAction == null) {
							Notifications.create().title(title).text(text).showConfirm();
						} else {
							Notifications.create().title(title).text(text).onAction(onAction).showConfirm();
						}
					} else {
						if (onAction == null) {
							Notifications.create().owner(owner).title(title).text(text).showConfirm();
						} else {
							Notifications.create().owner(owner).title(title).text(text).onAction(onAction).showConfirm();
						}
					}
					break;
				case WARNING:
					if (owner == null) {
						if (onAction == null) {
							Notifications.create().title(title).text(text).showWarning();
						} else {
							Notifications.create().title(title).text(text).onAction(onAction).showWarning();
						}
					} else {
						if (onAction == null) {
							Notifications.create().owner(owner).title(title).text(text).showWarning();
						} else {
							Notifications.create().owner(owner).title(title).text(text).onAction(onAction).showWarning();
						}
					}
					break;
				case ERROR:
					if (owner == null) {
						if (onAction == null) {
							Notifications.create().title(title).text(text).showError();
						} else {
							Notifications.create().title(title).text(text).onAction(onAction).showError();
						}
					} else {
						if (onAction == null) {
							Notifications.create().owner(owner).title(title).text(text).showError();
						} else {
							Notifications.create().owner(owner).title(title).text(text).onAction(onAction).showError();
						}
					}
					break;
				}
			}
		});
	}
}
