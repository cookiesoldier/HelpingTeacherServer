package tests;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import firebaseConn.FirebaseConnection;
import firebaseConn.IFirebaseConnection;

public class IFirebaseConnectionTest {
	
	IFirebaseConnection firebase = new FirebaseConnection();
	
	
	
	@Test
	public void testCreateUser() {
		JSONObject obj = new JSONObject();
       
            try {
				obj.put("TASK", "CREATEUSER");
				obj.put("USERNAME", "MartinOne");
				obj.put("PASSONE", "123");
				obj.put("PASSTWO", "123");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            firebase.createUser(obj);
      
		
	}

	@Test
	public void testAuthUser() {
		fail("Not yet implemented");
	}

}
