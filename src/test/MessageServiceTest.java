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
import com.hautrieu.chat.domains.Group;
import com.hautrieu.chat.domains.Message;
import com.hautrieu.chat.domains.User;
import com.hautrieu.chat.services.MessageService;

public class MessageServiceTest {
	
	private DataStorage storage;
	private MessageService service;
	
	@BeforeEach
	void setUp() {
		storage = InMemoryDataStorage.getInstance();
		service = new MessageService(storage);
	}
	
	@AfterEach
	void clearData() {
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
		
		service.send(firstUser, secondUser, "This is content of message", null);
		service.send(firstUser, secondUser, "This is content of message but for group", null);
		
		Message firstMessage = storage.getMessages().getFirst(element -> element.getContent().equals("This is content of message"));
		Message secondMessage = storage.getMessages().getFirst(element -> element.getContent().equals("This is content of message but for group"));

		assertNotNull(firstMessage);		
		assertNotNull(secondMessage);
	}
}
