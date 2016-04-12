package firebaseConn;

import org.json.simple.JSONObject;



public interface IFirebaseConnection {
	
	boolean createUser(JSONObject user);
	boolean authUser(JSONObject user);
	

}
