package com.hau_trieu.chat.domains;

import java.security.MessageDigest;
import java.util.Base64;

public class User {
	private int id;
	private String lastName;
	private String firstName;
	private String fullName;
	private String hashPassword;
	private String gender;
	private String dateOfBirth;

	public User(String lastName, String firstName, String hashPassword, String gender, String dob) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		fullName = lastName + " " + firstName;
		this.hashPassword = hashPassword;
		this.gender = gender;
		this.dateOfBirth = dob;
	}

	public User() {

	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return fullName;
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public void setHashPassword(String password) {
		this.hashPassword = hashPassword(password);
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

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

//	password hashing, String in, hashed password out
	public String hashPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(hash);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}



}
