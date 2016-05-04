package helper;

import java.util.HashMap;
import java.util.Random;

public class SessionMap {

	private HashMap<String, String> sessions = new HashMap<>();
	
	public HashMap<String, String> getSessions() {
		return sessions;
	}
	
	public void addSession(String username, String sessionKey) {
		if(sessions.containsKey(username)) return;
		sessions.put(username, sessionKey);
	}
	
	public String generateSessionKey() {
		String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // 36 letter.
		Random rand = new Random();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < 15; i++) {
			int randIndex = rand.nextInt(aToZ.length());
			res.append(aToZ.charAt(randIndex));
		}
		return res.toString();
	}

	public boolean sessionMapCheck(String username, String sessionKey) {
		String value = (String) sessions.get(username);
		return value.equals(sessionKey);
	}

}
