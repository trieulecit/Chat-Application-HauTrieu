package com.hautrieu.chat.data;

import com.hautrieu.chat.domains.Group;
import com.hautrieu.chat.domains.InMemoryFile;
import com.hautrieu.chat.domains.Message;
import com.hautrieu.chat.domains.User;

import com.hautrieu.chat.repositories.InMemoryRepository;
import com.hautrieu.chat.repositories.Repository;

public abstract class DataStorage {
	
	private Repository<User> users;
	private Repository<Group> groups;
	private Repository<Message> messages;
	private Repository<InMemoryFile> files;

	public DataStorage() {
		users = new InMemoryRepository<>();
		groups = new InMemoryRepository<>();
		messages = new InMemoryRepository<>();
		files = new InMemoryRepository<>();
	}
	
    public Repository<User> getUsers() {
        return users;
    }
    
    public Repository<Group> getGroups() {
    	return groups;
    }
    
    public Repository<Message> getMessages() {
    	return messages;
    }
    
    public Repository<InMemoryFile> getFiles() {
    	return files;
    }
}
