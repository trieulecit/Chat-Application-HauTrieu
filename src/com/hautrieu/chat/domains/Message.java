package com.hautrieu.chat.domains;

import java.util.List;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;

public class Message extends BaseEntity {
	
	private User sender;
	private MessageReceivable receiver;
	private String content;
	private List<File> attachments;
	private long timestamp;	

	public Message(User sender, MessageReceivable receiver, String content, List<File> attachments) {
		super(generateId());
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.attachments = attachments;
		this.timestamp = System.currentTimeMillis();
	}
	
	public static long generateId() {
		DataStorage storage = InMemoryDataStorage.getInstance();
		long size = storage.getMessages().getSize();		
		return size + 1;
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

	public List<File> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<File> attachments) {
		this.attachments = attachments;
	}

	public long getTimestamp() {
		return timestamp;
	}
}