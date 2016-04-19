package dtos.userdb.test;

import dtos.UserDTO;
import dtos.userdb.UserDatabase;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by Thomas on 13-04-2016.
 */
public class DBTest {

   public static void main(String[] args) {
      /*
      UserDatabase db = new UserDatabase();

      UserDTO user = new UserDTO("supm8", "topkek");
      user.setEmail("google@gmail.com");
      user.setFirstname("Bruce");
      user.setLastname("Wayne");
      System.out.println(user.toString());
      db.createUser(user);
      */
      try {
         InitialContext ic = new InitialContext();
         DataSource ds = (DataSource) ic.lookup("jdbc/sqlite");
         System.out.println("DataSource fundet.");
      } catch (NamingException e) {
         e.printStackTrace();
      }

   }
}
