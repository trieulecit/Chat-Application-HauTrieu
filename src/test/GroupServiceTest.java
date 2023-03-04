package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;
import com.hautrieu.chat.domains.Group;
import com.hautrieu.chat.domains.User;
import com.hautrieu.chat.services.GroupService;
import com.hautrieu.chat.services.TextService;
import com.hautrieu.chat.services.UserService;

class GroupServiceTest {
	User testUser;
	User testAdmin;
	Group testGroup;

	UserService service;

	DataStorage dataStorage;
	TextService textService;
	GroupService groupService;

	@BeforeEach
	void setUp() throws Exception {
		dataStorage = InMemoryDataStorage.getInstance();
		service = new UserService(dataStorage);
		textService = new TextService();
		groupService = new GroupService(dataStorage);
//		service.create(testUser);

	}

	@Test
	void testCreateGroup() {
		groupService.createGroup("CSE 422", true);
		assertNotNull(groupService.getGroup("CSE 422"));
	}

	@Test
	void testJoinGroup() {
		assertTrue(groupService.joinGroup("CSE 422", testUser));
	}

	@Test
	void testKick() {
		testUser = new User("Phuc Nguyen", "123");
		testAdmin = new User("Hawk Phan", "123");
		
		groupService.createGroup("CSE 422", true);
		
		groupService.privateGroupAdminPromote(testAdmin, testGroup);

		testGroup = groupService.getGroup("CSE 422");
		
		assertTrue(groupService.kickMember(testAdmin, testUser, testGroup));

	}

}
