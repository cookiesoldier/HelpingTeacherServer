package firebaseConn.test;

import dtos.UserDTO;
import firebaseConn.FirebaseConnection;
import firebaseConn.IUserDatabase;

public class TestMain extends Thread {

	public static void main(String[] args) {
		IUserDatabase  conn = new FirebaseConnection();
		
		UserDTO martin = conn.getUser("Martin23777");
		
		
	}

}
