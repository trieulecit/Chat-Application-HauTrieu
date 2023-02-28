package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;
import com.hautrieu.chat.domains.User;
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
		service = new UserService(dataStorage);
		textService = new TextService();
//		service.create(testUser);

	}

	@DisplayName("Sign up test")
	@Test
	void test() {
		assertTrue(service.addUser("hau", "admin"));
	}

	@DisplayName("Log in test")
	@Test
	void test2() {
		service.addUser("hau", "admin");
		assertTrue(service.login("hau", "admin"));
	}

	@DisplayName("Hash Test")
	@Test
	void test3() {
		
		String nonHash = "123456";
		String expected = "e10adc3949ba59abbe56e057f20f883e";
		String actual = textService.hashMD5(nonHash);
		assertEquals(expected, actual);

	}

}
