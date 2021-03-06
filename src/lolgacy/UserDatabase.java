package lolgacy;

import java.sql.*;

import dtos.AnswerDTO;
import dtos.EventDTO;
import dtos.QuestionDTO;
import dtos.RoomDTO;
import dtos.UserDTO;
import firebaseConn.IUserDatabase;
import org.json.simple.JSONObject;

/**
 * Created by Thomas on 13-04-2016.
 */
public class UserDatabase implements IUserDatabase {
   private Connection c = null;

   public UserDatabase() {
      try {
         Class.forName("com.mysql.jdbc.Driver").newInstance();
         String username = "admin";
         String password = "gruppe5";
         c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tas_db/" +
               "user=admin&password=gruppe5&useSSL=false");
         if(c == null) System.out.println("Der er ingen connection");
         else System.out.println("Der er connection, tror jeg.");

         //c.

      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         //System.exit(0);
      }

   }

   public UserDTO authenticateUser(String username, String password) {
      try {
         Statement st = c.createStatement();
         //String sql
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }

   public boolean createUser(UserDTO user) {
      //c.get
      PreparedStatement ps;
      String sql = "INSERT INTO user (" +
            "username, firstname, lastname, email, password)" +
            "VALUES (?,?,?,?,?);";
      /*
      String sql1 = "INSERT INTO user (" +
            "username, firstname, lastname, email, password)" +
            "VALUES ('hvemerdu', 'hvemerjeg', 'hvorerjeg'," +
            " 'hvaderjeg', 'hvaderdu');";
      */

      try {
         c.setAutoCommit(false);
         ps = c.prepareStatement(sql);
         ps.setString(1, user.getUsername());
         ps.setString(2, user.getFirstname());
         ps.setString(3, user.getLastname());
         ps.setString(4, user.getEmail());
         ps.setString(5, user.getPassword());
         System.out.println("Job went " + (ps.execute()?
         "good." : "bad."));
         c.commit();
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
      return true;
   }

   @Override
   public boolean authUser(String username, String password) {
      return false;
   }

   @Override
   public UserDTO getUser(String name) {
      return null;
   }

   @Override
   public JSONObject userToJSON(UserDTO user) {
      return null;
   }

   @Override
   public UserDTO JSONtoUser(JSONObject obj) {
      return null;
   }

   @Override
   public boolean isJSONObjectUser(JSONObject obj) {
      return false;
   }

@Override
public boolean getRoom(RoomDTO room) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean updateRoom(RoomDTO room) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean createRoom(RoomDTO room) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean deleteRoom(RoomDTO room) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean updateEvent(EventDTO event) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean createEvent(EventDTO event) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean deleteEvent(EventDTO event) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean getEvent(EventDTO event) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean updateQuestion(QuestionDTO question) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean createQuestion(QuestionDTO question) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean deleteQuestion(QuestionDTO question) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean getQuestion(QuestionDTO question) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean updateAnswers(AnswerDTO answer) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean createAnswer(AnswerDTO answer) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean deleteAnswers(AnswerDTO answer) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean getAnswers(AnswerDTO answer) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean updateUser(UserDTO user) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean deleteUser(UserDTO user) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean getUser(UserDTO user) {
	// TODO Auto-generated method stub
	return false;
}

}
