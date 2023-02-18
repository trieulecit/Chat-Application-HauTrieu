package com.hau_trieu.chat.data;

import com.hau_trieu.chat.domains.Group;
import com.hau_trieu.chat.domains.User;
import com.hau_trieu.chat.repositories.Repository;

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
