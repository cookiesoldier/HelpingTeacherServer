package firebaseConn.test;

import dtos.UserDTO;
import firebaseConn.FirebaseConnection;
import firebaseConn.IFirebaseConnection;

public class TestMain extends Thread {

	public static void main(String[] args) {
		IFirebaseConnection conn = new FirebaseConnection();
		
		UserDTO martin = conn.getUser("Martin23777");
		
		
	}

}
