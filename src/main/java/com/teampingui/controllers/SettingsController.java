package com.teampingui.controllers;

import com.teampingui.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

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

     @FXML
     void saveChanges(ActionEvent e){
        //get new name
        String newName= taName.getText();
        //set new name

        //get new date format
        //Set new date format
    }
}
