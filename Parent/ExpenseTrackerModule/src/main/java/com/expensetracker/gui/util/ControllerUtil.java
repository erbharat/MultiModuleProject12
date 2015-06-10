package com.expensetracker.gui.util;

import java.util.HashMap;
import java.util.Map;

public class ControllerUtil {
	
	public final static ControllerUtil controllerUtil = new ControllerUtil();
	private static Map<Object, Object> controllerMap = new HashMap<Object, Object>();
	
	public void setController(Object controllerKey, Object controller) {
		controllerMap.put(controllerKey, controller);
	}
	
	public Object getController(Object key) {
		return controllerMap.get(key);
	}
}
