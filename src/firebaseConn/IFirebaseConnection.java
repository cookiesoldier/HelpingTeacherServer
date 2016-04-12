package firebaseConn;

import dtos.UserJSON;



public interface IFirebaseConnection {
	
	boolean createUser(UserJSON user);
	boolean authUser(UserJSON user);
	UserJSON getUser(String name);

}
