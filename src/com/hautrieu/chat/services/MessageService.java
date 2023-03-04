package com.hautrieu.chat.services;

import java.util.List;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.domains.File;
import com.hautrieu.chat.domains.Message;
import com.hautrieu.chat.domains.MessageReceivable;
import com.hautrieu.chat.domains.User;

public class MessageService {
	private final DataStorage storage;

	public MessageService(DataStorage storage) {
		this.storage = storage;
	}
	
	public void send(User sender, MessageReceivable receiver, String content, List<File> attachments) {
		Message message = new Message(sender, receiver, content, attachments);
		storage.getMessages().add(message);
	}
}
