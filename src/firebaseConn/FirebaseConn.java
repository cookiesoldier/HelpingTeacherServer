package firebaseConn;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;

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
	

	public static FirebaseResponse firebaseCreateUser(JSONObject recievedData) throws UnsupportedEncodingException, JacksonUtilityException, FirebaseException {
		
		synchronized (firebase){
		FirebaseResponse response = null;
		//User map --- sub map
		Map<String, Object> userMap = firebase.get("USERS").getBody();
		//checker om brugeren findes allerede
		if(userMap.get(recievedData.get("USERNAME")) == null){
		//UserInfo map --- Sub sub Map
		Map<String, Object> userInfoMap = new LinkedHashMap<String, Object>();
		
		userInfoMap.put("PASSWORD", recievedData.get("PASSONE"));
		userInfoMap.put("EMAIL", "NULL");
		userInfoMap.put("FIRSTNAME", "NULL");
		userInfoMap.put("LASTNAME", "NULL");
		//Tilføj RecievedData til userMap
		
		userMap.put((String) recievedData.get("USERNAME"), userInfoMap);
		
		
		//check if exists
		
		System.out.println("usermapGet:"+userMap.get(recievedData.get("USERNAME")));
		
	
			System.out.println("Updating");
			response = firebase.put("USERS", userMap);
		}else{
			response = new FirebaseResponse(false, 404, null, null);
		}
		
		
		return response;
		}
		
		
	}
	
	
	
	private static boolean checkUserExist(String string) throws UnsupportedEncodingException, FirebaseException {
		String Userpath = "USERS";
		FirebaseResponse response = null;
		
		response = firebase.get(Userpath);
		
		//System.out.println("checkusers" +response.toString());
		return false;
	}
	
	private static void getUsers(){
		
		
		
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

}
