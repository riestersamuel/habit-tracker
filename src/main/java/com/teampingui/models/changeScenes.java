package com.teampingui.models;

import com.teampingui.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class changeScenes {

    public static void sceneSwitch(ActionEvent e, Button btnHabits, Button btnChallenge, Button btnSettings) {
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
