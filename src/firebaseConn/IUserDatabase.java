package firebaseConn;

import org.json.simple.JSONObject;

import dtos.UserDTO;

public interface IUserDatabase {
	
	boolean createUser(UserDTO user);
	boolean authUser(String username, String password);
	UserDTO getUser(String name);
	JSONObject userToJSON(UserDTO user);
	UserDTO JSONtoUser(JSONObject obj);
	boolean isJSONObjectUser(JSONObject obj);
	
}
