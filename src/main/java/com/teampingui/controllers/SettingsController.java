package com.teampingui.controllers;

import com.teampingui.Main;
import com.teampingui.models.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;


public class SettingsController implements Initializable {

    private static final Logger log = LogManager.getLogger(SettingsController.class);

    @FXML
    Button btnHabits, btnSettings;
    @FXML
    Label lName;
    @FXML
    TextField tfName;

    @FXML
    public void switchScenes(ActionEvent e) {
        Main.getInstance().sceneSwitch(e, btnHabits, btnSettings);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfName.setText(Settings.getUsername());
    }

    @FXML
    protected void saveChanges(ActionEvent e) {
        Settings.setUsername(tfName.getText().trim());
        log.info("Settings saved");
    }
}
