package com.hautrieu.chat.domains;


import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;
import com.hautrieu.chat.services.TextService;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Base64;
import java.util.Calendar;

public class User extends BaseEntity implements MessageReceivable {
	private String username;
	private String lastName;
	private String firstName;
	private String hashPassword;
	private String gender;
	private String dateOfBirth;
	
	public User(String username, String password) {
		super(generateId());
		this.username = username;
		this.hashPassword = password;
	}
	public User(long id, String lastName, String firstName, String hashPassword, String gender, String dob) {
		super(id);
		this.lastName = lastName;
		this.firstName = firstName;
		this.hashPassword = hashPassword;
		this.gender = gender;
		this.dateOfBirth = dob;
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
	
	public static long generateId() {
		DataStorage storage = InMemoryDataStorage.getInstance();
		long size = storage.getUsers().getSize();		
		return size + 1;
	}

}
