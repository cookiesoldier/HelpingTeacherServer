
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
		
		final AtomicBoolean done = new AtomicBoolean(false);
		final AtomicBoolean succes = new AtomicBoolean(false);
		
		usersRef.child(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot snapshot) {
		    	
		    	if(!snapshot.hasChildren()){
					usersRef.updateChildren(users);
					System.out.println("didnt find one name:"+user.getUsername());
					succes.set(true);
					
					
					
				}else{
					System.out.println("found one name:"+user.getUsername());
					succes.set(false);
					done.set(true);
				
					
				}
		    }
		    @Override
		    public void onCancelled(FirebaseError firebaseError) {
		    }
		});
		while(!done.get()){
			//derp derp sikke en løsning!
		};
		
		return succes.get();
		
		
		
		//usersRef.setValue(users);
		//return false;
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
