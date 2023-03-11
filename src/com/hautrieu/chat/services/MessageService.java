package com.hautrieu.chat.services;

import java.util.ArrayList;
import java.util.List;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.domains.InMemoryFile;
import com.hautrieu.chat.domains.Message;
import com.hautrieu.chat.domains.MessageReceivable;
import com.hautrieu.chat.domains.User;

public class MessageService {
	private final DataStorage storage;

	public MessageService(DataStorage storage) {
		this.storage = storage;
	}
	
	public Message send(User sender, MessageReceivable receiver, String content, List<InMemoryFile> attachments) {
		Message message = new Message(sender, receiver, content, attachments);
		storage.getMessages().add(message);
		return message;
	}
	
	public Message delete(long messageId) {
		Message attempttedMessage = storage.getMessages().getById(messageId);		
		List<InMemoryFile> files = attempttedMessage.getAttachments();		
		for(InMemoryFile fileItem: files) {
			fileItem.delete();
		}
		attempttedMessage.setDeleted();
		return attempttedMessage;
	}
	
	public List<InMemoryFile> getAllFiles(User sender, MessageReceivable receiver) {
		List<InMemoryFile> files = new ArrayList<>();
		List<Message> sentMessages = storage.getMessages()
				.getAllMatching(message -> 
				message.getSender().getId() == sender.getId() 
				&& message.getReceiver().getReceiverId() == receiver.getReceiverId() 
				&& !message.getDeleted());
		for(Message message: sentMessages) {
			for(InMemoryFile innerFile: message.getAttachments()) {
				files.add(innerFile);
			}
		}
		return files;
	}
	
	public List<Message> getTopLatestMessages(User sender, MessageReceivable receiver, long required, long ignored) {
		List<Message> outputMessages = new ArrayList<>();
		List<Message> sentMessages = storage.getMessages()
				.getAllMatching(message -> 
				message.getSender().getId() == sender.getId() 
				&& message.getReceiver().getReceiverId() == receiver.getReceiverId() 
				&& !message.getDeleted());
		for(long index = 1; index <= required + ignored; index++) {
			if(index <= ignored) {
				continue;
			}
			
			outputMessages.add(sentMessages.get((int)(sentMessages.size() - index)));
		}
		
		return outputMessages;
	}
	
	public List<Message> getMessagesContain(User sender, MessageReceivable receiver, String keyword) {
		List<Message> outputMessages = new ArrayList<>();
		List<Message> sentMessages = storage.getMessages()
				.getAllMatching(message -> 
				message.getSender().getId() == sender.getId() 
				&& message.getReceiver().getReceiverId() == receiver.getReceiverId() 
				&& !message.getDeleted());
		for(Message messageItem: sentMessages) {
			if(messageItem.getContent().contains(keyword)) {
				outputMessages.add(messageItem);
			}
		}		
		return outputMessages;
	}
}
