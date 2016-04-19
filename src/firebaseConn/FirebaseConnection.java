
package firebaseConn;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.json.simple.JSONObject;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import dtos.UserDTO;
import log.LogMethods;

public class FirebaseConnection implements IFirebaseConnection {
	LogMethods logger = new LogMethods();
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
		Map<String,  Object> users = new HashMap<String,  Object>();
		users.put(user.getUsername(), userInfoMap);
		
		//check if user exists
		final AtomicBoolean done = new AtomicBoolean(false);
		final AtomicBoolean succes = new AtomicBoolean(false);
		
		usersRef.child(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot snapshot) {
		    	
		    	if(!snapshot.hasChildren()){
					usersRef.updateChildren(users);
					//System.out.println("didnt find one name:"+user.getUsername());
					succes.set(true);
					done.set(true);
					logger.printLog("CreationSucces user created:" + user.getUsername());
					
					
					
				}else{
					//System.out.println("found one name:"+user.getUsername());
					succes.set(false);
					done.set(true);
					logger.printLog("CreationFailed user not created:" + user.getUsername());
					
				}
		    }
		    @Override
		    public void onCancelled(FirebaseError firebaseError) {
		    	
		    	logger.printLog("Firebase error:" + firebaseError.getMessage());
		    }
		});
		while(!done.get()){
			//http://stackoverflow.com/questions/26092632/java-firebase-delay-exit-until-writes-finish
			//derp derp sikke en løsning!
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return succes.get();
		
		
		
		//usersRef.setValue(users);
		//return false;
	}

	@Override
	public boolean authUser(UserDTO user) {
		
		Firebase usersRef = ref.child("users");
		
		
		final AtomicBoolean done = new AtomicBoolean(false);
		final AtomicBoolean succes = new AtomicBoolean(false);
		
		usersRef.child(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot snapshot) {
		    	
		    	if(!snapshot.hasChildren()){
		    		
		    
					//System.out.println("Someone tried to login with: Login:"+user.getUsername()+ " Pass:"+user.getPassword());
		    		logger.printLog("Failed to login with" + "Login:"+user.getUsername()+ " Pass:"+user.getPassword());
					succes.set(false);
					done.set(true);
					
					
				}else{
					//check if login info matches:
					
					System.out.println(snapshot.child("password"));
					if(snapshot.child("password").equals(user.getPassword())){
						//passwordMatch!
						logger.printLog("Succesfully logged in with" + "Login:"+user.getUsername()+ " Pass:"+user.getPassword());
						succes.set(true);
						done.set(true);
						
					}else{
						//password Mismatch!
						logger.printLog("Failed To login password mismatch!: " + "Login:"+user.getUsername()+ " Pass:"+user.getPassword());
						succes.set(false);
						done.set(true);
					}
					
				
					
				}
		    }
		    @Override
		    public void onCancelled(FirebaseError firebaseError) {
		    	
		    	logger.printLog("Firebase error:" + firebaseError.getMessage());
		    }
		});
		while(!done.get()){
			//http://stackoverflow.com/questions/26092632/java-firebase-delay-exit-until-writes-finish
			//derp derp sikke en løsning!
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		
		return succes.get();
		
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
