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

import firebaseConn.FirebaseConn;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 
        response.getOutputStream().println("Hurray !! This Servlet Works");
 
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int length = request.getContentLength();
			
			if(length > 0){
			byte[] input = new byte[length];
			ServletInputStream sin = request.getInputStream();
			int c, count = 0;
			while ((c = sin.read(input, count, input.length - count)) != -1) {
				count += c;
			}
			sin.close();

			String recievedString = new String(input);
			JSONParser parser = new JSONParser();
			JSONObject recievedData = (JSONObject) parser.parse(recievedString);
			//hent task:
			System.out.println("ellooooo");
			String firebResponse = "NULL";
			if(recievedData.get("TASK").equals("CREATEUSER")){
				
				
				System.out.println("test henrik og signe");
				firebResponse = FirebaseConn.firebaseCreateUser(recievedData);
				System.out.println("test Martin");
			}
			System.out.println(recievedString);
			response.setStatus(HttpServletResponse.SC_OK);
//			System.out.println("FirebaseRespons: " + firebResponse);
//			System.out.println("RecievedString: "+ recievedString);
			OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

			writer.write("Succes");
			writer.flush();
			writer.close();}

		} catch (IOException | ParseException | JacksonUtilityException | FirebaseException e) {

			try {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(e.getMessage());
				response.getWriter().close();
			} catch (IOException ioe) {
			}
		}
	}
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        try {
            int length = request.getContentLength();
            byte[] input = new byte[length];
            ServletInputStream sin = request.getInputStream();
            int c, count = 0 ;
            while ((c = sin.read(input, count, input.length-count)) != -1) {
                count +=c;
            }
            sin.close();
 
            String recievedString = new String(input);
            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println(recievedString);
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            
 
            writer.write("Succes");
            writer.flush();
            writer.close();
 
 
 
        } catch (IOException e) {
 
 
            try{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(e.getMessage());
                response.getWriter().close();
            } catch (IOException ioe) {
            }
        }   
        }
 
}