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
//    	 new org.apache.http.client.methods.HttpRequestBase() {
//
//			@Override
//			public String getMethod() {
//				// TODO Auto-generated method stub
//				return null;
//			}}.getClass();
        response.getOutputStream().println("Hurray !! This Servlet Works");
 
    }
    @SuppressWarnings("unused")
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
			
		
			OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
			if(response.getStatus() == 404){
				writer.write("CONNECTION TO FIREBASE FAILED");
			}else{
				writer.write("CONNECTION TO FIREBASE SUCCES");
			}
			
			writer.flush();
			writer.close();}

		} catch (IOException | ParseException e) {

			try {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(e.getMessage());
				response.getWriter().close();
			} catch (IOException e1) {
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