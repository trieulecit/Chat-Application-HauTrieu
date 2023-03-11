package com.hautrieu.chat.services;

import java.util.List;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.domains.User;
import com.hautrieu.chat.repositories.Repository;

public class UserService {

	private final DataStorage storage;

	public UserService(DataStorage storage) {
		this.storage = storage;
	}

	public boolean login(String username, String password) {
		User attemptedUser = findUserByUserName(username);
		
		if (!doesUsernameExist(username)) {
			return false;
		}
		return attemptedUser.login(password);
	}

	public boolean addUser(String username, String password) {
		
		TextService service = new TextService();
		
		if (doesUsernameExist(username)) {
			return false;
		}	
		User newUser = new User(username, service.hashMD5(password));
		getUsersFromStorage().add(newUser);
		
		return true;
	}

	public List<User> getUsers(String fullName) {
		return getUsersFromStorage().getAllMatching(user -> user.getFullName().equals(fullName));
	}
	
	private User findUserByUserName(String username) {
		return getUsersFromStorage().getFirst(user -> user.getUsername().equals(username));
	}
	
	private Repository<User> getUsersFromStorage() {
		return storage.getUsers();
	}
	
	private boolean doesUsernameExist(String username) {
		User existing = findUserByUserName(username);
		return existing != null;
	}
}