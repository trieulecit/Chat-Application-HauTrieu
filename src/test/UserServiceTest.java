package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;
import com.hautrieu.chat.domains.User;
import com.hautrieu.chat.repositories.Repository;
import com.hautrieu.chat.services.TextService;
import com.hautrieu.chat.services.UserService;

class UserServiceTest {
	
	User testUser;
	UserService service;
	DataStorage dataStorage;
	TextService textService;

	@BeforeEach
	void setUp() throws Exception {
		
		dataStorage = InMemoryDataStorage.getInstance();
		dataStorage.getGroups().deleteAll();
		dataStorage.getUsers().deleteAll();
		service = new UserService(dataStorage);
		textService = new TextService();
	}

	@DisplayName("Sign up test")
	@Test
	void testSignUp() {
		
		assertTrue(service.addUser("trieu", "trieu"));
		assertTrue(service.addUser("hau", "hau"));				
	}

	@DisplayName("Log in test")
	@Test
	void testLogin() {
		
		service.addUser("hau", "admin");
		assertTrue(service.login("hau", "admin"));
	}

	@DisplayName("Hash Test")
	@Test
	void testHashing() {
		
		String nonHash = "123456";
		String expected = "e10adc3949ba59abbe56e057f20f883e";
		String actual = textService.hashMD5(nonHash);
		
		assertEquals(expected, actual);
	}

	@DisplayName("Get All Users Matching a string")
	@Test
	void testGetAllUsersMatching() {

		Repository<User> users = dataStorage.getUsers();
		
		seedUser("somebody", "somebody", users);
		seedUser("trieu", "trieu", users);

		List<User> usersMatched = service.getUsers("Trieu Le Hoang");

		assertEquals(2, usersMatched.size());
	}
	
	private User seedUser(String username, String password, Repository<User> users) {
		
		service.addUser(username, password);
		
		User user = users.getFirst(userItem -> userItem.getUsername().equals(username));
		
		user.setFirstName("Trieu");
		user.setLastName("Le Hoang");
		
		return user;
	}

}
