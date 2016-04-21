package daos.interfaces;

import dtos.UserDTO;

public interface IUserDAO {

	boolean createUser(UserDTO user);
	boolean deleteUser(UserDTO user);
	UserDTO getUser(UserDTO user);
	boolean authUser(String username, String password);
	UserDTO getUser(String name);
	boolean updateUser(UserDTO oldUser, UserDTO newUser);
	
}
