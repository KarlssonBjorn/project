package tests.security;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

import security.PasswordHasher;

class PasswordHasherUnitTester {

	PasswordHasher puh = new PasswordHasher();
	
	private String password = "testingtesting";
	
	@Test
	void passwordHasherTest() throws NoSuchAlgorithmException {
		String hashedPassword = puh.hashPassword(password);
		
		assertEquals(puh.hashPassword(password), hashedPassword);
		System.out.println(hashedPassword);
	}

}
