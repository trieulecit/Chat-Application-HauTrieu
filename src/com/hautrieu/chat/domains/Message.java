package com.hautrieu.chat.domains;

import java.util.Map;

public class Message {
	
	private User sender;
	private MessageReceivable receiver;
	private String content;
	private Map<Long, File> attachments;
	private long timestamp;	

	public Message(User sender, MessageReceivable receiver, String content, Map<Long, File> attachments) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.attachments = attachments;
		this.timestamp = System.currentTimeMillis();
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sendUser) {
		sender = sendUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String message) {
		this.content = message;
	}

	public MessageReceivable getReceiver() {
		return receiver;
	}

	public void setReceiver(MessageReceivable receiver) {
		this.receiver = receiver;
	}

	public Map<Long, File> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<Long, File> attachments) {
		this.attachments = attachments;
	}

	public long getTimestamp() {
		return timestamp;
	}
}