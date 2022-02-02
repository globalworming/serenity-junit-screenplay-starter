package com.example.component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sqlitetutorial.net
 */
public class Connect {
  /**
   * Connect to a sample database
   */
  public static void connect() {
    Connection conn = null;
    try {
      // db parameters
      String url = "jdbc:sqlite:/home/wormi/workspace/serenity/serenity-junit-screenplay-starter-java/test.db";
      // create a connection to the database
      conn = DriverManager.getConnection(url);
      System.out.println("Connection to SQLite has been established.");

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
    }
  }
}