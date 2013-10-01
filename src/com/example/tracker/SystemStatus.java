package com.example.tracker;

public enum SystemStatus {
	
	MAINM("MainMenu"),
	INAPP("InApplication"),
	SWICH("Switch"),
	ERROR("Error");
	
	private String statusCode;
	
	private SystemStatus(String s) {
		statusCode = s;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
}
