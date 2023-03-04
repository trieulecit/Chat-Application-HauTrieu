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
	}
	@AfterEach
	void clear() {
		dataStorage.getGroups().deleteAll();
	}

	@Test
	void testCreateGroup() {
		groupService.createGroup("CSE 422", true);
		assertNotNull(groupService.getGroup("CSE 422"));
	}

	@Test
	void testJoinGroup() {
		groupService.createGroup("CSE 423", false);
		assertTrue(groupService.joinGroup("CSE 423", testUser));
	}
	@Test
	void testJoinPrivateGroup() {
		groupService.createGroup("CSE 423", true);
		assertFalse(groupService.joinGroup("CSE 423", testUser));
	}

	@Test
	void testKick() {
		testUser = new User("Phuc Nguyen", "123");
		testAdmin = new User("Hawk Phan", "123");
		
		groupService.createGroup("CSE 423", true);
		testGroup = groupService.getGroup("CSE 423");
		
		groupService.privateGroupAdminPromote(testAdmin, testGroup);
		groupService.addMember(testUser, testGroup);
		
		System.out.println(testGroup.getMembers().size());
		
		assertTrue(groupService.kickMember(testAdmin, testUser, testGroup));

	}
	@Test
	void testKickPublicGroup() {
		testUser = new User("Phuc Nguyen", "123");
		testAdmin = new User("Hawk Phan", "123");
		
		groupService.createGroup("CSE 422", false);
		groupService.joinGroup("CSE 422", testUser);
		groupService.joinGroup("CSE 422", testAdmin);
		
		testGroup = groupService.getGroup("CSE 422");
		
		assertFalse(groupService.kickMember(testAdmin, testUser, testGroup));

	}
	@Test
	void testPromoteNewAdmin() {
		testUser = new User("Phuc Nguyen", "123");
		testAdmin = new User("Hawk Phan", "123");
		
		groupService.createGroup("CSE 422", true);
		testGroup = groupService.getGroup("CSE 422");
		groupService.privateGroupAdminPromote(testAdmin, testGroup);
		
		//test user become admin
		groupService.promoteAdmin(testAdmin, testUser, testGroup);
		
		assertTrue(groupService.kickMember(testUser, testAdmin, testGroup));
		
	}
	@Test
	void testLeaveGroup() {
		testUser = new User("Phuc Nguyen", "123");
		testAdmin = new User("Hawk Phan", "123");
		
		groupService.createGroup("CSE 422", false);
		groupService.joinGroup("CSE 422", testAdmin);
		groupService.joinGroup("CSE 422", testUser);		
		

		testGroup = groupService.getGroup("CSE 422");
		
		groupService.leaveGroup(testUser, testGroup);
		
		assertEquals(1, testGroup.getMembers().size());
	}
	
}
