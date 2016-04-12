
package firebaseConn;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.firebase.client.Firebase;

import dtos.UserDTO;

public class FirebaseConnection implements IFirebaseConnection {
	
	Firebase ref;
	
	public FirebaseConnection() {
		ref = new Firebase("https://helpingteach.firebaseio.com/");
	}

	@Override
	public boolean createUser(UserDTO user) {
		
		Firebase usersRef = ref.child("users");
		
		Map<String, Object> userInfoMap = new HashMap<String,  Object>();
		userInfoMap.put("password", user.getPassword());
		userInfoMap.put("email", user.getEmail());
		userInfoMap.put("firstname", user.getFirstname());
		userInfoMap.put("lastname", user.getLastname());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		
		Map<String,  Object> users = new HashMap<String,  Object>();
		users.put(user.getUsername(), userInfoMap);
		
		//check if user exists
		usersRef.updateChildren(users);
		//usersRef.setValue(users);
		return false;
	}

	@Override
	public boolean authUser(UserDTO user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserDTO getUser(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject userToJSON(UserDTO user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO JSONtoUser(JSONObject obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isJSONObjectUser(JSONObject obj) {
		// TODO Auto-generated method stub
		return false;
	}


}
