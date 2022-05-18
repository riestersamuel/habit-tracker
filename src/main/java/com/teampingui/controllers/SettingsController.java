package com.teampingui.controllers;

import com.teampingui.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.*;

import static com.teampingui.models.changeScenes.sceneSwitch;

public class SettingsController {

    @FXML
    Button btnHabits, btnChallenge, btnSettings;
    @FXML
    Label lName, lDateFormat;
    @FXML
    TextArea taName, taDateFormat;

    @FXML
    public void switchScenes(ActionEvent e) {
        sceneSwitch(e, btnHabits, btnChallenge, btnSettings);
    }


    //Das hier war um den Nutzernamen in einer lokalen Datei zu speichern
    @FXML
    void saveChanges(ActionEvent e) {
        try {
            String username = taName.getText();
            FileWriter output = new FileWriter("properties.txt");
            output.write(username);
            output.close();
            System.out.println(username);
        }
        catch (IOException exception){
            System.out.println(exception);
        }
    }
}
