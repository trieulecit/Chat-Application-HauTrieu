package test;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;
import com.hautrieu.chat.domains.Group;
import com.hautrieu.chat.domains.InMemoryFile;
import com.hautrieu.chat.domains.Message;
import com.hautrieu.chat.domains.User;
import com.hautrieu.chat.services.GroupService;
import com.hautrieu.chat.services.MessageService;
import com.hautrieu.chat.services.UserService;

public class MessageServiceTest {
	
	private DataStorage storage;
	private MessageService messageService;
	private UserService userService;
	private GroupService groupService;
	
	@BeforeEach
	void setUp() {
		storage = InMemoryDataStorage.getInstance();
		messageService = new MessageService(storage);
		userService = new UserService(storage);
		groupService = new GroupService(storage);
		storage.getUsers().deleteAll();
		storage.getGroups().deleteAll();
		storage.getMessages().deleteAll();
	}
	
	@Test
	void testSendMessage() {
		User firstUser = new User("trieu", "trieu");
		User secondUser = new User("hau", "hau");
		Group group = new Group("CSE 422", false);
		
		storage.getUsers().add(firstUser);
		storage.getUsers().add(secondUser);
		storage.getGroups().add(group);
		
		messageService.send(firstUser, secondUser, "This is content of message", null);
		messageService.send(firstUser, secondUser, "This is content of message but for group", null);
		
		Message firstMessage = storage.getMessages().getFirst(element -> element.getContent().equals("This is content of message"));
		Message secondMessage = storage.getMessages().getFirst(element -> element.getContent().equals("This is content of message but for group"));

		assertNotNull(firstMessage);		
		assertNotNull(secondMessage);
	}
	
	@Test
	void testDeleteMessage() {
		userService.addUser("trieu", "trieu");
		userService.addUser("hau", "hau");
		User sender = storage.getUsers().getFirst(user -> user.getUsername().equals("trieu"));
		User receiver = storage.getUsers().getFirst(user -> user.getUsername().equals("hau"));
		List<InMemoryFile> files = new ArrayList<>();
		InMemoryFile file = new InMemoryFile("txt");
		file.upload("txt", Paths.get("C:\\Users\\Dell\\Desktop\\UploadText.txt"));
		files.add(file);
		Message message = messageService.send(sender, receiver, "Message from trieu to hau", files);	
		messageService.delete(message.getId());
		assertTrue(message.getDeleted());
	}
	
	@Test
	void testGetAllFiles() {
		userService.addUser("trieu", "trieu");
		groupService.createGroup("CSE422", false);
		
		User sender = storage.getUsers().getFirst(user -> user.getUsername().equals("trieu"));
		Group receiver = storage.getGroups().getFirst(group -> group.getName().equals("CSE422"));
		List<InMemoryFile> files = new ArrayList<>();
		InMemoryFile file = new InMemoryFile("txt");
		file.upload("txt", Paths.get("C:\\Users\\Dell\\Desktop\\UploadText.txt"));
		files.add(file);
		messageService.send(sender, receiver, "user to group", files);
		List<InMemoryFile> actualFiles = messageService.getAllFiles(sender, receiver);
		assertEquals(1, actualFiles.size());
		assertEquals("txt", actualFiles.get(0).getExtension());
		file.delete();
	}
	
	@Test
	void testGetTopLatestMessages() {
		userService.addUser("trieu", "trieu");
		groupService.createGroup("CSE422", false);
		
		User sender = storage.getUsers().getFirst(user -> user.getUsername().equals("trieu"));
		Group receiver = storage.getGroups().getFirst(group -> group.getName().equals("CSE422"));
		
		for(int index = 1; index <= 100; index++) {
			messageService.send(sender, receiver, String.valueOf(index), null);
		}
		
		List<Message> messages = messageService.getTopLatestMessages(sender, receiver, 3, 1);
		
		assertEquals("99", messages.get(0).getContent());
		assertEquals("98", messages.get(1).getContent());
		assertEquals("97", messages.get(2).getContent());
	}
	
	@Test
	void testGetMessagesContainKeyword() {
		userService.addUser("trieu", "trieu");
		groupService.createGroup("CSE422", false);
		
		User sender = storage.getUsers().getFirst(user -> user.getUsername().equals("trieu"));
		Group receiver = storage.getGroups().getFirst(group -> group.getName().equals("CSE422"));
		
		messageService.send(sender, receiver, "Exercise 1:", null);
		messageService.send(sender, receiver, "Exercise 2:", null);
		
		List<Message> messages = messageService.getMessagesContain(sender, receiver, "Exercise");
		
		assertEquals("Exercise 1:", messages.get(0).getContent());
		assertEquals("Exercise 2:", messages.get(1).getContent());
	}
}
