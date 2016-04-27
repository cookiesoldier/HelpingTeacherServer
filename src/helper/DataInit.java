package helper;

import daos.UserDAO;
import daos.interfaces.IUserDAO;
import dtos.UserDTO;

public class DataInit {
	
	IUserDAO userDAO;
	
	public DataInit(IUserDAO userDAO2){
		this.userDAO = userDAO2;
		UserDTO user = new UserDTO("test", "test");
		userDAO2.createUser(user);
		UserDTO user1 = new UserDTO("test1", "test1");
		UserDTO user2 = new UserDTO("martin", "12345");
		userDAO2.createUser(user1);
		userDAO2.createUser(user2);
	}

}
