package firebaseConn;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;

//import com.firebase.client.Firebase;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

public class FirebaseConn {
	
	
	
	static String firebase_baseUrl ="https://helpingteach.firebaseio.com/";
	static Firebase firebase;
	

	static{
		try {
			firebase = new Firebase(firebase_baseUrl);
		} catch (FirebaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public static  String firebaseCreateUser(JSONObject recievedData) throws UnsupportedEncodingException, JacksonUtilityException, FirebaseException {
		
		System.out.println("Test1");
		FirebaseResponse response;
		//Users Map --- Top Map
		Map<String, Object> usersMap = new LinkedHashMap<String, Object>();
		//User map --- sub map
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		//need to generate USERID - must be unique
		//78.364.164.096 combinationer af user ID....
		String userID = generateUserID();
		System.out.println("Test2");
		//UserInfo map --- Sub sub Map
		Map<String, Object> userInfoMap = new LinkedHashMap<String, Object>();
		userInfoMap.put("USERNAME", recievedData.get("USERNAME"));
		userInfoMap.put("PASSWORD", recievedData.get("PASSONE"));
		userInfoMap.put("EMAIL", "NULL");
		userInfoMap.put("FIRSTNAME", "NULL");
		userInfoMap.put("LASTNAME", "NULL");
		//Tilf�j RecievedData til userMap
		System.out.println("Test3");
		
		userMap.put(userID, userInfoMap);
		usersMap.put("USERS", userMap);
		response = firebase.put("USERS", usersMap);
		
		
		System.out.println("Test4");
		System.out.println(response.toString());
		return response.toString();
		
		
	}
	
	
	
	public static String generateUserID(){
		String userID = "";
		//Generate a userID

	    Random r = new Random();

	    String alphabet = "123xyz";
	    for (int i = 0; i < 7; i++) {
	       
	        userID = userID + alphabet.charAt(r.nextInt(alphabet.length()));
	    }
		
		return userID;
		
	}
	
	public static FirebaseResponse loginUser(JSONObject user) {
		String username = user.get("username");
		String password = user.get("password");
		firebase.
	}

}
