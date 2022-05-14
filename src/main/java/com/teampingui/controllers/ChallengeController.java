package com.teampingui.controllers;

import com.teampingui.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ChallengeController {
    @FXML
    Button btnHabits, btnChallenge, btnSettings;

    @FXML
    public void switchScenes(ActionEvent e) { // TODO: dont put this in every Controller => rework smarter
        Main main = Main.getInstance();
        Object source = e.getSource();
        if (btnHabits.equals(source)) {
            main.gotoMain();
        } else if (btnChallenge.equals(source)) {
            main.gotoChallenge();
        } else if (btnSettings.equals(source)) {
            main.gotoSettings();
        } else {
            throw new IllegalStateException("Unexpected value: " + e.getSource());
        }
    }
}
