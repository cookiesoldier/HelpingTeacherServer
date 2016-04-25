package servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import daos.RoomDAO;
import daos.UserDAO;
import daos.interfaces.IRoomDAO;
import daos.interfaces.IUserDAO;
import dtos.RoomDTO;
import dtos.UserDTO;

/**
 * Servlet implementation class HTSservlet
 */
@WebServlet("/HTSservlet")
public class HTSservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HTSservlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean check = false;
		// response.getOutputStream().println("Hurray !! This Servlet Works");
		String paramName = "logininfo";
		String paramValue = request.getParameter(paramName);

		try {
			if (paramValue != null) {

				String recievedString = new String(paramValue);
				// System.out.println(recievedString);
				JSONParser parser = new JSONParser();
				JSONObject receivedData = (JSONObject) parser.parse(recievedString);
				if (receivedData != null) {
					if (receivedData.get("TASK").equals("loginauth")) {
						//System.out.println(receivedData.toString());
						UserDTO userjson = new UserDTO(receivedData.get("PASSWORD").toString(),
								receivedData.get("USERNAME").toString());
					//	IFirebaseConnection firebase = new FirebaseConnection();
					//	check = firebase.authUser(userjson);
						
					}else if(receivedData.get("TASK").equals("????")){
						
					}
				}
			} else {
				check = false;
			}
			OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

			if (!check) {
				writer.write("loginfailed");
			} else {
				writer.write("loginsucces");
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
					UserDTO userjson = new UserDTO(receivedData.get("USERNAME").toString(),receivedData.get("PASSONE").toString());
					IUserDAO userDAO = new UserDAO();
					succes = userDAO.createUser(userjson);
					IRoomDAO roomDAO = new RoomDAO();
					succes = roomDAO.createRoom(new RoomDTO("", "", "", ""));
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

}