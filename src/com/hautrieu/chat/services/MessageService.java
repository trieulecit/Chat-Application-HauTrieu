package com.hautrieu.chat.services;

import java.util.ArrayList;
import java.util.List;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.domains.Group;
import com.hautrieu.chat.domains.InMemoryFile;
import com.hautrieu.chat.domains.Message;
import com.hautrieu.chat.domains.MessageReceivable;
import com.hautrieu.chat.domains.User;
import com.hautrieu.chat.repositories.Repository;

public class MessageService {

	private final DataStorage storage;

	public MessageService(DataStorage storage) {
		this.storage = storage;
	}

	public Message send(User sender, MessageReceivable receiver, String content, List<InMemoryFile> attachments) {
		Message message = new Message(sender, receiver, content, attachments);

		getAllMessagesInMemory().add(message);
		return message;
	}

	public Message delete(long messageId) {
		Message attempttedMessage = getAllMessagesInMemory().getById(messageId);

		List<InMemoryFile> attachedFiles = attempttedMessage.getAttachments();

		for (InMemoryFile fileItem : attachedFiles) {
			fileItem.delete();
		}
		attempttedMessage.setDeleted();
		return attempttedMessage;
	}

	public List<InMemoryFile> getAllFiles(User sender, MessageReceivable receiver) {

		List<InMemoryFile> files = constructFileList();
		List<Message> sentMessages = getExistingMessages(sender, receiver);

		for (Message message : sentMessages) {
			addAllAttachedFilesFromMessage(message, files);
		}
		return files;
	}
	
	private void addAllAttachedFilesFromMessage(Message message, List<InMemoryFile> targettedFile) {
		
		List<InMemoryFile> attachments = message.getAttachments();
		
		for(InMemoryFile fileItem: attachments) {
			targettedFile.add(fileItem);
		}
	}

	public List<Message> getTopLatestMessages(User sender, MessageReceivable receiver, long required, long ignored) {
		
		List<Message> outputMessages = constructMessageList();
		List<Message> sentMessages = getExistingMessages(sender, receiver);
		
		int size = sentMessages.size();
		long considered = required + ignored;

		for (long index = 1; index <= considered; index++) {

			if (index <= ignored) {
				continue;
			}
			
			Message targettedMessage = sentMessages.get((int) (size - index));
			outputMessages.add(targettedMessage);
		}
		return outputMessages;
	}

	public List<Message> getMessagesContain(User sender, MessageReceivable receiver, String keyWord) {

		List<Message> outputMessages = constructMessageList();
		List<Message> sentMessages = getExistingMessages(sender, receiver);

		for (Message messageItem : sentMessages) {
			
			String content = messageItem.getContent();
			
			if (content.contains(keyWord)) {
				outputMessages.add(messageItem);
			}
		}
		return outputMessages;
	}

	public List<Message> getRelatedMessage(User user) {
		
		List<Message> relatedMessage = constructMessageList();

		for (Group userGroup : user.getGroups()) {
			relatedMessage.addAll(getUserMessagesFromReceiver(user, userGroup));
		}
		return relatedMessage;
	}

	public List<Message> getUserMessagesFromReceiver(User sender, MessageReceivable receiver) {
		
		List<Message> sentMessages = getAllMessagesInMemory()
				.getAllMatching(message -> checkValidMessageMatchingInputs(message, sender, receiver));

		return sentMessages;
	}
	
	private boolean checkValidMessageMatchingInputs(Message message, User sender, MessageReceivable receiver) {
		
		UserService userService = new UserService(storage);

		boolean checkCorrectSender = userService.compareTwoUsersById(message.getSender(), sender);
		boolean checkCorrectReceiver = compareTwoReceivers(message.getReceiver(), receiver);
		boolean checkMessageNotDeleted = !message.getDeleted();
		
		return checkCorrectSender && checkCorrectReceiver && checkMessageNotDeleted;
	}
	
	private boolean compareTwoReceivers(MessageReceivable firstReceiver, MessageReceivable secondReceiver) {
		long firstId = firstReceiver.getReceiverId();
		long secondId = secondReceiver.getReceiverId();
		
		boolean condition = (firstId == secondId);
		
		return condition;
	}

	private List<Message> getExistingMessages(User sender, MessageReceivable receiver) {

		List<Message> sentMessages = getAllMessagesInMemory()
				.getAllMatching(message -> checkValidMessageMatchingInputs(message, sender, receiver));

		return sentMessages;
	}
	
	private Repository<Message> getAllMessagesInMemory() {
		return storage.getMessages();
	}

	private List<Message> constructMessageList() {
		return new ArrayList<Message>();
	}

	private List<InMemoryFile> constructFileList() {
		return new ArrayList<InMemoryFile>();
	}
	
	public boolean compareCorrectMessageContent(Message message, String comparator) {
		
		String content = message.getContent();	
		boolean condition = content.equals(comparator);
		
		return condition;
	}
}
