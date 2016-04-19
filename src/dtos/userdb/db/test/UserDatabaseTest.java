package dtos.userdb.db.test;

import dtos.UserDTO;
import dtos.userdb.UserDatabase;
import firebaseConn.IUserDatabase;

/**
 * Created by Thomas on 14-04-2016.
 */
public class UserDatabaseTest {

   public static void main(String[] args) {
      IUserDatabase db = new UserDatabase();
      UserDTO user = new UserDTO("testing", "testing", "testing", "testing", "testing");
      db.createUser(user);

   }
}
