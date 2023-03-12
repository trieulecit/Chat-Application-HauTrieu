package com.hautrieu.chat.domains;

import java.util.List;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;

public class Message extends BaseEntity {

	private User sender;
	private MessageReceivable receiver;
	private String content;
	private List<InMemoryFile> attachments;
	private long timestamp;	
	private boolean deleted;

	public Message(User senderAsInput, MessageReceivable receiverAsInput, String contentAsInput, List<InMemoryFile> attachments) {
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.attachments = attachments;
		this.timestamp = System.currentTimeMillis();
		this.deleted = false;
	}
	
	public boolean getDeleted() {
		return deleted;
	}
	
	public void setDeleted() {
		deleted = true;
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
		content = message;
	}

	public MessageReceivable getReceiver() {
		return receiver;
	}

	public void setReceiver(MessageReceivable receiverAsInput) {
		receiver = receiverAsInput;
	}

	public List<InMemoryFile> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<InMemoryFile> attachments) {
		this.attachments = attachments;
	}

	public long getTimestamp() {
		return timestamp;
	}
}