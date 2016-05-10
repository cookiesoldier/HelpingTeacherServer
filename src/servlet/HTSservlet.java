package servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
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

import daos.Handler;
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
import helper.DataInit;
import helper.LogMethods;
import helper.SessionMap;

/**
 * Servlet implementation class HTSservlet
 */
@WebServlet("/HTSservlet")
public class HTSservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IAnswerDAO answerDAO = Handler.answerdao;
	IEventDAO eventDAO = Handler.eventdao;
	IQuestionDAO questionDAO = Handler.questiondao;
	IRoomDAO roomDAO = Handler.roomdao;
	IUserDAO userDAO = Handler.userdao;
	LogMethods logger = new LogMethods();
	// sessions, en given bruger ved login vil f� en sessionID tilknyttet til
	// login navnet, denne sessionID bruger til at bekr�fte
	// hvem de er hvergang de vil lave en action udover login og create user

	// sessions, en given bruger ved login vil f� en sessionID tilknyttet til
	// login navnet, denne sessionID bruger til at bekr�fte
	// hvem de er hvergang de vil lave en action udover login og create user

	SessionMap sessions = Handler.sessions;
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
		if (runOnce) {
			DataInit dataTest = new DataInit(userDAO, roomDAO, answerDAO, questionDAO, eventDAO);
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
					// logger.printLog("Message GET: " + recievedString);

					if (receivedData.get("TASK").equals("loginauth")) {

						UserDTO user = new UserDTO(receivedData.get("USERNAME").toString(),
								receivedData.get("PASSWORD").toString());
						if (userDAO.authUser(user.getUsername(), user.getPassword())) {
							JSONObject reply = new JSONObject();
							reply.put("REPLY", "succes");
							String sessionKey = sessions.generateSessionKey();
							reply.put("SESSIONKEY", sessionKey);
							// save session in the sessionMap
							sessions.addSession(user.getUsername(), sessionKey);

							writer.write(reply.toString());
							logger.printLog("User authenticated: " + user.toJSONObject().toString());
						} else {
							JSONObject reply = new JSONObject();
							reply.put("REPLY", "failed");
							reply.put("MESSAGE", "wrong password or username");
							writer.write(reply.toString());
							logger.printLog("User not authenticated: " + user.toJSONObject().toString());
						}

					} else {
						// check if user is valid
						if (loginInfoVerify(receivedData)) {
							String sessionKey = null;
							UserDTO user = null;
							sessionKey = receivedData.get("SESSIONKEY").toString();
							user = new UserDTO(receivedData.get("USERNAME").toString());

							if (sessions.sessionMapCheck(user.getUsername(), sessionKey)) {
								getDataCalls(writer, receivedData);
							}else{
								JSONObject reply = new JSONObject();
								reply.put("REPLY", "failed");
								reply.put("MESSAGE", "session key error");
								writer.write(reply.toString());
							}

						} else {
							writer.write("Message recieved but not understood, message:" + receivedData.toString());
						}

					}
				}
			}

			writer.flush();
			writer.close();

		} catch (Exception e) {
			try {

				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(e.getMessage());
				response.getWriter().close();
			} catch (IOException | IllegalStateException e1) {
				// yolo
			}
		}

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String paramName = "delete";
		String paramValue = request.getParameter(paramName);
		// writer til at skrive response tilbage
		OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
		try {

		} catch (IllegalStateException e) {

		}

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// System.out.println("doPut");

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
				JSONObject receivedData = null;

				receivedData = (JSONObject) parser.parse(recievedString);

				// hent task:

				// check who it is, if match to sessionKey do stuff,
				// else reply error
				JSONObject reply = new JSONObject();

				if (receivedData.get("TASK").toString().equals("CREATEUSER")) {
					putCreate(writer, receivedData);
				} else {
					if (loginInfoVerify(receivedData)) {
						String sessionKey = null;
						UserDTO user = null;
						sessionKey = receivedData.get("SESSIONKEY").toString();
						user = new UserDTO(receivedData.get("USERNAME").toString());

						if (sessions.sessionMapCheck(user.getUsername(), sessionKey)) {
							logger.printLog("User Authenticated " + sessionKey);

							if (receivedData.get("TASK").toString().contains("UPDATE")) {
								putUpdate(writer, receivedData);
							} else if (receivedData.get("TASK").toString().contains("CREATE")) {
								logger.printLog("create Request " + receivedData.get("TASK").toString());
								putCreate(writer, receivedData);
							} else {
								reply.put("REPLY", "failed");
								reply.put("MESSAGE", "Received message did not contain create or update.");
								writer.write(reply.toString());
							}
						} else {
							reply.put("REPLY", "failed");
							reply.put("MESSAGE", "Error sessionkey mismatch");
							writer.write(reply.toString());
						}
					} else {
						reply.put("REPLY", "failed");
						reply.put("MESSAGE", "Input Error");
						writer.write(reply.toString());
					}
				}

				writer.flush();
				writer.close();
			}

		} catch (IOException | ParseException | IllegalStateException e) {

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().print(e.getMessage());
			response.getWriter().close();

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

	public void getDataCalls(OutputStreamWriter writer, JSONObject receivedData) throws IOException {
		String username = receivedData.get("USERNAME").toString();
		String sessionKey = receivedData.get("SESSIONKEY").toString();
		JSONObject reply = new JSONObject();
		if (sessions.sessionMapCheck(username, sessionKey)) {
			if (receivedData.get("TASK").equals("getuser")) {

				UserDTO userFound = userDAO.getUser(receivedData.get("GETNAME").toString());

				reply.put("REPLY", "succes");
				reply.put("USER", userFound.toJSONObject());
				writer.write(reply.toString());
				System.out.println(reply.toString());
				logger.printLog("GetUser request from:" + username + " found:" + userFound.toJSONObject().toString());

			} else if (receivedData.get("TASK").equals("getevent")) {
				EventDTO event = eventDAO.getEvent(receivedData.get("EVENTKEY").toString());

				reply.put("REPLY", "succes");
				reply.put("EVENT", event.toJSONObject());
				writer.write(reply.toString());
				logger.printLog("GetEvent request from: " + receivedData.get("USERNAME").toString() + " found:"
						+ event.toJSONObject().toString());

			} else if (receivedData.get("TASK").equals("getanswer")) {

				String answerKey = receivedData.get("ANSWERKEY").toString();
				AnswerDTO answer = answerDAO.getAnswer(answerKey);
				reply.put("REPLY", "succes");
				reply.put("ANSWER", answer.toJSONObject());
				writer.write(reply.toString());
				logger.printLog("GetAnswer request from: " + username + " found: " + answer.toJSONObject().toString());

			} else if (receivedData.get("TASK").equals("getroom")) {
				String roomKey = receivedData.get("ROOMKEY").toString();
				RoomDTO room = roomDAO.getRoom(roomKey);
				reply.put("REPLY", "succes");
				reply.put("ROOM", room.toJSONObject());
				writer.write(reply.toString());
				logger.printLog("GetRoom request from: " + username + " found: " + room.toJSONObject().toString());
			}
		} else {
			reply.put("REPLY", "failed");
			writer.write(reply.toString());
			logger.printLog("getRoom failed from: " + username + " found: " + receivedData.get("ROOMKEY").toString());
		}

	}

	private boolean loginInfoVerify(JSONObject receivedData) {

		String sessionKey = null;
		UserDTO user = null;
		logger.printLog("data received " + receivedData);
		if (receivedData.containsKey("SESSIONKEY")) {
			sessionKey = receivedData.get("SESSIONKEY").toString();
			logger.printLog("sessionKey received " + sessionKey);
		}

		if (receivedData.containsKey("USERNAME")) {
			user = new UserDTO(receivedData.get("USERNAME").toString());
			logger.printLog("Username Received" + user);
		}
		if (sessionKey != null && user != null) {
			return true;
		} else {
			return false;
		}
	}

	private void putUpdate(OutputStreamWriter writer, JSONObject receivedData) throws IOException {

		// If wrong data input, user overridden/replaced... so be carefull?
		if (receivedData.get("TASK").equals("UPDATEUSER")) {
			String testString = receivedData.get("SUBBEDROOMS").toString().substring(1,
					receivedData.get("SUBBEDROOMS").toString().length() - 1);
			List<String> subbedRooms = Arrays.asList(testString.toString().split(","));

			UserDTO user = new UserDTO(receivedData.get("USERNAME").toString(), receivedData.get("EMAIL").toString(),
					receivedData.get("FIRSTNAME").toString(), receivedData.get("LASTNAME").toString(),
					receivedData.get("PASSWORD").toString(), subbedRooms);
			JSONObject reply = new JSONObject();
			if (userDAO.updateUser(user.getUsername(), user)) {

				reply.put("REPLY", "succes");
				reply.put("USER", user.toJSONObject());
				writer.write(reply.toString());
				logger.printLog("UpdateUser succes from: " + receivedData.get("USERNAME").toString()
						+ " requested update to:" + user);

			} else {

				reply.put("REPLY", "failed");
				reply.put("MESSAGE", "could not update user");
				writer.write(reply.toString());
				logger.printLog("UpdateUser failed from: " + receivedData.get("USERNAME").toString()
						+ " requested update to:" + user);
			}

		} else if (receivedData.get("TASK").equals("UPDATEANSWER")) {
			AnswerDTO answer = new AnswerDTO(receivedData.get("answerkey").toString(),
					receivedData.get("BODY").toString(), receivedData.get("TIMESTAMP").toString(),
					receivedData.get("SENDER").toString());
			JSONObject reply = new JSONObject();
			if (answerDAO.updateAnswer(answer, answer)) {

				reply.put("REPLY", "succes");
				reply.put("ANSWER", answer.toJSONObject());
				writer.write(reply.toString());
				logger.printLog("UpdateAnswer succes from: " + receivedData.get("USERNAME").toString()
						+ " requested update to:" + answer);
			} else {
				reply.put("REPLY", "failed");
				reply.put("MESSAGE", "could not update answer");
				writer.write(reply.toString());
				logger.printLog("UpdateAnswer failed from: " + receivedData.get("USERNAME").toString()
						+ " requested update to:" + answer);
			}

		} else if (receivedData.get("TASK").equals("UPDATEEVENT")) {
			String testString = receivedData.get("QUESTIONKEYS").toString().substring(1,
					receivedData.get("QUESTIONKEYS").toString().length() - 1);
			List<String> questions = Arrays.asList(testString.toString().split(","));
			EventDTO event = new EventDTO(receivedData.get("title").toString(),
					receivedData.get("TIMESTAMP").toString(), receivedData.get("EVENTKEY").toString(),
					receivedData.get("CREATOR").toString(), questions);
			JSONObject reply = new JSONObject();
			if (eventDAO.updateEvent(event, event)) {
				reply.put("REPLY", "succes");
				reply.put("ANSWER", event.toJSONObject());
				writer.write(reply.toString());
				logger.printLog("UpdateEvent succes from: " + receivedData.get("USERNAME").toString()
						+ " requested update to:" + event);

			} else {
				reply.put("REPLY", "failed");
				reply.put("ANSWER", event.toJSONObject());
				writer.write(reply.toString());
				logger.printLog("UpdateEvent failed from: " + receivedData.get("USERNAME").toString()
						+ " requested update to:" + event);
			}

		} else if (receivedData.get("TASK").equals("UPDATEQUESTION")) {
			String testString = receivedData.get("ANSWERKEYS").toString().substring(1,
					receivedData.get("ANSWERKEYS").toString().length() - 1);
			List<String> answers = Arrays.asList(testString.toString().split(","));
			QuestionDTO question = new QuestionDTO(receivedData.get("TITLE").toString(),
					receivedData.get("BODY").toString(), receivedData.get("TIMESTAMP").toString(),
					receivedData.get("QUESTIONKEYS").toString(), receivedData.get("SENDER").toString(), answers);
			JSONObject reply = new JSONObject();
			if (questionDAO.updateQuestion(question, question)) {
				reply.put("REPLY", "succes");
				reply.put("QUESTION", question.toJSONObject());
				writer.write(reply.toString());
				logger.printLog("UpdateQuestion succes from: " + receivedData.get("USERNAME").toString()
						+ " requested update to:" + question);

			} else {
				reply.put("REPLY", "failed");
				reply.put("QUESTION", question.toJSONObject());
				writer.write(reply.toString());
				logger.printLog("UpdateQuestion failed from: " + receivedData.get("USERNAME").toString()
						+ " requested update to:" + question);
			}
		} else if (receivedData.get("TASK").equals("UPDATEROOM")) {
			String testString = receivedData.get("EVENTKEYS").toString().substring(1,
					receivedData.get("EVENTKEYS").toString().length() - 1);
			List<String> events = Arrays.asList(testString.toString().split(","));
			RoomDTO room = new RoomDTO(receivedData.get("TITLE").toString(), receivedData.get("ROOMKEYS").toString(),
					receivedData.get("OWNER").toString(), receivedData.get("TYPE").toString(), events);
			JSONObject reply = new JSONObject();
			if (roomDAO.updateRoom(room, room)) {
				reply.put("REPLY", "succes");
				reply.put("ROOM", room);
				writer.write(reply.toString());
				logger.printLog("UpdateRoom succes from: " + receivedData.get("USERNAME").toString()
						+ " requested update to:" + room);
			} else {
				reply.put("REPLY", "failed");
				reply.put("ROOM", room);
				writer.write(reply.toString());
				logger.printLog("UpdateRoom failed from: " + receivedData.get("USERNAME").toString()
						+ " requested update to:" + room);
			}
		} else {
			writer.write("Message recieved but not understood, message:" + receivedData.toString());
		}

	}

	public void putCreate(OutputStreamWriter writer, JSONObject receivedData) throws IOException {
		if (receivedData.get("TASK").equals("CREATEUSER")) {
			// System.out.println(receivedData.toString());
			UserDTO user = new UserDTO(receivedData.get("USERNAME").toString(), receivedData.get("PASSONE").toString());
			JSONObject reply = new JSONObject();
			if (userDAO.createUser(user)) {
				reply.put("REPLY", "succes");
				reply.put("USER", user.toJSONObject());
				logger.printLog("User created: " + user.getUsername() + " pass: " + user.getPassword());
			} else {
				reply.put("REPLY", "failed");
			}
			writer.write(reply.toString());
		} else if (receivedData.get("TASK").equals("CREATEANSWER")) {
			/*
			 * Need to only recieve Body, Timestamp and who the sender is. Rest
			 * is made here Answerkey is generated with sessionKey generator for
			 * now :)
			 */
			AnswerDTO answer = new AnswerDTO(sessions.generateSessionKey(), receivedData.get("BODY").toString(),
					receivedData.get("TIMESTAMP").toString(), receivedData.get("SENDER").toString());
			JSONObject reply = new JSONObject();
			if (answerDAO.createAnswer(answer)) {
				reply.put("REPLY", "succes");
				reply.put("ANSWER", answer.toJSONObject());
				logger.printLog("Answer created: " + answer.toJSONObject().toString());

			} else {
				reply.put("REPLY", "failed");
			}
			writer.write(reply.toString());

		} else if (receivedData.get("TASK").equals("CREATEEVENT")) {
			EventDTO event = new EventDTO(receivedData.get("TITLE").toString(),
					receivedData.get("TIMESTAMP").toString(), receivedData.get("CREATOR").toString(),
					(sessions.generateSessionKey()));
			JSONObject reply = new JSONObject();
			if (eventDAO.createEvent(event)) {
				reply.put("REPLY", "succes");
				reply.put("EVENT", event.toJSONObject());
				logger.printLog("Event created: " + event.toJSONObject().toString());
			} else {
				reply.put("REPLY", "failed");
			}
			writer.write(reply.toString());

		} else if (receivedData.get("TASK").equals("CREATEQUESTION")) {
			QuestionDTO question = new QuestionDTO(receivedData.get("TITLE").toString(),
					receivedData.get("BODY").toString(), receivedData.get("TIMESTAMP").toString(),
					sessions.generateSessionKey(), receivedData.get("SENDER").toString());
			JSONObject reply = new JSONObject();
			if (questionDAO.createQuestion(question)) {
				reply.put("REPLY", "succes");
				reply.put("QUESTION", question.toJSONObject());
				logger.printLog("Question created: " + question.toJSONObject().toString());
			} else {
				reply.put("REPLY", "failed");
			}
			writer.write(reply.toString());
		} else if (receivedData.get("TASK").equals("CREATEROOM")) {
			RoomDTO room = new RoomDTO(receivedData.get("TITLE").toString(), sessions.generateSessionKey(),
					receivedData.get("OWNER").toString(), receivedData.get("TYPE").toString());
			JSONObject reply = new JSONObject();
			if (roomDAO.createRoom(room)) {
				reply.put("REPLY", "succes");
				reply.put("ROOM", room.toJSONObject());
				logger.printLog("Room created: " + room.toJSONObject().toString());
			} else {
				reply.put("REPLY", "failed");
			}
			writer.write(reply.toString());

		} else {
			writer.write("Message recieved but not understood, message:" + receivedData.toString());
		}
	}

}