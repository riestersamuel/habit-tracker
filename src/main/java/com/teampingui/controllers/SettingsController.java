package com.teampingui.controllers;

import com.teampingui.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

import static com.teampingui.dao.Database.location;


public class SettingsController {

    @FXML
    Button btnHabits, btnChallenge, btnSettings;
    @FXML
    Label lName, lDateFormat;
    @FXML
    TextArea taName, taDateFormat;

    @FXML
    public void switchScenes(ActionEvent e) {
        Main.getInstance().sceneSwitch(e, btnHabits, btnChallenge, btnSettings);
    }


    //This method is used to save the user's name to the database
    @FXML
    void saveChanges(ActionEvent e) {
        try {
            String username = taName.getText();
            String dbPrefix = "jdbc:sqlite:";
            Connection con = DriverManager.getConnection(dbPrefix + location);
            String query = "INSERT INTO properties VALUES ('name', '" + username + "');";
            System.out.println("You sent the following query to the database: " + query);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.executeUpdate();

            stmt.close();
            con.close();
        }

        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }

    }
}
