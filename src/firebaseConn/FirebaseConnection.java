package firebaseConn;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import com.firebase.client.Firebase;

import dtos.UserJSON;

public class FirebaseConnection implements IFirebaseConnection {
	
	String firebaseUrl = "https://helpingteach.firebaseio.com/";
	Firebase firebaseRef = new Firebase(firebaseUrl);
	
	
	@Override
	public boolean createUser(UserJSON user) {
	Firebase ref = firebaseRef.child("users");
		
		Map<String,Object> userInfo = new HashMap<String,Object>();
		userInfo.put("PASSWORD", user.getPassword());
		userInfo.put("FIRSTNAME", user.getFirstname());
		userInfo.put("LASTNAME", user.getLastname());
		userInfo.put("EMAIL",user.getEmail());
		
		
		Map<String, Object> users = new HashMap<String, Object>();
		users.put(user.get("USERNAME").toString(), userInfo);
		System.out.println(users);
		ref.setValue(users);
		return false;
	}
	@Override
	public boolean authUser(UserJSON user) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public UserJSON getUser(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	




}