package daos;

import java.util.HashMap;

import daos.impl.AnswerDAO;
import daos.impl.EventDAO;
import daos.impl.QuestionDAO;
import daos.impl.RoomDAO;
import daos.impl.UserDAO;

public class Handler {
	
	public UserDAO userdao = new UserDAO();
	public EventDAO eventdao = new EventDAO();
	public RoomDAO roomdao = new RoomDAO();
	public QuestionDAO questiondao = new QuestionDAO();
	public AnswerDAO answerdao = new AnswerDAO();
	
	HashMap<String, String> sessions = new HashMap<>();
	
	public HashMap<String, String> getSessions() {
		return sessions;
	}
}
