package com.teampingui.controllers;

import com.teampingui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import com.teampingui.models.Habit;
import com.teampingui.models.JournalEntry;
import com.teampingui.models.JournalEntryListViewCell;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainController implements Initializable {

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

    //Journal
    private ObservableList<JournalEntry> journalObservableList= FXCollections.observableArrayList(
            new JournalEntry("24.05.2022","Baba Tag heute! Ein ganz langer Eintrag"),
            new JournalEntry("24.05.2022","Baba Tag heute!"),
            new JournalEntry("24.05.2022","Baba Tag heute!"),
            new JournalEntry("24.05.2022","Baba Tag heute!")
    );
    public MainController() {

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
        lvJournal.setCellFactory(studentListView -> new JournalEntryListViewCell());

        // Habits
       TableColumn tcName = tvHabits.getColumns().get(0);
       tcName.setCellValueFactory(new PropertyValueFactory<Habit, String>("name"));

        for (int i = 1; i <= 7; i++) {
            TableColumn tcDay = tvHabits.getColumns().get(i);
            tcDay.setCellValueFactory(new PropertyValueFactory<Habit, String>("done"));
            tcDay.setCellFactory(tc -> new CheckBoxTableCell<>());
        }

        // TableColumn tcReps = tvHabits.getColumns().get(8);
        // tcReps.setCellValueFactory(new PropertyValueFactory<Habit, Integer>("reps"));

        tvHabits.setItems(habitObservableList);
        tvHabits.setEditable(true);
    }
}