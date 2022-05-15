package com.teampingui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Objects;

public class Main extends Application {

    //Initializing the logger
    private static Logger log = LogManager.getLogger(Main.class);

    private Stage stage;

    // Singleton
    private static Main instance;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            gotoMain();
            primaryStage.show();
        } catch (Exception ex) {
            log.error("Failed to show the primary stage");
        }
    }

    public void gotoMain() {
        try {
            replaceSceneContent("Main.fxml");
        } catch (Exception ex) {
            log.error("Failed to replace the scene content with the main page.");
        }
    }

    public void gotoChallenge() {
        try {
            replaceSceneContent("Challenge.fxml");
        } catch (Exception ex) {
            log.error("Failed to replace the scene content with the challenge page.");
        }
    }

    public void gotoSettings() {
        try {
            replaceSceneContent("Settings.fxml");
        } catch (Exception ex) {
            log.error("Failed to show the scene content with the settings.");
        }
    }

    private void replaceSceneContent(String fxml) throws Exception {
        // Was ist Parent für ein Datentyp und was kann/soll er machen?
        FXMLLoader lloader = new FXMLLoader();
        lloader.setLocation(getClass().getResource("/fxml/"+fxml));
        Parent page = lloader.load();
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page);
            //Importing our own css sheet
            scene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
