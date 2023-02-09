package com.hau_trieu.chat.domains;

public class Message {
	
	private User sendUser;
	private String message;
	
	Message(){
		
	}

	public User getSendUser() {
		return sendUser;
	}

	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
