package org.silentsoft.ui.component.messagebox;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javafx.application.Platform;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

@SuppressWarnings("deprecation")
public final class MessageBox {
	
	public static void showAbout(String message) {
		showInformation(null, null, message);
	}
	
	public static void showAbout(Object owner, String message) {
		showInformation(owner, null, message);
	}
	
	public static void showAbout(String masthead, String message) {
		showInformation(null, masthead, message);
	}
	
	public static void showInformation(Object owner, String masthead, String message) {
		Platform.runLater(() -> {
			String title = "Information";
			
			if (owner == null) {
				if (masthead == null) {
					Dialogs.create().title(title).message(message).showInformation();
				} else {
					Dialogs.create().title(title).masthead(masthead).message(message).showInformation();
				}
			} else {
				if (masthead == null) {
					Dialogs.create().owner(owner).title(title).message(message).showInformation();
				} else {
					Dialogs.create().owner(owner).title(title).masthead(masthead).message(message).showInformation();
				}
			}
		});
	}
	
	public static Action showConfirm(String message) {
		return showConfirm(null, null, message);
	}
	
	public static Action showConfirm(Object owner, String message) {
		return showConfirm(owner, null, message);
	}
	
	public static Action showConfirm(String masthead, String message) {
		return showConfirm(null, masthead, message);
	}
	
	public static Action showConfirm(Object owner, String masthead, String message) {
		//////////////////////////////////////////////////////////////
		String title = "Confirm";
		
		if (owner == null) {
			if (masthead == null) {
				return Dialogs.create().title(title).message(message).showConfirm();
			} else {
				return Dialogs.create().title(title).masthead(masthead).message(message).showConfirm();
			}
		} else {
			if (masthead == null) {
				return Dialogs.create().owner(owner).title(title).message(message).showConfirm();
			} else {
				return Dialogs.create().owner(owner).title(title).masthead(masthead).message(message).showConfirm();
			}
		}
		//////////////////////////////////////////////////////////////
		/**
		FutureTask<Action> futureTask = new FutureTask<Action>(() -> {
			String title = "Confirm";
			
			if (owner == null) {
				if (masthead == null) {
					return Dialogs.create().title(title).message(message).showConfirm();
				} else {
					return Dialogs.create().title(title).masthead(masthead).message(message).showConfirm();
				}
			} else {
				if (masthead == null) {
					return Dialogs.create().owner(owner).title(title).message(message).showConfirm();
				} else {
					return Dialogs.create().owner(owner).title(title).masthead(masthead).message(message).showConfirm();
				}
			}
		});
		
		Platform.runLater(futureTask);
		
		Action action = null;
		try {
			action = futureTask.get();
		} catch (Exception e) {
			;
		}
		
		return action;
		*/
	}
	
	public static void showError(String message) {
		showError(null, null, message);
	}
	
	public static void showError(Object owner, String message) {
		showError(owner, null, message);
	}
	
	public static void showError(String masthead, String message) {
		showError(null, masthead, message);
	}
	
	public static void showError(Object owner, String masthead, String message) {
		Platform.runLater(() -> {
			String title = "Error";
			
			if (owner == null) {
				if (masthead == null) {
					Dialogs.create().title(title).message(message).showError();
				} else {
					Dialogs.create().title(title).masthead(masthead).message(message).showError();
				}
			} else {
				if (masthead == null) {
					Dialogs.create().owner(owner).title(title).message(message).showError();
				} else {
					Dialogs.create().owner(owner).title(title).masthead(masthead).message(message).showError();
				}
			}
		});
	}
	
	public static void showException(Throwable exception) {
		showException(null, null, null, exception);
	}
	
	public static void showException(Object owner, Throwable exception) {
		showException(owner, null, null, exception);
	}
	
	public static void showException(String message, Throwable exception) {
		showException(null, null, message, exception);
	}
	
	public static void showException(Object owner, String message, Throwable exception) {
		showException(owner, null, message, exception);
	}
	
	public static void showException(String masthead, String message, Throwable exception) {
		showException(null, masthead, message, exception);
	}
	
	public static void showException(Object owner, String masthead, String message, Throwable exception) {
		Platform.runLater(() -> {
			String title = "Exception";
			
			if (owner == null) {
				if (masthead == null) {
					if (message == null) {
						Dialogs.create().title(title).showException(exception);
					} else {
						Dialogs.create().title(title).message(message).showException(exception);
					}
				} else {
					if (message == null) {
						Dialogs.create().title(title).masthead(masthead).showException(exception);
					} else {
						Dialogs.create().title(title).masthead(masthead).message(message).showException(exception);
					}
				}
			} else {
				if (masthead == null) {
					if (message == null) {
						Dialogs.create().owner(owner).title(title).showException(exception);
					} else {
						Dialogs.create().owner(owner).title(title).message(message).showException(exception);
					}
				} else {
					if (message == null) {
						Dialogs.create().owner(owner).title(title).masthead(masthead).showException(exception);
					} else {
						Dialogs.create().owner(owner).title(title).masthead(masthead).message(message).showException(exception);
					}
				}
			}
		});
	}
}
