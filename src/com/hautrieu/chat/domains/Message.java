package com.hautrieu.chat.domains;

public class Message {
	
	private User sendUser;
	private String message;
	private File attachment;
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

	public File getAttachment() {
		return attachment;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}
	
}