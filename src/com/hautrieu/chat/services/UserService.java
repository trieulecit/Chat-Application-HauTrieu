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
		
		if (!checkUserNameExists(username)) {
			return false;
		}
		return attemptedUser.login(password);
	}

	public boolean addUser(String username, String password) {
		
		TextService service = new TextService();
		
		if (checkUserNameExists(username)) {
			return false;
		}	
		User newUser = new User(username, service.hashByMD5(password));
		getUsersFromStorage().add(newUser);
		
		return true;
	}

	public List<User> getUsers(String fullName) {
		return getUsersFromStorage().getAllMatching(user -> user.getFullName().equals(fullName));
	}
	
	private User findUserByUserName(String userName) {
		return getUsersFromStorage().getFirst(user -> checkCorrectUserName(user, userName));
	}
	
	private boolean checkCorrectUserName(User user, String comparator) {
		
		String userName = user.getUserName();		
		return userName.equals(comparator);
	}
	
	private Repository<User> getUsersFromStorage() {
		return storage.getUsers();
	}
	
	private boolean checkUserNameExists(String username) {
		User existing = findUserByUserName(username);
		return existing != null;
	}
	
	public boolean compareTwoUsersById(User firstUser, User secondUser) {
		
		long firstId = firstUser.getId();
		long secondId = secondUser.getId();
		
		boolean condition = firstId == secondId;
		
		return condition;
	}
	
	public boolean compareCorrectUserName(User user, String comparator) {
		
		String userName = user.getUserName();
		
		boolean condition = userName.equalsIgnoreCase(comparator);
		
		return condition;
	}
}