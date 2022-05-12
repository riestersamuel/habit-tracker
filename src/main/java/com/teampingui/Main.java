package com.teampingui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class Main extends Application {

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
            // TODO: Create log here
        }
    }

    public void gotoMain() {
        try {
            replaceSceneContent("Main.fxml");
        } catch (Exception ex) {
            // TODO: Create log here
        }
    }

    public void gotoChallenge() {
        try {
            replaceSceneContent("Challenge.fxml");
        } catch (Exception ex) {
            // TODO: Create log here
        }
    }

    public void gotoSettings() {
        try {
            replaceSceneContent("Settings.fxml");
        } catch (Exception ex) {
            // TODO: Create log here
        }
    }

    private Parent replaceSceneContent(String fxml) throws Exception {
        URL fxmlFileUrl = getClass().getClassLoader().getResource(fxml);
        Parent page = FXMLLoader.load(Objects.requireNonNull(fxmlFileUrl));
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page);
            //scene.getStylesheets().add(Main.class.getResource("demo.css").toExternalForm());
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
