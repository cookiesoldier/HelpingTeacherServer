package helper;

import daos.UserDAO;
import daos.interfaces.IUserDAO;
import dtos.UserDTO;

public class DataInit {
	
	IUserDAO userDAO;
	
	public DataInit(IUserDAO userDAO2){
		this.userDAO = userDAO2;
		UserDTO user = new UserDTO("test", "test");
		userDAO.createUser(user);
		UserDTO user1 = new UserDTO("test1", "test1");
		UserDTO user2 = new UserDTO("martin", "12345");
		userDAO.createUser(user1);
		//userDAO.createUser(user2);
		
		System.out.println(userDAO.updateUser(user, user2));
		System.out.println(userDAO.getUser(user2));
		System.out.println(userDAO.getUser(user));
		
	}

}
