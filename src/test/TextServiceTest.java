package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hautrieu.chat.services.TextService;

class TextServiceTest {
	
	private TextService service;
	
	@BeforeEach
	void setUp() {
		service = new TextService();
	}

	@Test
	void testHashing() {
		assertEquals("202cb962ac59075b964b07152d234b70", service.hashByMD5("123"));
	}
}
