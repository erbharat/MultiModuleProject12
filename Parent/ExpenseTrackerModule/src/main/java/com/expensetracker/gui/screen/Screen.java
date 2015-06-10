package com.expensetracker.gui.screen;

import java.net.URL;

import com.expensetracker.gui.controller.ScreenController;

public abstract class Screen {
	private ScreenController screenController;
	
	public Screen(ScreenController controller) {
		screenController = controller;
	}
	
	public abstract URL getFXMLResource();
}
