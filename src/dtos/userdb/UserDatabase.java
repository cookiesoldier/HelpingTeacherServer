package dtos.userdb;

import java.sql.*;

import dtos.UserDTO;

/**
 * Created by Thomas on 13-04-2016.
 */
public class UserDatabase {
   private Connection c = null;

   public UserDatabase() {
      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:usertest.db");
         System.out.println("Opened database successfully");

      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
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
            "VALUES (?, ?, ?, ?, ?);";
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

}
