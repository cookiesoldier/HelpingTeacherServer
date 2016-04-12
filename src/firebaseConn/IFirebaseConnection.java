package firebaseConn;

import org.json.simple.JSONObject;

import dtos.UserDTO;

public interface IFirebaseConnection {
	
	boolean createUser(UserDTO user);
	boolean authUser(UserDTO user);
	UserDTO getUser(String name);
	JSONObject userToJSON(UserDTO user);
	UserDTO JSONtoUser(JSONObject obj);
	boolean isJSONObjectUser(JSONObject obj);
	
}
