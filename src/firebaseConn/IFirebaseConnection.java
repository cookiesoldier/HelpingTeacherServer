package firebaseConn;

import org.json.simple.JSONObject;

import dtos.UserDTO;

public interface IFirebaseConnection {
	
	/**
	 * should store the given user in the Firebase database
	 * @param user user to be stored
	 * @return true if successful
	 */
	boolean createUser(UserDTO user);
	
	/**
	 * should authenticate a user using the Firebase database username and password
	 * @param user user to authenticate
	 * @return true if authenticated, false if not.
	 */
	boolean authUser(UserDTO user);
	
	/**
	 * should return a UserDTO with the values specified in the Firebase database
	 * @param username the username of the user we want to find
	 * @return should return a UserDTO if user is found. Otherwise return null
	 */
	UserDTO getUser(String username);
	
	/**
	 * should convert a UserDTO into an equivalent JSONObject
	 * @param user user to convert
	 * @return JSONObject that was created
	 */
	JSONObject userToJSON(UserDTO user);
	
	/**
	 * should convert a JSONObject to the equivalent UserDTO
	 * @param obj object to convert.
	 * @return returns the converted UserDTO. 
	 */
	UserDTO JSONtoUser(JSONObject obj);
	
	/**
	 * should check if a given JSONObject is applicable for conversion to UserDTO
	 * @param obj object to check
	 * @return true if the object contains same field as UserDTO. Otherwise false
	 */
	boolean isJSONObjectUser(JSONObject obj);
	
}
