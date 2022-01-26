package test.org.leolo.web.dm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.leolo.web.dm.util.APIKeyGenerator;

class APIGeneratorTester {

	@Test
	void test() {
		String key1 = APIKeyGenerator.generateApiKey();
		String key2 = APIKeyGenerator.generateApiKey();
		System.out.println("Key 1:"+key1);
		System.out.println("Key 2:"+key2);
		assertNotEquals(key1, key2);
	}

}
