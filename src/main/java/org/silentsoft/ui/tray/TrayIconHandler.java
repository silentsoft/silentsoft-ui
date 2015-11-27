package org.silentsoft.ui.tray;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TrayIconHandler {
	
	private static Logger LOGGER = LoggerFactory.getLogger(TrayIconHandler.class);
	
	private static TrayIcon trayIcon;
	
	private static PopupMenu getPopupMenu() {
		PopupMenu popupMenu = trayIcon.getPopupMenu();
		
		if (popupMenu == null) {
			popupMenu = new PopupMenu();
		}
		
		return popupMenu;
	}
	
	private static void add(MenuItem item) {
		if (isNotRegistered()) {
			return;
		}
		
		PopupMenu popupMenu = getPopupMenu();
		popupMenu.add(item);
		
		trayIcon.setPopupMenu(popupMenu);
	}
	
	private static void addToMenu(String menu, MenuItem item) {
		if (isNotRegistered()) {
			return;
		}
		
		if (isNotExistsMenu(menu)) {
			addMenu(menu);
		}
		
		for (int i=0, j=getPopupMenu().getItemCount(); i<j; i++) {
			if (getPopupMenu().getItem(i) instanceof Menu) {
				Menu menuitem = (Menu) getPopupMenu().getItem(i);
				if (menuitem.getLabel().equals(menu)) {
					menuitem.add(item);
					
					getPopupMenu().insert(menuitem, i);
					
					break;
				}
			}
		}
	}
	
	public static boolean isRegistered() {
		return (trayIcon != null && getPopupMenu() != null) ? true : false;
	}
	
	public static boolean isNotRegistered() {
		return !isRegistered();
	}
	
	public static boolean isExistsMenu(String menu) {
		if (isNotRegistered()) {
			return false;
		}
		
		for (int i=0, j=getPopupMenu().getItemCount(); i<j; i++) {
			if (getPopupMenu().getItem(i) instanceof Menu) {
				Menu item = (Menu) getPopupMenu().getItem(i);
				if (item.getLabel().equals(menu)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean isNotExistsMenu(String menu) {
		return !isExistsMenu(menu);
	}
	
	public static void registerTrayIcon(Image image) {
		registerTrayIcon(image, null, null);
	}
	
	public static void registerTrayIcon(Image image, String toolTip) {
		registerTrayIcon(image, toolTip, null);
	}
	
	public static void registerTrayIcon(Image image, String toolTip, ActionListener action) {
		if (SystemTray.isSupported()) {
			if (trayIcon != null) {
				trayIcon = null;
			}
			
			trayIcon = new TrayIcon(image);
			trayIcon.setImageAutoSize(true);
			
			if (toolTip != null) {
				trayIcon.setToolTip(toolTip);
			}
			
			if (action != null) {
				trayIcon.addActionListener(action);
			}
			
			try {
				for (TrayIcon registeredTrayIcon : SystemTray.getSystemTray().getTrayIcons()) {
					SystemTray.getSystemTray().remove(registeredTrayIcon);
				}
				
				SystemTray.getSystemTray().add(trayIcon);
			} catch (AWTException e) {
				LOGGER.error("I got catch an error during add system tray !", e);
			}
		} else {
			LOGGER.error("System tray is not supported !");
		}
	}
	
	public static void setToolTip(String toolTip) {
		if (isNotRegistered()) {
			return;
		}
		
		trayIcon.setToolTip(toolTip);
	}
	
	public static void setImage(Image image) {
		if (isNotRegistered()) {
			return;
		}
		
		trayIcon.setImage(image);
	}
	
	public static void displayMessage(String caption, String text, MessageType messageType) {
		if (isNotRegistered()) {
			return;
		}
		
		trayIcon.displayMessage(caption, text, messageType);
	}
	
	public static void addSeparator() {
		if (isNotRegistered()) {
			return;
		}
		
		getPopupMenu().addSeparator();
	}
	
	public static void addSeparator(String menu) {
		if (isNotRegistered()) {
			return;
		}
		
		for (int i=0, j=getPopupMenu().getItemCount(); i<j; i++) {
			if (getPopupMenu().getItem(i) instanceof Menu) {
				Menu item = (Menu) getPopupMenu().getItem(i);
				if (item.getLabel().equals(menu)) {
					item.addSeparator();
					
					getPopupMenu().insert(item, i);
					
					break;
				}
			}
		}
	}
	
	public static void addMenu(String menu) {
		add(new Menu(menu));
	}
	
	public static void addItem(String label, ActionListener action) {
		MenuItem menuItem = new MenuItem(label);
		menuItem.addActionListener(action);

		add(menuItem);
	}
	
	public static void addCheckBox(String label, ItemListener action) {
		addCheckBox(label, false, action);
	}
	
	public static void addCheckBox(String label, boolean state, ItemListener action) {
		CheckboxMenuItem checkboxMenuItem = new CheckboxMenuItem(label, state);
		checkboxMenuItem.addItemListener(action);
		
		add(checkboxMenuItem);
	}
	
	public static void addItemToMenu(String menu, String label, ActionListener action) {
		MenuItem menuItem = new MenuItem(label);
		menuItem.addActionListener(action);
		
		addToMenu(menu, menuItem);
	}
	
	public static void addCheckBoxToMenu(String menu, String label, ItemListener action) {
		addCheckBoxToMenu(menu, label, false, action);
	}
	
	public static void addCheckBoxToMenu(String menu, String label, boolean state, ItemListener action) {
		CheckboxMenuItem checkboxMenuItem = new CheckboxMenuItem(label, state);
		checkboxMenuItem.addItemListener(action);
		
		addToMenu(menu, checkboxMenuItem);
	}
}