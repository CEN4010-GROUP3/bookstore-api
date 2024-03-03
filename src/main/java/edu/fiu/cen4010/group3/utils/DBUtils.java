package edu.fiu.cen4010.group3.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

   public static Connection Connect() {
      Connection c = null;
      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/bookstoredb",
            "postgres", "6369");
      } catch (SQLException e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         return null;
      } catch (Exception e) {
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(-1);
      }
      
      return c;
   }
}
