package com.hautrieu.chat.domains;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;
import com.hautrieu.chat.services.TextService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Calendar;

public class User extends BaseEntity implements MessageReceivable {
	
	private final DataStorage storage;
	
	private String username;
	private String lastName;
	private String firstName;
	private String hashPassword;
	private String gender;
	private String dateOfBirth;
	
	public User(String username, String password) {
		storage = InMemoryDataStorage.getInstance();
		this.setId(storage.getUsers().getNextId());
		this.username = username;
		this.hashPassword = password;
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

	public String hash(String password) {
		TextService textService = new TextService();
		String hashed = textService.hashMD5(password);
        return hashed;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return this.firstName;
	}
	
	@Override
	public long getReceiverId() {
		return getId();
	}

}
