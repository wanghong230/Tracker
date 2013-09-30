package com.example.tracker;

public class AggregateMessages {
	public static String messages = null;
	
	public static synchronized void addMessages(String message) {
		messages = messages + "/n" + message;
	}
	
	public static synchronized void setMessages(String newMessages) {
		messages = newMessages;
	}
	
	public static synchronized String getMessages() {
		return messages;
	}
	
	public static synchronized void cleanMessages() {
		messages = null;
	}
}
