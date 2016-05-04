package daos;

import daos.impl.AnswerDAO;
import daos.impl.EventDAO;
import daos.impl.QuestionDAO;
import daos.impl.RoomDAO;
import daos.impl.UserDAO;
import helper.SessionMap;

public class Handler {
	
	public static UserDAO userdao = new UserDAO();
	public static EventDAO eventdao = new EventDAO();
	public static RoomDAO roomdao = new RoomDAO();
	public static QuestionDAO questiondao = new QuestionDAO();
	public static AnswerDAO answerdao = new AnswerDAO();
	
	public static SessionMap sessions = new SessionMap();
	
	public static String generateSessionKey() {
		return sessions.generateSessionKey();
	}
	
}
