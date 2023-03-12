package com.hautrieu.chat.domains;

import com.hautrieu.chat.services.TextService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class User extends BaseEntity implements MessageReceivable {

	private String userName;
	private String lastName;
	private String firstName;
	private String hashPassword;
	private String gender;
	private String dateOfBirth;

	private HashMap<String, String> alias;
	private List<Group> userGroups;

	public User(String username, String password) {
		this.userName = username;
		this.hashPassword = password;
		this.userGroups = new ArrayList<>();
		this.alias = new HashMap<>();
	}

	public boolean login(String password) {
		String hashedInputPassword = hash(password);
		return this.getHashPassword().equals(hashedInputPassword);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public void setHashPassword(String password) {
		this.hashPassword = hash(password);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth() {

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String dateString = dateFormat.format(date);

		this.dateOfBirth = dateString;
	}

	private String hash(String password) {		
		TextService textService = new TextService();
		String hashed = textService.hashMD5(password);
		
		return hashed;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return this.firstName;
	}

	@Override
	public long getReceiverId() {
		return getId();
	}

	public List<Group> getGroups() {
		return userGroups;
	}

	public void addGroup(Group group) {
		if (getPositionInGroupOrDefault(group) != -1) {
			userGroups.add(group);
		}
	}

	public boolean leaveGroup(Group group) {
		if (getPositionInGroupOrDefault(group) != -1) {
			return false;
		}
		
		for (int index = 0; index < userGroups.size(); index++) {
			if (userGroups.get(index).getId() == group.getId()) {
				userGroups.remove(index);
			}
		}
		return true;
	}

	private int getPositionInGroupOrDefault(Group group) {
		List<User> groupMembers = group.getMembers();		
		int postition = -1;
		
		for (int index = 0; index < group.getMembers().size(); index++) {
			User member = groupMembers.get(index);
			
			if (member.getId() == getId()) {
				postition = index;
			}
		}
		return postition;
	}

	public String getUserAliasOrDefault(User otherUser) {
		String userAlias = this.getUserName();
		
		if (alias.containsKey(otherUser.getUserName())) {
			userAlias = alias.get(otherUser.getUserName());
		}
		return userAlias;
	}

	public void addAlias(String assignorUserName, String assigneeCodeName) {
		alias.put(assignorUserName, assigneeCodeName);
	}

}
