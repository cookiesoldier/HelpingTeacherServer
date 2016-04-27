package helper;

import java.util.ArrayList;
import java.util.List;

import daos.AnswerDAO;
import daos.UserDAO;
import daos.interfaces.IAnswerDAO;
import daos.interfaces.IEventDAO;
import daos.interfaces.IQuestionDAO;
import daos.interfaces.IRoomDAO;
import daos.interfaces.IUserDAO;
import dtos.AnswerDTO;
import dtos.EventDTO;
import dtos.QuestionDTO;
import dtos.RoomDTO;
import dtos.UserDTO;

public class DataInit {
	
	
	public DataInit(IUserDAO userDAO2, IRoomDAO roomDAO, IAnswerDAO answerDAO, IQuestionDAO questionDAO,IEventDAO eventDAO){
		
		LogMethods logger = new LogMethods();
	
		
	
		//userDAO.createUser(user2);
		AnswerDTO answerOne = new AnswerDTO("asdfghjklq12345", "Du henter de med get kald!", logger.timeStamp(), "martin");
		AnswerDTO answerTwo = new AnswerDTO("asprghjklq12375", "Du bruger nogle lister eller noget", logger.timeStamp(), "martin");
		AnswerDTO answerThree = new AnswerDTO("asplomhjklq123e", "man kan bruge frugt!", logger.timeStamp(), "martin");
		answerDAO.createAnswer(answerOne);
		answerDAO.createAnswer(answerTwo);
		answerDAO.createAnswer(answerThree);
		
		List<String> answerKeys1 = new ArrayList<>();
		answerKeys1.add(answerOne.getAnswerKey());
		QuestionDTO queOne = new QuestionDTO("Datahentning", "Hvordan henter man data, anyone?", logger.timeStamp(), "irj4ke93md95kt0","martin", answerKeys1);
		List<String> answerKeys2 = new ArrayList<>();
		answerKeys2.add(answerTwo.getAnswerKey());
		QuestionDTO queTwo = new QuestionDTO("Data visning", "Nogen gode ideer til at vise data??", logger.timeStamp(), "irj4kpeorkdikt0","thomas",answerKeys2);
		List<String> answerKeys3 = new ArrayList<>();
		answerKeys3.add(answerThree.getAnswerKey());
		QuestionDTO queThree = new QuestionDTO("tærtehjælp!", "Hvordan laver man tærte?", logger.timeStamp(), "irj4kkdjeodi777","peter", answerKeys3);
		questionDAO.createQuestion(queOne);
		questionDAO.createQuestion(queTwo);
		questionDAO.createQuestion(queThree);
		
		
		
		
		List<String> queKeys = new ArrayList<>();
		queKeys.add(queOne.getQuestionKey());
		queKeys.add(queTwo.getQuestionKey());
		EventDTO eventOne = new EventDTO("Computer og data", logger.timeStamp(), "oenzjs990274bew", queKeys);
		
		
		List<String> queKeys2 = new ArrayList<>();
		queKeys2.add(queThree.getQuestionKey());
		EventDTO eventTwo = new EventDTO("madlavning", logger.timeStamp(), "pdie94ie8m1g304", queKeys2);
		
		eventDAO.createEvent(eventOne);
		eventDAO.createEvent(eventTwo);
		
		
		
		
		
		List<String> eventKeys = new ArrayList<>();
		eventKeys.add(eventOne.getEventKey());
		List<String> eventKeys2 = new ArrayList<>();
		eventKeys2.add(eventTwo.getEventKey());
		RoomDTO roomOne = new RoomDTO("Uni2016","abcdefrtghi9847", "martin", "public",eventKeys);
		RoomDTO roomTwo = new RoomDTO("Madhjørnet","gauejr8rjd9ikd3", "peter", "public",eventKeys2);
		RoomDTO roomThree = new RoomDTO("Random","pdl3kr85jd9ikd3", "thomas", "public");
		roomDAO.createRoom(roomOne);
		roomDAO.createRoom(roomTwo);
		roomDAO.createRoom(roomThree);
		
		List<String> subRooms = new ArrayList<>();
		subRooms.add(roomOne.getRoomKey());
		subRooms.add(roomTwo.getRoomKey());
		subRooms.add(roomThree.getRoomKey());
		
		UserDTO user = new UserDTO("peter", "test",subRooms);
		UserDTO user1 = new UserDTO("thomas", "test",subRooms);
		UserDTO user2 = new UserDTO("martin", "test",subRooms);
		userDAO2.createUser(user1);
		userDAO2.createUser(user);
		userDAO2.createUser(user2);
		
		
		
	
		
	}

}
