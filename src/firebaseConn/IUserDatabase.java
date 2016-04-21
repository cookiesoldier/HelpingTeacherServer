package firebaseConn;

import org.json.simple.JSONObject;

import dtos.AnswerDTO;
import dtos.EventDTO;
import dtos.QuestionDTO;
import dtos.RoomDTO;
import dtos.UserDTO;

public interface IUserDatabase {

	boolean authUser(String username, String password);
	UserDTO getUser(String name);
	JSONObject userToJSON(UserDTO user);
	UserDTO JSONtoUser(JSONObject obj);
	boolean isJSONObjectUser(JSONObject obj);
	
	

	boolean createUser(UserDTO user);
	boolean createAnswer(AnswerDTO answer);
	boolean createEvent(EventDTO event);
	boolean createQuestion(QuestionDTO question);
	boolean createRoom(RoomDTO room);
	
	
	
	boolean deleteRoom(RoomDTO room);
	boolean deleteEvent(EventDTO event);
	boolean deleteQuestion(QuestionDTO question);
	boolean deleteAnswers(AnswerDTO answer);
	boolean deleteUser(UserDTO user);
	
	boolean updateRoom(RoomDTO room);
	boolean updateEvent(EventDTO event);
	boolean updateQuestion(QuestionDTO question);
	boolean updateAnswers(AnswerDTO answer);
	boolean updateUser(UserDTO user);
	
	boolean getRoom(RoomDTO room);
	boolean getEvent(EventDTO event);
	boolean getQuestion(QuestionDTO question);
	boolean getAnswers(AnswerDTO answer);
	boolean getUser(UserDTO user);
}
