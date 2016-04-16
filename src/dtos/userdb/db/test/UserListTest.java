package dtos.userdb.db.test;

import com.sun.rmi.rmid.ExecPermission;
import dtos.UserDTO;
import dtos.userdb.db.UserList;
import firebaseConn.IUserDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

/**
 * Created by Thomas on 14-04-2016.
 */
public class UserListTest {
   IUserDatabase users = new UserList();

   @Test
   public void createUser() throws Exception {
      UserDTO user = new UserDTO("mr1jakob", "mr1@jakob.org", "mr1.", "jakob", "mr1j4k0b");
      users.createUser(user);
      ((UserList) users).printList();
   }

   @Test
   public void authUserPosTest() throws Exception {
      String username = "mrjakob";
      String password = "mrj4k0b";
      assertTrue(users.authUser(username, password));
   }

   @Test
   public void authUserNegTest() throws Exception {
      String username = "mrjakob";
      String password = "yellowhorse";
      assertFalse(users.authUser(username, password));
   }


   
}