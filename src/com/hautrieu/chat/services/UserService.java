package com.hautrieu.chat.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;
import com.hautrieu.chat.domains.User;

public class UserService {

	private User currentUser;
	private final DataStorage storage;

	public UserService(DataStorage storage) {
		this.storage = storage;
	}

	public boolean login(String username, String password) {
		User attemptedUser = storage.getUsers().getFirst(user -> user.getUsername().equals(username));
		if (attemptedUser == null) {
			return false;
		}
		return attemptedUser.login(password);
	}

	public boolean addUser(String username, String password) {
		TextService service = new TextService();
		User existing = storage.getUsers().getFirst(user -> user.getUsername().equals(username));
		
		if (existing != null) {
			return false;
		}
	
		User newUser = new User(username, service.hashMD5(password));
		storage.getUsers().add(newUser);
		return true;
	}

	public List<User> getUsers(String fullName) {
		return storage.getUsers().getAllMatching(user -> user.getFullName().equals(fullName));
	}
	
	public static void main(String[] args) {
		DataStorage dataStorage = InMemoryDataStorage.getInstance();
		UserService service = new UserService(dataStorage);
		
		dataStorage.getUsers().deleteAll();
		
		service.addUser("trieu", "password");
		service.addUser("somebody", "somebody");

		User firstUser = dataStorage.getUsers().getFirst(user -> user.getUsername().equals("somebody"));
		User secondUser = dataStorage.getUsers().getFirst(user -> user.getUsername().equals("trieu"));
		firstUser.setFirstName("Trieu");
		firstUser.setLastName("Le Hoang");
		secondUser.setFirstName("Trieu");
		secondUser.setLastName("Le Hoang");

		List<User> usersMatched = service.getUsers("Trieu Le Hoang");
		System.out.println(usersMatched.size());
	}
	
}