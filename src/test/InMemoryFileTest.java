package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;
import com.hautrieu.chat.domains.InMemoryFile;

class InMemoryFileTest {

	private DataStorage storage;
	private InMemoryFile file;
	
	@BeforeEach
	void setUp() {
		storage = InMemoryDataStorage.getInstance();
		file = new InMemoryFile(null);
	}
	
	@AfterEach
	void clearAll() {
		storage.getFiles().deleteAll();
	}
	
	@Test
	void testUpload() {
		file = file.upload("txt", Paths.get("C:\\Users\\Dell\\Desktop\\UploadText.txt"));
		System.out.println(file.getFullFileName());
		assertEquals(1, storage.getFiles().getSize());
		file.delete();
	}
	
	@Test
	void testOpen() {
		file = file.upload("txt", Paths.get("C:\\Users\\Dell\\Desktop\\UploadText.txt"));
		assertDoesNotThrow(() -> file.open());
		file.delete();
	}
	
	@Test
	void testDelete() {
		file = file.upload("txt", Paths.get("C:\\Users\\Dell\\Desktop\\UploadText.txt"));
		assertTrue(file.delete());
	}
		

}
