
package firebaseConn;

import java.util.HashMap;
import java.util.Iterator;
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
			//derp derp sikke en l�sning!
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
	public UserDTO getUser(final String username) {
		final Firebase userRef = ref.child("users/"+username);
		final UserDTO user = new UserDTO();
		user.setUsername(username);
		System.out.println("userRef String: "+ userRef.toString());
		
		Runnable r = new Runnable() {
			volatile boolean done = false;
			volatile boolean success = false;
			@Override
			public void run() {
				userRef.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot snap) {
						// System.out.println(snap.getChildrenCount());
						Iterable<DataSnapshot> children = snap.getChildren();
						for(DataSnapshot ds : children) {
							String str = ds.getKey().toString();
							switch(str) {
							case "email": 
								user.setEmail(ds.getValue().toString());
								break;
							case "firstname":
								user.setFirstname(ds.getValue().toString());
								break;
							case "lastname":
								user.setLastname(ds.getValue().toString());
								break;
							case "password":
								user.setPassword(ds.getValue().toString());
								break;
							default:
								System.out.println("default: " + str);
							}
						}
						
						done = true;
						//System.out.println("Is now done");
						success = true;
					}	
					@Override
					public void onCancelled(FirebaseError err) {
						done = true;	
						System.out.println("An error occurred on Firebase");
					}
				});
				// int i = 0;
				while(!done) {
					try {
						Thread.sleep(200);
						// System.out.println("i er "+ i++);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				FirebaseConnection.class.notify();
				System.out.println(user.toString());
			}
		};
		
		new Thread(r).start();
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
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
