package com.teampingui.controllers;

import com.teampingui.Main;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static com.teampingui.models.changeScenes.sceneSwitch;

public class MainController implements Initializable {

    //General Layout
    @FXML
    Label lWelcome;
    @FXML
    Label lMiniJournal;
    @FXML
    Label lErrorMsg;
    @FXML
    Button btnHide;

    //Journal
    @FXML
    TextArea taNewJournal; // Hier auslesen
    @FXML
    Button btnAddJournal;
    @FXML
    Label wordCount;
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
    public void switchScenes(ActionEvent e) {
        sceneSwitch(e, btnHabits, btnChallenge, btnSettings);
    }

    public void hideMsg(ActionEvent e){
        lErrorMsg.setVisible(false);
        btnHide.setVisible(false);
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
    private void addNewEntry() {
        int length = taNewJournal.getText().trim().length();
        if (length > 200 || length <= 0) {
            lErrorMsg.setVisible(true);
            btnHide.setVisible(true);
            lErrorMsg.setText(length == 0 ? "The text can not be empty" : "Text is too long (max. 200 chars)");
        } else {
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            JournalEntry testEntry = new JournalEntry(
                    currentDate,
                    taNewJournal.getText().trim());
            lvJournal.getItems().add(0, testEntry);
            taNewJournal.clear();
        }
    }
    //journal rowCount
    private static int countLines(String str){
        String[] lines = str.split("\r\n|\r|\n");
        return  lines.length;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Journal
        lvJournal.setItems(journalObservableList);
        lvJournal.setCellFactory(studentListView -> new JournalEntryListViewCell());
        // journal entry max length
        final int MAX_CHARS = 200;
        //journal entry max rows
        final int MAX_LINES = 7;
        taNewJournal.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));
        //journal rowCount
        taNewJournal.setTextFormatter(new TextFormatter<String>(change ->
                countLines(change.getControlNewText()) <= MAX_LINES ? change : null));

        //journal wordCount
        wordCount.textProperty().bind(taNewJournal.textProperty().length().asString("%d/"+MAX_CHARS));

        // Habits
       TableColumn tcName = tvHabits.getColumns().get(0);
       tcName.setCellValueFactory(new PropertyValueFactory<Habit, String>("name"));

        TableColumn tcDay;
       for (int i = 1; i <= 7; i++) {
            tcDay = tvHabits.getColumns().get(i);
            tcDay.setCellValueFactory(new PropertyValueFactory<Habit, Boolean>("checked"));
            tcDay.setCellFactory(tc -> new CheckBoxTableCell<>());

            /* Versuch eines Listeners für die Checkboxen....Betonung liegt auf VERSUCH!

            CheckBoxTableCell.forTableColumn(new Callback<Integer, ObservableValue<Boolean>>() {

                @Override
                public ObservableValue<Boolean> call(Integer param) {
                    System.out.println("Habit "+tcDay.get(param).getName()+" changed value to " +tcDay.get(param).isChecked());
                    return tcDay.get(param).checkedProperty();
                }
            })); */
        }

        TableColumn tcReps = tvHabits.getColumns().get(8);
        tcReps.setCellValueFactory(new PropertyValueFactory<Habit, Integer>("reps"));

        tvHabits.setItems(habitObservableList);
        tvHabits.setEditable(true);
    }
}