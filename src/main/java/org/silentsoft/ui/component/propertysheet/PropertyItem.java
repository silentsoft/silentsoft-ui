package org.silentsoft.ui.component.propertysheet;

import java.util.Map;

import org.controlsfx.control.PropertySheet.Item;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PropertyItem implements Item {
	private Map<String, Object> map;
	private String key;
	private String name;
	private String description;

	public PropertyItem(Map<String, Object> map, String key, String name, String description) {
		this.map = map;
		this.key = key;
		this.name = name;
		this.description = description;
	}

	@Override
	public String getCategory() {
		return null;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<?> getType() {
		return getValue().getClass();
	}

	@Override
	public Object getValue() {
		return map.get(key);
	}

	@Override
	public void setValue(Object arg0) {
		map.put(key, arg0);
	}
}