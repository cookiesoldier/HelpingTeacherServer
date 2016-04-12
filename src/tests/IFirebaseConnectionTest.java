package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import firebaseConn.FirebaseConnection;
import firebaseConn.IFirebaseConnection;

public class IFirebaseConnectionTest {
	
	IFirebaseConnection firebase = new FirebaseConnection();
	
	
	@Test
	public void testCreateUser() {
		
	}

	@Test
	public void testAuthUser() {
		fail("Not yet implemented");
	}

}
