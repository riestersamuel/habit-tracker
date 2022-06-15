package com.teampingui.controllers;

import com.teampingui.Main;
import com.teampingui.models.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class SettingsController implements Initializable {

    @FXML
    Button btnHabits, btnChallenge, btnSettings;
    @FXML
    Label lName, lDateFormat;
    @FXML
    TextField tfName, tfDateFormat;

    @FXML
    public void switchScenes(ActionEvent e) {
        Main.getInstance().sceneSwitch(e, btnHabits, btnChallenge, btnSettings);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfName.setText(Settings.getUsername());
        tfDateFormat.setText(Settings.getDateFormat());
    }

    @FXML
    void saveChanges(ActionEvent e) {
        Settings.setUsername(tfName.getText().trim());
        Settings.setDateFormat(tfDateFormat.getText().trim());
    }
}
