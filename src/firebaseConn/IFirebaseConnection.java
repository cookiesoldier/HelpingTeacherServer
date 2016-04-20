package firebaseConn;

import org.json.simple.JSONObject;

import dtos.AnswerDTO;
import dtos.EventDTO;
import dtos.QuestionDTO;
import dtos.RoomDTO;
import dtos.RoomUserDTO;
import dtos.UserDTO;

public interface IFirebaseConnection {
	
	
	boolean authUser(UserDTO user);
	UserDTO getUser(String name);
	JSONObject userToJSON(UserDTO user);
	UserDTO JSONtoUser(JSONObject obj);
	
	boolean createUser(UserDTO user);
	boolean isJSONObjectUser(JSONObject obj);
	boolean createAnswer(AnswerDTO answer);
	boolean createEvent(EventDTO event);
	boolean createQuestion(QuestionDTO question);
	boolean createRoom(RoomDTO room);
	boolean createRoomUser(RoomUserDTO roomuser);
	
	
	boolean deleteRoom(RoomDTO room);
	boolean deleteEvent(EventDTO event);
	boolean deleteQuestion(QuestionDTO question);
	boolean deleteAnswers(AnswerDTO answer);
	boolean deleteRoomUser(RoomUserDTO roomuser);
	boolean deleteUser(UserDTO user);
	
	boolean updateRoom(RoomDTO room);
	boolean updateEvent(EventDTO event);
	boolean updateQuestion(QuestionDTO question);
	boolean updateAnswers(AnswerDTO answer);
	boolean updateRoomUser(RoomUserDTO roomuser);
	boolean updateUser(UserDTO user);
	
	
	boolean getRoom(RoomDTO room);
	boolean getEvent(EventDTO event);
	boolean getQuestion(QuestionDTO question);
	boolean getAnswers(AnswerDTO answer);
	boolean getRoomUser(RoomUserDTO roomuser);
	boolean getUser(UserDTO user);
	
	
	
}
