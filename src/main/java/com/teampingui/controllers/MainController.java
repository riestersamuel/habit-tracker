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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            new JournalEntry("2022-05-13","Today was a nice day. I learned that sometimes, you just have to stay positive."),
            new JournalEntry("2022-05-12","Insight: Coding isn't as hard as I thought it would be."),
            new JournalEntry("2022-05-11","Very stressful day, waiting for the weekend."),
            new JournalEntry("2022-05-10","Started a project today - I'm excited for what it turns out to become!")
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
        int length = taNewJournal.getText().trim().length();
        if (length > 200 || length <= 0) {
            // TODO: Display hint on textarea
            // TODO: Display number of chars and max chars (e.g.: 33/200)
            System.out.println(length == 0 ? "The text can not be empty" : "Text is too long (max. 200 chars)");
        } else {
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            JournalEntry testEntry = new JournalEntry(
                    currentDate,
                    taNewJournal.getText().trim());
            lvJournal.getItems().add(0, testEntry);
            taNewJournal.clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Journal
        lvJournal.setItems(journalObservableList);
        lvJournal.setCellFactory(studentListView -> new JournalEntryListViewCell());

        // journal entry max length
        final int MAX_CHARS = 200;
        taNewJournal.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));

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