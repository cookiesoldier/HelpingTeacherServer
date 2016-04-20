
package firebaseConn;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.json.simple.JSONObject;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import dtos.AnswerDTO;
import dtos.EventDTO;
import dtos.QuestionDTO;
import dtos.RoomDTO;
import dtos.RoomUserDTO;
import dtos.UserDTO;
import log.LogMethods;

public class FirebaseConnection implements IFirebaseConnection {
	LogMethods logger = new LogMethods();
	Firebase ref;

	public FirebaseConnection() {
		ref = new Firebase("https://helpingteach.firebaseio.com/");
	}

	public boolean createAnswer(AnswerDTO answer) {
		Firebase answerRef = ref.child("answers");

		Map<String, Object> answerInfoMap = new HashMap<String, Object>();
		answerInfoMap.put("message", answer.getMessage());
		answerInfoMap.put("poster", answer.getPoster());
		answerInfoMap.put("timestamp", answer.getTimestamp());
		Map<String, Object> answers = new HashMap<String, Object>();
		answers.put(answer.getAnswerKey(), answerInfoMap);

		// check if user exists
		final AtomicBoolean done = new AtomicBoolean(false);
		final AtomicBoolean succes = new AtomicBoolean(false);

		answerRef.child(answer.getAnswerKey()).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {

				if (!snapshot.hasChildren()) {
					answerRef.updateChildren(answers);

					succes.set(true);
					done.set(true);
					logger.printLog("CreationSucces answer created:" + answer.getAnswerKey());

				} else {
					// System.out.println("found one name:"+user.getUsername());
					succes.set(false);
					done.set(true);
					logger.printLog("CreationFailed answer not created:" + answer.getAnswerKey());

				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {

				logger.printLog("Firebase error:" + firebaseError.getMessage());
				succes.set(false);
				done.set(true);
			}
		});
		while (!done.get()) {
			// http://stackoverflow.com/questions/26092632/java-firebase-delay-exit-until-writes-finish
			// derp derp sikke en løsning!
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return succes.get();

	}

	public boolean createEvent(EventDTO event) {

		Firebase eventRef = ref.child("events");

		Map<String, Object> eventInfoMap = new HashMap<String, Object>();
		eventInfoMap.put("name", event.getName());
		eventInfoMap.put("questionkeys", event.getQuestionKeys());
		eventInfoMap.put("timestamp", event.getTimestamp());
		Map<String, Object> events = new HashMap<String, Object>();
		events.put(event.getEventKey(), eventInfoMap);

		// check event exists
		final AtomicBoolean done = new AtomicBoolean(false);
		final AtomicBoolean succes = new AtomicBoolean(false);

		eventRef.child(event.getEventKey()).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {

				if (!snapshot.hasChildren()) {
					eventRef.updateChildren(events);

					succes.set(true);
					done.set(true);
					logger.printLog("CreationSucces event created:" + event.getEventKey());

				} else {
					// System.out.println("found one name:"+user.getUsername());
					succes.set(false);
					done.set(true);
					logger.printLog("CreationFailed event not created:" + event.getEventKey());

				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {

				logger.printLog("Firebase error:" + firebaseError.getMessage());
				succes.set(false);
				done.set(true);
			}
		});
		while (!done.get()) {
			// http://stackoverflow.com/questions/26092632/java-firebase-delay-exit-until-writes-finish
			// derp derp sikke en løsning!
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return succes.get();

	}

	public boolean createQuestion(QuestionDTO question) {

		Firebase questionRef = ref.child("questions");
		Map<String, Object> questionInfoMap = new HashMap<String, Object>();
		questionInfoMap.put("answerkeys", question.getAnswerKeys());
		questionInfoMap.put("message", question.getMessage());
		questionInfoMap.put("timestamp", question.getTimestamp());
		Map<String, Object> questions = new HashMap<String, Object>();
		questions.put(question.getQuestionKey(), questionInfoMap);

		// check if question exists
		final AtomicBoolean done = new AtomicBoolean(false);
		final AtomicBoolean succes = new AtomicBoolean(false);

		questionRef.child(question.getQuestionKey()).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {

				if (!snapshot.hasChildren()) {
					questionRef.updateChildren(questions);

					succes.set(true);
					done.set(true);
					logger.printLog("CreationSucces question created:" + question.getQuestionKey());

				} else {
					// System.out.println("found one name:"+user.getUsername());
					succes.set(false);
					done.set(true);
					logger.printLog("CreationFailed question not created:" + question.getQuestionKey());

				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {

				logger.printLog("Firebase error:" + firebaseError.getMessage());
				succes.set(false);
				done.set(true);
			}
		});
		while (!done.get()) {
			// http://stackoverflow.com/questions/26092632/java-firebase-delay-exit-until-writes-finish
			// derp derp sikke en løsning!
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return succes.get();

	}

	public boolean createRoom(RoomDTO room) {

		Firebase roomRef = ref.child("rooms");

		Map<String, Object> roomInfoMap = new HashMap<String, Object>();
		roomInfoMap.put("owner", room.getOwner());
		roomInfoMap.put("eventkeys", room.getEventKeys());
		roomInfoMap.put("type", room.getType());
		Map<String, Object> rooms = new HashMap<String, Object>();
		rooms.put(room.getRoomKey(), roomInfoMap);

		// check if room exists
		final AtomicBoolean done = new AtomicBoolean(false);
		final AtomicBoolean succes = new AtomicBoolean(false);

		roomRef.child(room.getRoomKey()).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {

				if (!snapshot.hasChildren()) {
					roomRef.updateChildren(rooms);
					succes.set(true);
					done.set(true);
					logger.printLog("CreationSucces room created:" + room.getRoomKey());

				} else {
					// System.out.println("found one name:"+user.getUsername());
					succes.set(false);
					done.set(true);
					logger.printLog("CreationFailed room no created:" + room.getRoomKey());

				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {

				logger.printLog("Firebase error:" + firebaseError.getMessage());
				succes.set(false);
				done.set(true);
			}
		});
		while (!done.get()) {
			// http://stackoverflow.com/questions/26092632/java-firebase-delay-exit-until-writes-finish
			// derp derp sikke en løsning!
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return succes.get();

	}

	public boolean createRoomUser( RoomUserDTO roomuser){
		
		Firebase roomuserRef = ref.child("roomusers");

		Map<String, Object> roomuserInfoMap = new HashMap<String, Object>();
		roomuserInfoMap.put("admin", roomuser.getAdmin());
		roomuserInfoMap.put("studerende", roomuser.getStuderende());
		roomuserInfoMap.put("vejleder", roomuser.getVejleder());
		Map<String, Object> roomusers = new HashMap<String, Object>();
		roomusers.put(roomuser.getUsername(), roomuserInfoMap);
		
		Map<String, Object> roomName = new HashMap<String, Object>();
		roomName.put(roomuser.getRoomName(), roomusers);
		// check if user exists
		final AtomicBoolean done = new AtomicBoolean(false);
		final AtomicBoolean succes = new AtomicBoolean(false);

		roomuserRef.child(roomuser.getRoomName()).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {

				if (!snapshot.hasChildren()) {
					roomuserRef.updateChildren(roomusers);

					succes.set(true);
					done.set(true);
					logger.printLog("CreationSucces room user created:" +roomuser.getRoomName()+ "::"+ roomuser.getUsername());

				} else {
					// System.out.println("found one name:"+user.getUsername());
					succes.set(false);
					done.set(true);
					logger.printLog("CreationFailed room user not created:" +roomuser.getRoomName()+ "::"+ roomuser.getUsername());

				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {

				logger.printLog("Firebase error:" + firebaseError.getMessage());
				succes.set(false);
				done.set(true);
			}
		});
		while (!done.get()) {
			// http://stackoverflow.com/questions/26092632/java-firebase-delay-exit-until-writes-finish
			// derp derp sikke en løsning!
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return succes.get();

	}
	
	@Override
	public boolean createUser(UserDTO user) {

		Firebase usersRef = ref.child("users");

		Map<String, Object> userInfoMap = new HashMap<String, Object>();
		userInfoMap.put("password", user.getPassword());
		userInfoMap.put("email", user.getEmail());
		userInfoMap.put("firstname", user.getFirstname());
		userInfoMap.put("lastname", user.getLastname());
		Map<String, Object> users = new HashMap<String, Object>();
		users.put(user.getUsername(), userInfoMap);

		// check if user exists
		final AtomicBoolean done = new AtomicBoolean(false);
		final AtomicBoolean succes = new AtomicBoolean(false);

		usersRef.child(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {

				if (!snapshot.hasChildren()) {
					usersRef.updateChildren(users);

					succes.set(true);
					done.set(true);
					logger.printLog("CreationSucces user created:" + user.getUsername());

				} else {
					// System.out.println("found one name:"+user.getUsername());
					succes.set(false);
					done.set(true);
					logger.printLog("CreationFailed user not created:" + user.getUsername());

				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {

				logger.printLog("Firebase error:" + firebaseError.getMessage());
				succes.set(false);
				done.set(true);
			}
		});
		while (!done.get()) {
			// http://stackoverflow.com/questions/26092632/java-firebase-delay-exit-until-writes-finish
			// derp derp sikke en løsning!
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return succes.get();

		// usersRef.setValue(users);
		// return false;
	}

	@Override
	public boolean authUser(UserDTO user) {

		Firebase usersRef = ref.child("users");

		final AtomicBoolean done = new AtomicBoolean(false);
		final AtomicBoolean succes = new AtomicBoolean(false);

		usersRef.child(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {

				if (!snapshot.hasChildren()) {

					// System.out.println("Someone tried to login with:
					// Login:"+user.getUsername()+ " Pass:"+user.getPassword());
					logger.printLog(
							"Failed to login with" + "Login:" + user.getUsername() + " Pass:" + user.getPassword());
					succes.set(false);
					done.set(true);

				} else {
					// check if login info matches:

					System.out.println(snapshot.child("password"));
					if (snapshot.child("password").getValue().equals(user.getPassword())) {
						// passwordMatch!
						logger.printLog("Succesfully logged in with" + "Login:" + user.getUsername() + " Pass:"
								+ user.getPassword());
						succes.set(true);
						done.set(true);

					} else {
						// password Mismatch!
						logger.printLog("Failed To login password mismatch!: " + "Login:" + user.getUsername()
								+ " Pass:" + user.getPassword());
						succes.set(false);
						done.set(true);
					}

				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {

				logger.printLog("Firebase error:" + firebaseError.getMessage());
				succes.set(false);
				done.set(true);
			}
		});
		while (!done.get()) {
			// http://stackoverflow.com/questions/26092632/java-firebase-delay-exit-until-writes-finish
			// derp derp sikke en løsning!
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		;

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
		if (obj.size() == 5) {
			if (!obj.containsKey("username"))
				return false;
			if (!obj.containsKey("email"))
				return false;
			if (!obj.containsKey("password"))
				return false;
			if (!obj.containsKey("firstname"))
				return false;
			if (!obj.containsKey("lastname"))
				return false;
		} else
			return false;
		return true;
	}

}
