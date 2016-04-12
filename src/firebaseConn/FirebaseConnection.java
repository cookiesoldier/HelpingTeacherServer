package firebaseConn;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import com.firebase.client.Firebase;

public class FirebaseConnection implements IFirebaseConnection {
	
	String firebaseUrl = "https://helpingteach.firebaseio.com/";
	Firebase firebaseRef = new Firebase(firebaseUrl);
	

	@Override
	public boolean createUser(JSONObject user) {
		Firebase ref = firebaseRef.child("users");
		
		Map<String,Object> userInfo = new HashMap<String,Object>();
		userInfo.put("PASSWORD", user.get("PASSWORD"));
//		userInfo.put("FIRSTNAME", user.get("FIRSTNAE"));
//		userInfo.put("LASTNAME", user.get("LASTNAME"));
//		userInfo.put("EMAIL",user.get("EMAIL"));
		
		userInfo.put("FIRSTNAME", "");
		userInfo.put("LASTNAME", "");
		userInfo.put("EMAIL","");
		Map<String, Object> users = new HashMap<String, Object>();
		users.put(user.get("USERNAME").toString(), userInfo);
		
		
		System.out.println(users);
		ref.setValue(users);
		
		
		
		return false;
	}



	@Override
	public boolean authUser(JSONObject user) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
