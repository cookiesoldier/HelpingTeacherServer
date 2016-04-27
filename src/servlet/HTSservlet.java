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
import dtos.EventDTO;
import dtos.QuestionDTO;
import dtos.AnswerDTO;
import dtos.RoomDTO;
import dtos.UserDTO;
import helper.DataInit;

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
	// sessions, en given bruger ved login vil få en sessionID tilknyttet til
	// login navnet, denne sessionID bruger til at bekræfte
	// hvem de er hvergang de vil lave en action udover login og create user

	// sessions, en given bruger ved login vil fï¿½ en sessionID tilknyttet til
	// login navnet, denne sessionID bruger til at bekrï¿½fte
	// hvem de er hvergang de vil lave en action udover login og create user

	HashMap sessionMap = new HashMap();
	// eksempel sessionMap.put("username", "sessionIDUnique");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HTSservlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean runOnce = true;
		if(runOnce){
			DataInit dataTest = new DataInit(userDAO);
			runOnce = false;
		}
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
						
						if (userDAO.authUser(user.getUsername(), user.getPassword())) {
							JSONObject reply = new JSONObject();
							reply.put("REPLY", "succes");
							String sessionKey = sessionKeyGenerator();
							reply.put("SESSIONKEY", sessionKey);
							// save session in the sessionMap
							sessionMap.put(user.getUsername(), sessionKey);

							writer.write(reply.toString());
						} else {
							JSONObject reply = new JSONObject();
							reply.put("REPLY", "failed");

							reply.put("SESSIONKEY", "NULL");
							writer.write(reply.toString());
						}

					} else if (receivedData.get("TASK").equals("getuser")) {
						UserDTO user = new UserDTO(receivedData.get("USERNAME").toString());
						String sessionKey = receivedData.get("SESSIONKEY").toString();
						// check who it is, if match to sessionKey do stuff,
						// else reply error
						if (sessionMapCheck(user.getUsername(), sessionKey)) {

							UserDTO userFound = userDAO.getUser(receivedData.get("GETNAME").toString());
							JSONObject reply = new JSONObject();
							reply.put("REPLY", "succes");
							reply.put("USER", userFound.toJSONObject());
							writer.write(reply.toString());

						} else {
							JSONObject reply = new JSONObject();
							reply.put("REPLY", "failed");
							reply.put("MESSAGE", "Sessionkey not matching");
							writer.write(reply.toString());

						}

					} else if (receivedData.get("TASK").equals("getevent")) {
						String sessionKey = receivedData.get("SESSIONKEY").toString();
						if (sessionMapCheck(receivedData.get("USERNAME").toString(), sessionKey)) {
							EventDTO event = eventDAO.getEvent(receivedData.get("EVENTKEY").toString());
							JSONObject reply = new JSONObject();
							reply.put("REPLY", "succes");
							reply.put("EVENT", event.toJSONObject());
							writer.write(reply.toString());

						} else {
							JSONObject reply = new JSONObject();
							reply.put("REPLY", "failed");
							reply.put("MESSAGE", "Sessionkey not matching");
							writer.write(reply.toString());

						}
					} else if (receivedData.get("TASK").equals("getanswer")) {
						String username = receivedData.get("USERNAME").toString();
						String sessionKey = receivedData.get("SESSIONKEY").toString();
						JSONObject reply = new JSONObject();

						if (sessionMapCheck(username, sessionKey)) {
							// skal hente et specifikt svar, defineret af dens
							// key
							String answerKey = receivedData.get("ANSWERKEY").toString();
							AnswerDTO answer = answerDAO.getAnswer(answerKey);
							reply.put("REPLY", "succes");
							reply.put("ANSWER", answer.toJSONObject());
							writer.write(reply.toString());
						} else {
							reply.put("REPLY", "failed");
							writer.write(reply.toString());
						}

					} else if (receivedData.get("TASK").equals("getroom")) {
						String username = receivedData.get("USERNAME").toString();
						String sessionKey = receivedData.get("SESSIONKEY").toString();
						JSONObject reply = new JSONObject();

						if (sessionMapCheck(username, sessionKey)) {
							// skal hente et specifikt svar, defineret af dens
							// key
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
				} else {
					writer.write("Message recieved but not understood, message:" + receivedData.toString());
				}
			} else {
				writer.write("An Error Occured!");
			}

			writer.flush();
			writer.close();

		} catch (Exception e) {
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
	
		
		OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
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

				if (receivedData.get("TASK").equals("CREATEUSER")) {

					// System.out.println(receivedData.toString());
					UserDTO user = new UserDTO(receivedData.get("USERNAME").toString(),
							receivedData.get("PASSONE").toString());
					JSONObject reply = new JSONObject();
					if (userDAO.createUser(user)) {
						reply.put("REPLY", "succes");
						reply.put("USER", user);
					} else {
						reply.put("REPLY", "failed");
					}
					writer.write(reply.toString());
				} else if (receivedData.get("TASK").equals("CREATEANSWER")) {
					/*
					 * Need to only recieve Body, Timestamp and who the sender
					 * is. Rest is made here Answerkey is generated with
					 * sessionKey generator for now :)
					 */
					AnswerDTO answer = new AnswerDTO(sessionKeyGenerator(), receivedData.get("BODY").toString(),
							receivedData.get("TIMESTAMP").toString(), receivedData.get("SENDER").toString());
					JSONObject reply = new JSONObject();
					if (answerDAO.createAnswer(answer)) {
						reply.put("REPLY", "succes");
						reply.put("ANSWER", answer.toJSONObject());

					} else {
						reply.put("REPLY", "failed");
					}
					writer.write(reply.toString());

				} else if (receivedData.get("TASK").equals("CREATEEVENT")) {
					EventDTO event = new EventDTO(receivedData.get("TITLE").toString(),
							receivedData.get("TIMESTAMP").toString(), (sessionKeyGenerator()));
					JSONObject reply = new JSONObject();
					if (eventDAO.createEvent(event)) {
						reply.put("REPLY", "succes");
						reply.put("EVENT", event.toJSONObject());
					} else {
						reply.put("REPLY", "failed");
					}
					writer.write(reply.toString());

				} else if (receivedData.get("TASK").equals("CREATEQUESTION")) {
					QuestionDTO question = new QuestionDTO(receivedData.get("TITLE").toString(),
							receivedData.get("BODY").toString(), receivedData.get("TIMESTAMP").toString(),
							sessionKeyGenerator());
					JSONObject reply = new JSONObject();
					if (questionDAO.createQuestion(question)) {
						reply.put("REPLY", "succes");
						reply.put("QUESTION", question.toJSONObject());
					} else {
						reply.put("REPLY", "failed");
					}
					writer.write(reply.toString());
				} else if (receivedData.get("TASK").equals("CREATEROOM")) {
					RoomDTO room = new RoomDTO(sessionKeyGenerator(), receivedData.get("OWNER").toString(),receivedData.get("type").toString() );
					JSONObject reply = new JSONObject();
					if (roomDAO.createRoom(room)) {
						reply.put("REPLY", "succes");
						reply.put("ROOM", room.toJSONObject());
					} else {
						reply.put("REPLY", "failed");
					}
					writer.write(reply.toString());
					
				} else {
					writer.write("Message recieved but not understood, message:" + receivedData.toString());
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

	public String sessionKeyGenerator() {
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

		String value  = (String) sessionMap.get(username);
		return value.equals(sessionKey);
	}

}