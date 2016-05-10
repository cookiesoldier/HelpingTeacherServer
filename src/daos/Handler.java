package daos;

import daos.impl.AnswerDAO;
import daos.impl.EventDAO;
import daos.impl.QuestionDAO;
import daos.impl.RoomDAO;
import daos.impl.UserDAO;
import daos.interfaces.IAnswerDAO;
import daos.interfaces.IEventDAO;
import daos.interfaces.IQuestionDAO;
import daos.interfaces.IRoomDAO;
import daos.interfaces.IUserDAO;
import helper.SessionMap;

public class Handler {
	
	public static IUserDAO userdao = new UserDAO();
	public static IEventDAO eventdao = new EventDAO();
	public static IRoomDAO roomdao = new RoomDAO();
	public static IQuestionDAO questiondao = new QuestionDAO();
	public static IAnswerDAO answerdao = new AnswerDAO();
	
	public static SessionMap sessions = new SessionMap();
	
	public static String generateSessionKey() {
		return sessions.generateSessionKey();
	}
	
}
