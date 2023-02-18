package com.hautrieu.chat.data;

import com.hautrieu.chat.domains.Group;
import com.hautrieu.chat.domains.User;
import com.hautrieu.chat.repositories.Repository;

public abstract class DataStorage {
	private Repository<User> users;
	private Repository<Group> groups;

    public Repository<User> getUsers() {
        return users;
    }
    
    public Repository<Group> getGroups() {
    	return groups;
    }
}
