package firebaseConn;

import com.firebase.client.*;

public interface IFirebaseConnection {
	
	boolean createUser(JSONObject user);
	boolean authUser(JSONObjet user);
	

}
