package com.hau_trieu.chat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hau_trieu.chat.domains.Group;
import com.hau_trieu.chat.domains.User;

public class UserService {

	private User currentUser = null;

	private List<User> users = new ArrayList<>();

	public void create(User newUser) {

//		newUser.setUsername("");
//		newUser.setHashPassword("");
//		newUser.setFirstName("");
//		newUser.setLastName("");
//		newUser.setDateOfBirth();

		users.add(newUser);

		currentUser = newUser;
	}
	public User getCurrentUser() {
		return this.currentUser;
	}
	public List<User> getUserList(){
		return this.users;
	}

	public void login(String username, String password) {

		boolean accountValid = false;

		for (User user : users) {
			if (user.getUsername().equalsIgnoreCase(username)) {

				String passwordHash = user.passwordHash(password);

				if (passwordHash.equals(user.getHashPassword())) {
					currentUser = user;
					accountValid = true;
				}
				if (accountValid) {
					System.out.println("Welcome welcome! " + currentUser.getFullName());
				} else {
					System.out.println("your username or password is incorrect, please try again.");
				}
				break;

			}

		}

	}

	public void logout() {
		currentUser = null;
		System.out.println("Logged out");
	}

	public void createGroup(User creator, boolean isPrivate) {
		Group group = new Group();

		group.addMember(creator);

//		if creating private group, request user will be the admin
		group.setPrivate(isPrivate, creator);

	}

	public void joinGroup(String groupName) {
		GroupService groupService = new GroupService();
		boolean joinSuccess = false;
		if (currentUser != null) {
			joinSuccess = groupService.joinGroup(groupName, currentUser);
		}
	}

	public void leaveGroup() {

	}

	public void sendMessage() {

	}
}
