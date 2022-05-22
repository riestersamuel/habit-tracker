package com.teampingui.dao;

import com.teampingui.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;

public class Database {
    //Initializing the logger
    private static Logger log = LogManager.getLogger(Main.class);

    /**
    * Location of database
    */
  public static final String location = Main.class.getResource("/database/database.db").toExternalForm();

    /**
     * Reuired Table for Programm to work
     */
    private static final String requiredTable ="test";

    public static boolean isOK() {
        if (!checkDrivers()) return false;
        if(!checkConnection()) return false;
        return checkTables();
    }

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.sqlite.JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            log.error(LocalDateTime.now() + ": Could not start SQLite Drivers");
            return false;
        }
    }

    private static boolean checkConnection() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            log.error(LocalDateTime.now() + ": Could not connect to database");
            return false;
        }
    }

   private static boolean checkTables() {
        String checkTables = "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + requiredTable + "'";

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(checkTables);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("tbl_name").equals(requiredTable)) return true;
            }
        } catch (SQLException exception) {
            log.error(LocalDateTime.now() + ": Could not find tables in database");
            return false;
        }
        return false;
    }

    protected static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + location);
        } catch (SQLException exception) {
            log.error(LocalDateTime.now() + ": Could not connect to SQLite DB at " +location);
            return null;
        }
        return connection;
    }

}
