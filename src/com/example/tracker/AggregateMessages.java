package com.example.tracker;

public class AggregateMessages {
	public static StringBuffer messages = null;
	public static int count = 0;
	public static final int max = 200;
	
	public static synchronized void addMessages(String message) {
		if(messages == null) {
			messages = new StringBuffer();
		}
		if(count < max) {
			messages.append(message + '\n');
			count++;
		}
	}
	
	public static synchronized void setMessages(String newMessages) {
		messages = new StringBuffer(newMessages);
	}
	
	public static synchronized String getMessages() {
		return messages.toString();
	}
	
	public static synchronized void cleanMessages() {
		messages = null;
		count = 0;
	}
}
