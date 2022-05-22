package com.teampingui;

import com.teampingui.dao.Database;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;

//Zu entfernen
import static com.teampingui.dao.Database.location;

public class Main extends Application {

    //Initializing the logger
    private static Logger log = LogManager.getLogger(Main.class);
    // Singleton
    private static Main instance;
    private Stage stage;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    public static void main(String[] args) throws SQLException {
        //DB Test -> Später zu entfernen
        String dbPrefix = "jdbc:sqlite:";
        Connection con = DriverManager.getConnection(dbPrefix + location);
        String query = "SELECT * FROM test";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            System.out.print(rs.getInt("id") + ": ");
            System.out.print(rs.getString("word") + " ");
        }

        rs.close();
        stmt.close();
        con.close();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        if (Database.isOK()) {
            try {
                stage = primaryStage;
                gotoMain();
                primaryStage.show();
            } catch (Exception ex) {
                log.error("Failed to show the primary stage (" + ex + ").");
            }
        } else {
            log.error(LocalDateTime.now() + ": Database could not be loaded");
        }
    }

    public void gotoMain() {
        try {
            replaceSceneContent("Main.fxml");
        } catch (Exception ex) {
            log.error("Failed to replace the scene content with the main page (" + ex + ").");
        }
    }

    public void gotoSettings() {
        try {
            replaceSceneContent("Settings.fxml");
        } catch (Exception ex) {
            log.error("Failed to show the scene content with the settings (" + ex + ").");
        }
    }

    private void replaceSceneContent(String fxml) throws Exception {
        // Was ist Parent für ein Datentyp und was kann/soll er machen?
        FXMLLoader lloader = new FXMLLoader();
        lloader.setLocation(getClass().getResource("/fxml/" + fxml));
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

    public void sceneSwitch(ActionEvent e, Button btnHabits, Button btnChallenge, Button btnSettings) {
        Object source = e.getSource();
        if (btnHabits.equals(source)) {
            gotoMain();
        } else if (btnSettings.equals(source)) {
            gotoSettings();
        } else {
            throw new IllegalStateException("Unexpected value: " + e.getSource());
        }
    }
}
