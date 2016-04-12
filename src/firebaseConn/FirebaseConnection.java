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
