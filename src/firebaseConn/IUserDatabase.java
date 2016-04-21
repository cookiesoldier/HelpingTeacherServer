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
	
	


	
	
	
	
	boolean getRoom(RoomDTO room);
	boolean updateRoom(RoomDTO room);
	boolean createRoom(RoomDTO room);
	boolean deleteRoom(RoomDTO room);
	boolean updateEvent(EventDTO event);
	boolean createEvent(EventDTO event);
	boolean deleteEvent(EventDTO event);
	boolean getEvent(EventDTO event);
	boolean updateQuestion(QuestionDTO question);
	boolean createQuestion(QuestionDTO question);
	boolean deleteQuestion(QuestionDTO question);
	boolean getQuestion(QuestionDTO question);
	boolean updateAnswers(AnswerDTO answer);
	boolean createAnswer(AnswerDTO answer);
	boolean deleteAnswers(AnswerDTO answer);
	boolean getAnswers(AnswerDTO answer);
	boolean updateUser(UserDTO user);
	boolean createUser(UserDTO user);
	boolean deleteUser(UserDTO user);
	boolean getUser(UserDTO user);
	
	
}
