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
import com.hautrieu.chat.services.UserService;

class GroupServiceTest {

	private DataStorage storage;
	private UserService userService;	
	private GroupService groupService;

	@BeforeEach
	void setUp() throws Exception {
		storage = InMemoryDataStorage.getInstance();
		userService = new UserService(storage);
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
		assertNotNull(groupService.getGroupByName("CSE 422"));
	}

	@Test
	void testJoinGroup() {
		
		groupService.createGroup("CSE 423", false);
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");

		User userForTesting = storage.getUsers().getFirst(user -> userService.compareCorrectUserName(user, "Hawk Fang"));
		
		
		assertTrue(groupService.joinGroup("CSE 423", userForTesting));
		assertEquals(1, userForTesting.getGroups().size());
	}

	@Test
	void testJoinPrivateGroup() {
		
		groupService.createGroup("CSE 423", true);
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");
		
		User userForTesting = storage.getUsers().getFirst(user -> userService.compareCorrectUserName(user, "Hawk Fang"));	
		
		assertFalse(groupService.joinGroup("CSE 423", userForTesting));
		assertEquals(0, userForTesting.getGroups().size());
	}

	@Test
	void testKick() {
		
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");

		User adminForTesting = storage.getUsers().getFirst(user -> userService.compareCorrectUserName(user, "Phuc Nguyen"));
		User userForTesting = storage.getUsers().getFirst(user -> userService.compareCorrectUserName(user, "Hawk Fang"));

		groupService.createGroup("CSE 423", true);
		Group testGroup = groupService.getGroupByName("CSE 423");
		groupService.setCreatorAsAdminInPrivateGroup(adminForTesting, testGroup);

		groupService.addMember(userForTesting, testGroup);

		assertTrue(groupService.kickMember(adminForTesting, userForTesting, testGroup));
	}

	@Test
	void testKickPublicGroup() {
		
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");

		User adminForTesting = storage.getUsers().getFirst(user -> userService.compareCorrectUserName(user, "Phuc Nguyen"));
		User userForTesting = storage.getUsers().getFirst(user -> userService.compareCorrectUserName(user, "Hawk Fang"));

		groupService.createGroup("CSE 422", false);
		groupService.joinGroup("CSE 422", userForTesting);
		groupService.joinGroup("CSE 422", adminForTesting);

		Group groupForTesting = groupService.getGroupByName("CSE 422");

		assertFalse(groupService.kickMember(adminForTesting, userForTesting, groupForTesting));
	}

	@Test
	void testPromoteNewAdmin() {
		
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");

		User adminforTesting = storage.getUsers().getFirst(user -> userService.compareCorrectUserName(user, "Phuc Nguyen"));
		User userForTesting = storage.getUsers().getFirst(user -> userService.compareCorrectUserName(user, "Hawk Fang"));

		groupService.createGroup("CSE 422", true);
		Group groupForTesting = groupService.getGroupByName("CSE 422");
		groupService.setCreatorAsAdminInPrivateGroup(adminforTesting, groupForTesting);

		groupService.promoteAdmin(adminforTesting, userForTesting, groupForTesting);

		assertTrue(groupService.kickMember(userForTesting, adminforTesting, groupForTesting));
		assertEquals(userForTesting.getId(), groupForTesting.getAdmin().getId());
	}

	@Test
	void testLeaveGroup() {
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");

		User adminforTesting = storage.getUsers().getFirst(user -> userService.compareCorrectUserName(user, "Phuc Nguyen"));
		User userForTesting = storage.getUsers().getFirst(user -> userService.compareCorrectUserName(user, "Hawk Fang"));

		groupService.createGroup("CSE 422", false);
		groupService.joinGroup("CSE 422", adminforTesting);
		groupService.joinGroup("CSE 422", userForTesting);

		Group groupForTesting = groupService.getGroupByName("CSE 422");

		assertTrue(groupService.leaveGroup(userForTesting, groupForTesting));
		assertEquals(1, groupForTesting.getMembers().size());
	}

	@Test
	void testGetUserGroups() {
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");

		User adminForTesting = storage.getUsers().getFirst(user -> user.getUserName().equals("Phuc Nguyen"));
		User userForTesting = storage.getUsers().getFirst(user -> user.getUserName().equals("Hawk Fang"));

		groupService.createGroup("CSE 422", false);
		groupService.createGroup("CSE 421", false);
		groupService.createGroup("CSE 420", false);

		groupService.joinGroup("CSE 422", adminForTesting);
		groupService.joinGroup("CSE 422", userForTesting);
		groupService.joinGroup("CSE 421", userForTesting);
		groupService.joinGroup("CSE 420", userForTesting);

		assertEquals(3, groupService.GetGroupsOfUser(userForTesting).size());

		Group groupForTesting = groupService.getGroupByName("CSE 422");

		groupService.leaveGroup(userForTesting, groupForTesting);

		assertEquals(2, groupService.GetGroupsOfUser(userForTesting).size());
	}

	@Test
	void testAlias() {
		
		groupService.createGroup("CSE 422", false);
		userService.addUser("Phuc Nguyen", "123");
		userService.addUser("Hawk Fang", "123");
		userService.addUser("Ngoc Suong", "123");

		User normalUserForTesting = storage.getUsers().getFirst(user -> user.getUserName().equals("Phuc Nguyen"));
		User aliasChanger = storage.getUsers().getFirst(user -> user.getUserName().equals("Hawk Fang"));
		User userToChangeAlias = storage.getUsers().getFirst(user -> user.getUserName().equals("Ngoc Suong"));
			
		groupService.joinGroup("CSE 422", normalUserForTesting);
		groupService.joinGroup("CSE 422", aliasChanger);
		groupService.joinGroup("CSE 422", userToChangeAlias);

		groupService.setAliasForUser(aliasChanger, userToChangeAlias, "Honey");

		assertEquals("Honey", userToChangeAlias.getUserAliasOrDefault(aliasChanger));
		assertEquals("Ngoc Suong", userToChangeAlias.getUserAliasOrDefault(normalUserForTesting));
	}

}