package lolgacy.userdb.db;

import dtos.AnswerDTO;
import dtos.EventDTO;
import dtos.QuestionDTO;
import dtos.RoomDTO;
import dtos.UserDTO;
import firebaseConn.IUserDatabase;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Created by Thomas on 14-04-2016.
 */
public class UserList implements IUserDatabase {

   private List<UserDTO> users;

   public UserList() {
      try {
         FileInputStream fin = new FileInputStream(new File("WebContent/test/users.ser"));
         ObjectInputStream oin = new ObjectInputStream(fin);
         users = (ArrayList<UserDTO>) oin.readObject();
         oin.close();
         fin.close();
         System.out.println("users list loaded from file.");
      } catch (IOException | ClassNotFoundException e) {
         users = new ArrayList<>();
         System.out.println("No list found. A new list has been created.");
         e.printStackTrace();
      }
   }

   @Override
   public boolean createUser(UserDTO user) {
      if(getUser(user.getUsername()) != null) return false;
      users.add(user);
      System.out.println("User added to list");
      try {
         FileOutputStream fout = new FileOutputStream("WebContent/test/users.ser");
         ObjectOutputStream oout = new ObjectOutputStream(fout);
         oout.writeObject(users);
         oout.close();
         fout.close();
         System.out.println("List was saved to disk.");
      } catch (IOException e) {
         System.out.println("List could not be saved to disk.");
         e.printStackTrace();

      }
      return true;
   }

   @Override
   public boolean authUser(String username, String password) {
      for(UserDTO u : users) {
         if(u.getUsername().equals(username)) {
            System.out.println("User "+username+" found.");
            if(u.getPassword().equals(password)){
               System.out.println("Password matches.");
               return true;
            } else return false;
         }
      } return false;
   }

   @Override
   public UserDTO getUser(String name) {
      for(UserDTO u : users) {
         if(u.getUsername().equals(name)) return u;
      }
      return null;
   }

   @Override
   public JSONObject userToJSON(UserDTO user) {
      JSONObject obj = new JSONObject();
      obj.put("username", user.getUsername());
      obj.put("email", user.getEmail());
      obj.put("password", user.getPassword());
      obj.put("firstname", user.getFirstname());
      obj.put("lastname", user.getLastname());
      return obj;
   }

   @Override
   public UserDTO JSONtoUser(JSONObject obj) {
      UserDTO user;
      String username = obj.get("username").toString();
      String email = obj.get("email").toString();
      String password = obj.get("password").toString();
      String firstname = obj.get("firstname").toString();
      String lastname = obj.get("lastname").toString();
      user = new UserDTO(username, email, firstname, lastname, password);
      return user;
   }

   @Override
   public boolean isJSONObjectUser(JSONObject obj) {
      if(obj.size() == 5) {
         if(!obj.containsKey("username")) return false;
         if(!obj.containsKey("email")) return false;
         if(!obj.containsKey("password")) return false;
         if(!obj.containsKey("firstname")) return false;
         if(!obj.containsKey("lastname")) return false;
      } else return false;
      return true;
   }

   public void printList() {
      for(UserDTO u : users) {
         System.out.println(u.toString());
      }
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
