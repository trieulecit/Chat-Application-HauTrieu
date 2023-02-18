package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hautrieu.chat.services.TextService;

class TextServiceTest {
	TextService service = new TextService();
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		assertEquals("202cb962ac59075b964b07152d234b70", service.hashMD5("123"));
	}

}
