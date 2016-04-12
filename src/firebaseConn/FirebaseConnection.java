
package firebaseConn;

import org.json.simple.JSONObject;

import com.firebase.client.Firebase;

import dtos.UserDTO;

public class FirebaseConnection implements IFirebaseConnection {

	Firebase ref;

	public FirebaseConnection() {
		ref = new Firebase("http://localhost:8080/HelpingTeacherServer2/HTSservlet");
	}

	@Override
	public boolean createUser(UserDTO user) {
		// TODO Auto-generated method stub
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
		JSONObject obj = new JSONObject();
		obj.put("username", user.getUsername());
		obj.put("email", user.getEmail());
		obj.put("password", user.getPassword());
		obj.put("firstname", user.getFirstname());
		obj.put("lastname", user.getLastname());
		return obj;
	}

	@Override
	public UserDTO JSONtoUser(JSONObject obj) {
		UserDTO user;
		String username = obj.get("username").toString();
		String email = obj.get("email").toString();
		String password = obj.get("password").toString();
		String firstname = obj.get("firstname").toString();
		String lastname = obj.get("lastname").toString();
		user = new UserDTO(username, email, firstname, lastname, password);
		return user;
	}

	@Override
	public boolean isJSONObjectUser(JSONObject obj) {
		if(obj.size() == 5) {
			if(!obj.containsKey("username")) return false;
			if(!obj.containsKey("email")) return false;
			if(!obj.containsKey("password")) return false;
			if(!obj.containsKey("firstname")) return false;
			if(!obj.containsKey("lastname")) return false;
		} else return false;
		return true;
	}


}
