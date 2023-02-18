package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hautrieu.chat.domains.User;
import com.hautrieu.chat.services.UserService;

class UserServiceTest {
	User testUser = new User();
	UserService service = new UserService();

	@BeforeEach
	void setUp() throws Exception {
		testUser.setUsername("ola");
		testUser.setHashPassword("123456");
		testUser.setFirstName("Hau");
		testUser.setLastName(" Fang");
		service.create(testUser);

	}

	@DisplayName("Sign up test")
	@Test
	void test() {
		User user = new User();
		service.create(user);
		service.create(user);
		service.create(user);

		assertTrue(service.getUserList().size() == 4);
	}

	@DisplayName("Log in test")
	@Test
	void test2() {
		service.login("ola", "123456");
		assertTrue(service.getCurrentUser() != null);
	}

	@DisplayName("Hash Test")
	@Test
	void test3() {
		String nonHash = "123456";
		
		String expected = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";
		
		String actual = testUser.passwordHash(nonHash);
		assertTrue(expected.equals(actual));
		
	}

}
