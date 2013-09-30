package com.example.tracker;

public class AggregateMessages {
	public static StringBuffer messages = null;
	
	public static synchronized void addMessages(String message) {
		if(messages == null) {
			messages = new StringBuffer();
		}
		messages.append(message + '\n');
	}
	
	public static synchronized void setMessages(String newMessages) {
		messages = new StringBuffer(newMessages);
	}
	
	public static synchronized String getMessages() {
		return messages.toString();
	}
	
	public static synchronized void cleanMessages() {
		messages = null;
	}
}
