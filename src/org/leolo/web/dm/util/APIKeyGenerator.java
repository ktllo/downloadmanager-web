package org.leolo.web.dm.util;

import java.security.SecureRandom;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APIKeyGenerator {
	
	private static Logger log = LoggerFactory.getLogger(APIKeyGenerator.class);
	private static Random random = new SecureRandom();
	public static final int DEFAULT_LENGTH = 48;
	public static final char [] AVAILABLE_CHARS = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM-_".toCharArray();

	public static String generateApiKey() {
		return generateApiKey(DEFAULT_LENGTH);
	}
	public static String generateApiKey(final int LENGTH) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<LENGTH;i++) {
			sb.append(AVAILABLE_CHARS[random.nextInt(AVAILABLE_CHARS.length)]);
		}
		return sb.toString();
	}
}
