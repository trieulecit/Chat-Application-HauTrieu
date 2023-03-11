package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
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

	UserService userService;

	DataStorage storage;
	TextService textService;
	GroupService groupService;

	@BeforeEach
	void setUp() throws Exception {
		storage = InMemoryDataStorage.getInstance();
		userService = new UserService(storage);
		textService = new TextService();
		groupService = new GroupService(storage);
	}

	@AfterEach
	void clear() {
		storage.getGroups().deleteAll();
		storage.getUsers().deleteAll();
	}

	@Test
	void testCreateGroup() {
		groupService.createGroup("CSE 422", true);
		assertNotNull(groupService.getGroup("CSE 422"));
	}

	@Test
	void testJoinGroup() {
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");

		testAdmin = storage.getUsers().getFirst(user -> user.getUsername().equals("Phuc Nguyen"));
		testUser = storage.getUsers().getFirst(user -> user.getUsername().equals("Hawk Fang"));

		groupService.createGroup("CSE 423", false);
		assertTrue(groupService.joinGroup("CSE 423", testUser));
		assertEquals(true, testUser.getGroups().size() > 0);
	}

	@Test
	void testJoinPrivateGroup() {
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");

		testAdmin = storage.getUsers().getFirst(user -> user.getUsername().equals("Phuc Nguyen"));
		testUser = storage.getUsers().getFirst(user -> user.getUsername().equals("Hawk Fang"));

		groupService.createGroup("CSE 423", true);
		assertFalse(groupService.joinGroup("CSE 423", testUser));
		assertEquals(false, testUser.getGroups().size() > 0);
	}

	@Test
	void testKick() {
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");

		testAdmin = storage.getUsers().getFirst(user -> user.getUsername().equals("Phuc Nguyen"));
		testUser = storage.getUsers().getFirst(user -> user.getUsername().equals("Hawk Fang"));

		groupService.createGroup("CSE 423", true);
		testGroup = groupService.getGroup("CSE 423");

		groupService.privateGroupAdminPromote(testAdmin, testGroup);
		groupService.addMember(testUser, testGroup);

		assertTrue(groupService.kickMember(testAdmin, testUser, testGroup));

	}

	@Test
	void testKickPublicGroup() {
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");

		testAdmin = storage.getUsers().getFirst(user -> user.getUsername().equals("Phuc Nguyen"));
		testUser = storage.getUsers().getFirst(user -> user.getUsername().equals("Hawk Fang"));

		groupService.createGroup("CSE 422", false);
		groupService.joinGroup("CSE 422", testUser);
		groupService.joinGroup("CSE 422", testAdmin);

		testGroup = groupService.getGroup("CSE 422");

		assertFalse(groupService.kickMember(testAdmin, testUser, testGroup));

	}

	@Test
	void testPromoteNewAdmin() {
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");

		testAdmin = storage.getUsers().getFirst(user -> user.getUsername().equals("Phuc Nguyen"));
		testUser = storage.getUsers().getFirst(user -> user.getUsername().equals("Hawk Fang"));

		groupService.createGroup("CSE 422", true);
		testGroup = groupService.getGroup("CSE 422");
		groupService.privateGroupAdminPromote(testAdmin, testGroup);

		// test user become admin
		groupService.promoteAdmin(testAdmin, testUser, testGroup);

		assertTrue(groupService.kickMember(testUser, testAdmin, testGroup));
		assertEquals(testUser.getId(), testGroup.getAdmin().getId());
	}

	@Test
	void testLeaveGroup() {
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");

		testAdmin = storage.getUsers().getFirst(user -> user.getUsername().equals("Phuc Nguyen"));
		testUser = storage.getUsers().getFirst(user -> user.getUsername().equals("Hawk Fang"));

		groupService.createGroup("CSE 422", false);
		groupService.joinGroup("CSE 422", testAdmin);
		groupService.joinGroup("CSE 422", testUser);

		testGroup = groupService.getGroup("CSE 422");

		groupService.leaveGroup(testUser, testGroup);
		assertEquals(1, testGroup.getMembers().size());
	}

}