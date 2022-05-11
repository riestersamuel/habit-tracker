package com.TeamPingui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {
    //Switch Scenes
    @FXML
    Button btnHabits, btnChallenge, btnSettings;
    private Stage primaryStage;
    private Scene scene;

    public void switchToSceneHabits(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("com.TeamPingui.Main.fxml"));
        primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void switchToSceneChallenge(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Challenge.fxml"));
        primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void switchToSceneSettings(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
        primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
