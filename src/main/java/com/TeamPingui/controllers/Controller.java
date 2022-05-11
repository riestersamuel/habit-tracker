package com.TeamPingui.controllers;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.TeamPingui.Models.Habit;
import com.TeamPingui.Models.JournalEntry;
import com.TeamPingui.Models.JournalEntryListViewCell;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //General Layout
    @FXML
    Label lWelcome;
    @FXML
    Label lMiniJournal;

    //Journal
    @FXML
    TextArea taNewJournal; // Hier auslesen
    @FXML
    Button btnAddJournal;
    @FXML
    ListView<JournalEntry> lvJournal;

    //Habits
    @FXML
    Button btnAddHabit;
    @FXML
    Button btnRemoveHabit;
    @FXML
    private ProgressBar habitsProgress;
    @FXML
    private Label progressDisplay;

    private double progress;

    private final CheckBoxTableCell checkbox = new CheckBoxTableCell<>();
    @FXML
    private TableView<Habit> tvHabits = new TableView<>();

    private ObservableList<Habit> habitObservableList;


    private LocalDate date;

    //Switch Scenes
    @FXML
    Button btnHabits, btnChallenge, btnSettings;
    private Stage stage;
    private Scene scene;

    Parent root;

    public void switchScenes(ActionEvent e) throws IOException {
       if (e.getSource() == btnHabits) {
           root = FXMLLoader.load(getClass().getResource("com.TeamPingui.Main.fxml"));
           stage = (Stage)((Node)e.getSource()).getScene().getWindow();
       } else if (e.getSource() == btnChallenge) {
           root = FXMLLoader.load(getClass().getResource("Challenge.fxml"));
           stage = (Stage)((Node)e.getSource()).getScene().getWindow();
       } else {
           root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
           stage = (Stage)((Node)e.getSource()).getScene().getWindow();
       }

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Journal
    private ObservableList<JournalEntry> journalObservableList= FXCollections.observableArrayList(
            new JournalEntry("24.05.2022","Baba Tag heute! Ein ganz langer Eintrag"),
            new JournalEntry("24.05.2022","Baba Tag heute!"),
            new JournalEntry("24.05.2022","Baba Tag heute!"),
            new JournalEntry("24.05.2022","Baba Tag heute!")
    );
    public Controller() {

        // Habits
        habitObservableList = FXCollections.observableArrayList();

        habitObservableList.addAll(
                new Habit("Könken", true, 1),
                new Habit("Lesen", true, 4),
                new Habit("Lernen", true, 5),
                new Habit("Sport", true, 7),
                new Habit("Ordentlich abschießen", true, 4),
                new Habit("Wasser trinken", true, 3)
        );
    }

    @FXML
    private void addNewEntry() throws Exception {
        // taNewJournal.setTextFormatter(new TextFormatter<String>(change ->
        //         change.getControlNewText().length() <= MAX_CHARS ? change : null));

        if (taNewJournal.getLength() <= 200) {
            lvJournal.getItems().add(0, new JournalEntry(date.now().toString(),taNewJournal.getText().trim()));
        } else {
            throw new Exception("The entry is greater than 200 Characters");
        }


        taNewJournal.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Journal
        lvJournal.setItems(journalObservableList);
        // Lambda Method: Defining an implementation of a functional Interface
        lvJournal.setCellFactory(studentListView -> new JournalEntryListViewCell());

        // Habits
       /*TableColumn tcName = tvHabits.getColumns().get(0);
       tcName.setCellValueFactory(new PropertyValueFactory<Habit, String>("name"));

        for (int i = 1; i <= 7; i++) {
            TableColumn tcDay = tvHabits.getColumns().get(i);
            tcDay.setCellValueFactory(new PropertyValueFactory<Habit, String>("done"));
            tcDay.setCellFactory(tc -> new CheckBoxTableCell<>());
        }*/

        // TableColumn tcReps = tvHabits.getColumns().get(8);
        // tcReps.setCellValueFactory(new PropertyValueFactory<Habit, Integer>("reps"));

        tvHabits.setItems(habitObservableList);
        tvHabits.setEditable(true);
    }
}