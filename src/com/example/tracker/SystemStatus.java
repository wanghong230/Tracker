package com.example.tracker;

public enum SystemStatus {
	
	MAINMENU("MainMenu"),
	INAPPLICATION("InApplication"),
	SWITCH("Switch"),
	ERROR("Error");
	
	private String statusCode;
	
	private SystemStatus(String s) {
		statusCode = s;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
}
