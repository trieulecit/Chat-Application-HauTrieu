package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;

import com.hautrieu.chat.domains.InMemoryFile;

class InMemoryFileTest {

	private DataStorage storage;
	private InMemoryFile fileInMemory;
	private File fileInSystem;
	
	@BeforeEach
	void setUp() {
		storage = InMemoryDataStorage.getInstance();
		storage.getFiles().deleteAll();
		fileInMemory = new InMemoryFile(null);
		fileInSystem = new File("src/test/files/UploadText.txt");
		fileInMemory = fileInMemory.upload("txt", fileInSystem.toPath());
	}
	
	
	@AfterEach
	void clearThroughAllTestCases() {
		for(InMemoryFile file: storage.getFiles().toList()) {
			file.delete();
		}
	}
	
	@Test
	void testUpload() {
		assertEquals(1, storage.getFiles().getSize());
	}
	
	@Test
	void testOpen() {
		assertDoesNotThrow(() -> fileInMemory.open());
	}
	
	@Test
	void testDelete() {
		assertTrue(fileInMemory.delete());
	}
		

}
