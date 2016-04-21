package daos.interfaces;

import dtos.UserDTO;

public interface IUserDAO {

	boolean updateUser(UserDTO user);
	boolean createUser(UserDTO user);
	boolean deleteUser(UserDTO user);
	boolean getUser(UserDTO user);
	boolean authUser(String username, String password);
	UserDTO getUser(String name);
	
}
