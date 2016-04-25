package servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import daos.AnswerDAO;
import daos.EventDAO;
import daos.QuestionDAO;
import daos.RoomDAO;
import daos.UserDAO;
import daos.interfaces.IAnswerDAO;
import daos.interfaces.IEventDAO;
import daos.interfaces.IQuestionDAO;
import daos.interfaces.IRoomDAO;
import daos.interfaces.IUserDAO;
import dtos.AnswerDTO;
import dtos.RoomDTO;
import dtos.UserDTO;

/**
 * Servlet implementation class HTSservlet
 */
@WebServlet("/HTSservlet")
public class HTSservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IAnswerDAO answerDAO = new AnswerDAO();
	IEventDAO eventDAO = new EventDAO();
	IQuestionDAO questionDAO = new QuestionDAO();
	IRoomDAO roomDAO = new RoomDAO();
	IUserDAO userDAO = new UserDAO();
	//sessions, en given bruger ved login vil f� en sessionID tilknyttet til login navnet, denne sessionID bruger til at bekr�fte 
	//hvem de er hvergang de vil lave en action udover login og create user
	HashMap sessionMap = new HashMap();
	//eksempel sessionMap.put("username", "sessionIDUnique");
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HTSservlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		// response.getOutputStream().println("Hurray !! This Servlet Works");
		String paramName = "logininfo";
		// 
		String paramValue = request.getParameter(paramName);
		// writer til at skrive response tilbage
		OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
		try {
			if (paramValue != null) {

				String recievedString = new String(paramValue);
				// System.out.println(recievedString);
				JSONParser parser = new JSONParser();
				JSONObject receivedData = (JSONObject) parser.parse(recievedString);
				if (receivedData != null) {
					if (receivedData.get("TASK").equals("loginauth")) {
						// System.out.println(receivedData.toString());
						UserDTO user = new UserDTO(receivedData.get("USERNAME").toString(),receivedData.get("PASSWORD").toString());
						boolean loginAuth = userDAO.authUser(user.getUsername(), user.getPassword());
						
						if (loginAuth) {
							JSONObject reply = new JSONObject();
							reply.put("REPLY","succes");
							String sessionKey =sessionKeyGenerator();
							reply.put("SESSIONKEY", sessionKey);
							//save session in the sessionMap
							sessionMap.put(user.getUsername(), sessionKey);
							
							writer.write(reply.toString());
						} else {
							JSONObject reply = new JSONObject();
							reply.put("REPLY","failed");
							
							reply.put("SESSIONKEY", "NULL");
							writer.write(reply.toString());
						}

					} else if (receivedData.get("TASK").equals("getuser")) {
						UserDTO user = new UserDTO(receivedData.get("USERNAME").toString());
						String sessionKey = receivedData.get("SESSIONKEY").toString();
						//check who it is, if match to sessionKey do stuff, else reply error
						if(sessionMapCheck(user.getUsername(), sessionKey)){
							
						UserDTO userFound = userDAO.getUser(user.getUsername());
						JSONObject reply = new JSONObject();
						reply.put("REPLY","succes");
						reply.put("USER",userFound.toJSONObject());
						//not done
						}else{
							JSONObject reply = new JSONObject();
							reply.put("REPLY","failed");
							reply.put("MESSAGE", "Sessionkey not matching");
							writer.write(reply.toString());
							
						}
						

					}else if (receivedData.get("TASK").equals("getanswer")) {
						String username = receivedData.get("USERNAME").toString();
						String sessionKey = receivedData.get("SESSIONKEY").toString();
						JSONObject reply = new JSONObject();
						
						if (sessionMapCheck(username, sessionKey)) {
							// skal hente et specifikt svar, defineret af dens key
							String answerKey = receivedData.get("ANSWERKEY").toString();
							AnswerDTO answer = answerDAO.getAnswer(answerKey);
							reply.put("REPLY", "succes");
							reply.put("ANSWER", answer.toJSONObject());
							writer.write(reply.toString());
						} else {
							reply.put("REPLY", "failed");
							writer.write(reply.toString());
						}
						
						
					}else if (receivedData.get("TASK").equals("getevent")) {
						

					}else if (receivedData.get("TASK").equals("getroom")) {
						String username = receivedData.get("USERNAME").toString();
						String sessionKey = receivedData.get("SESSIONKEY").toString();
						JSONObject reply = new JSONObject();
						
						if (sessionMapCheck(username, sessionKey)) {
							// skal hente et specifikt svar, defineret af dens key
							String roomKey = receivedData.get("ROOMKEY").toString();
							RoomDTO room = roomDAO.getRoom(roomKey);
							reply.put("REPLY", "succes");
							reply.put("ROOM", room.toJSONObject());
							writer.write(reply.toString());
						} else {
							reply.put("REPLY", "failed");
							writer.write(reply.toString());
						}
					
					}
				}else{
					writer.write("Message recieved but not understood, message:"+receivedData.toString());
				}
			} else {
				writer.write("An Error Occured!");
			}
			

			

			writer.flush();
			writer.close();

		} catch (IOException | ParseException e) {
			try {

				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(e.getMessage());
				response.getWriter().close();
			} catch (IOException e1) {
			}
		}

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPut");
		try {
			int length = request.getContentLength();

			if (length > 0) {
				byte[] input = new byte[length];
				ServletInputStream sin = request.getInputStream();
				int c, count = 0;
				while ((c = sin.read(input, count, input.length - count)) != -1) {
					count += c;
				}
				sin.close();

				String recievedString = new String(input);
				JSONParser parser = new JSONParser();
				JSONObject receivedData = (JSONObject) parser.parse(recievedString);
				// hent task:
				boolean succes = false;
				if (receivedData.get("TASK").equals("CREATEUSER")) {
					// System.out.println(receivedData.toString());
					UserDTO userjson = new UserDTO(receivedData.get("USERNAME").toString(),
							receivedData.get("PASSONE").toString());
					IUserDAO userDAO = new UserDAO();
					succes = userDAO.createUser(userjson);

				}

				OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

				if (!succes) {
					writer.write("CONNECTION TO FIREBASE FAILED");
				} else {
					writer.write("CONNECTION TO FIREBASE SUCCES");
				}

				writer.flush();
				writer.close();
			}

		} catch (IOException | ParseException e) {

			try {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(e.getMessage());
				response.getWriter().close();
			} catch (IOException e1) {
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int length = request.getContentLength();
			byte[] input = new byte[length];
			ServletInputStream sin = request.getInputStream();
			int c, count = 0;
			while ((c = sin.read(input, count, input.length - count)) != -1) {
				count += c;
			}
			sin.close();

			String recievedString = new String(input);
			response.setStatus(HttpServletResponse.SC_OK);
			System.out.println("doPost" + recievedString);
			OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

			writer.write("Succes");
			writer.flush();
			writer.close();

		} catch (IOException e) {

			try {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(e.getMessage());
				response.getWriter().close();
			} catch (IOException ioe) {
			}
		}
	}
	
	public String sessionKeyGenerator(){
		String aToZ="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // 36 letter.
		 Random rand=new Random();
		    StringBuilder res=new StringBuilder();
		    for (int i = 0; i < 15; i++) {
		       int randIndex=rand.nextInt(aToZ.length()); 
		       res.append(aToZ.charAt(randIndex));            
		    }
		    return res.toString();
		
	}
	
	public boolean sessionMapCheck(String username, String sessionKey){
		
		
		
		
		return false;
	}

}