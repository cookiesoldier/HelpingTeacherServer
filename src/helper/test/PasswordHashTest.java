package helper.test;

import dtos.UserDTO;
import dtos.userdb.db.UserList;
import firebaseConn.IUserDatabase;
import helper.Password;

public class PasswordHashTest {
	
	
	public static void main (String[] args) {
		IUserDatabase users = new UserList();
		String username;
		String password;
		String hashedPassword = "der skete en fejl";
		
		username = "Tester4";
		password = "testpass4";
		try {
			hashedPassword = Password.getSaltedHash(password);
			UserDTO user = new UserDTO(username, hashedPassword);
			users.createUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(
				"username: "+ username+"\n"
				+ "password: "+ password + "\n"
				+ "hashedPassword: "+ hashedPassword
		);
		
		try {
			((UserList) users).printList();
			UserDTO user = users.getUser(username);
			String name = user.getUsername();
			System.out.println("name: "+name);
			String hashedPass = users.getUser(username).getPassword();
			String jobstatus = (Password.check("testpass4", hashedPass))?
				"good" : "bad";
			System.out.println("Check status: "+jobstatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
	}

}
