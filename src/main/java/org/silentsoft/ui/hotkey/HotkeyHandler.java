package org.silentsoft.ui.hotkey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;
import javafx.scene.input.KeyEvent;

public class HotkeyHandler implements EventHandler<KeyEvent> {

	private static HotkeyHandler hotkeyHandler;
	
	private Map<KeyCodeCombination, Runnable> hotkeyMap;
	
	private HotkeyHandler() { }

	public static HotkeyHandler getInstance() {
		if (hotkeyHandler == null) {
			hotkeyHandler = new HotkeyHandler();
		}
		
		return hotkeyHandler;
	}
	
	private Map<KeyCodeCombination, Runnable> getHotkeyMap() {
		if (hotkeyMap == null) {
			hotkeyMap = new HashMap<KeyCodeCombination, Runnable>();
		}
		
		return hotkeyMap;
	}
	
	public boolean registerHotkey(KeyCode keyCode, boolean shiftDown, boolean controlDown, boolean altDown, Runnable action) {
		boolean result = true;
		
		if (keyCode.isModifierKey()) {
			// Key code must not match modifier key. it may occur IllegalArgumentException.
			result = false;
		} else {
			getHotkeyMap().put(new KeyCodeCombination(keyCode, makeModifiers(shiftDown, controlDown, altDown)), action);
		}
		
		return result;
	}
	
	private Modifier[] makeModifiers(boolean shiftDown, boolean controlDown, boolean altDown) {
		List<Modifier> modifiers = new ArrayList<Modifier>();
		
		if (shiftDown) {
			modifiers.add(KeyCombination.SHIFT_DOWN);
		}
		
		if (controlDown) {
			modifiers.add(KeyCombination.CONTROL_DOWN);
		}

		if (altDown) {
			modifiers.add(KeyCombination.ALT_DOWN);
		}
		
		return modifiers.toArray(new Modifier[0]);
	}
	
	@Override
	public void handle(KeyEvent event) {
		if (event.getCode().isModifierKey() == false) {
			for (Entry<KeyCodeCombination, Runnable> entry : getHotkeyMap().entrySet()) {
				if (entry.getKey().match(event)) {
					entry.getValue().run();
				}
			}
		}
	}
}
