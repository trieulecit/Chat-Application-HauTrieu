package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
		storage.getFiles().deleteAll();
		file = new InMemoryFile(null);
	}
	
	
	@AfterEach
	void clearThroughAllTestCases() {
		for(InMemoryFile file: storage.getFiles().toList()) {
			file.delete();
		}
	}
	
	@Test
	void testUpload() {
		File ioFile = new File("src/test/files/UploadText.txt");
		file = file.upload("txt", ioFile.toPath());
		System.out.println(file.getFullFileName());
		assertEquals(1, storage.getFiles().getSize());
		
	}
	
	@Test
	void testOpen() {
		File ioFile = new File("src/test/files/UploadText.txt");
		file = file.upload("txt", ioFile.toPath());
		assertDoesNotThrow(() -> file.open());
	}
	
	@Test
	void testDelete() {
		File ioFile = new File("src/test/files/UploadText.txt");
		file = file.upload("txt", ioFile.toPath());
		assertTrue(file.delete());
	}
		

}
