package com.teampingui.controllers;

import com.teampingui.Main;
import com.teampingui.dao.HabitDAO;
import com.teampingui.dao.JournalDAO;
import com.teampingui.interfaces.ICheckBoxClickListener;
import com.teampingui.models.DayCell;
import com.teampingui.models.Habit;
import com.teampingui.models.JournalEntry;
import com.teampingui.models.JournalEntryListViewCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    public ObservableList<Habit> habitObservableList;
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
    Button btnHabits, btnChallenge, btnSettings;
    @FXML
    private ProgressBar habitsProgress;
    @FXML
    private Label progressDisplay;
    private int haveTodoCounter = 0;
    private int doneCounter = 0;
    @FXML
    private TableView<Habit> tvHabits = new TableView<>();
    //Journal
    private ObservableList<JournalEntry> journalObservableList = FXCollections.observableArrayList(
            new JournalEntry("13.05.2022", "Today was a nice day. I learned that sometimes, you just have to stay positive."),
            new JournalEntry("12.05.2022", "Insight: Coding isn't as hard as I thought it would be."),
            new JournalEntry("11.05.2022", "Very stressful day, waiting for the weekend."),
            new JournalEntry("10.05.2022", "Started a project today - I'm excited for what it turns out to become!")
    );

    public MainController() {

        // Habits
        habitObservableList = FXCollections.observableArrayList();

        habitObservableList.addAll(
                new Habit(
                        "Könken",
                        new boolean[]{true, true, true, true, true, true, true},
                        new boolean[]{false, false, true, false, true, false, false}),
                new Habit(
                        "Könken",
                        new boolean[]{true, false, false, false, false, false, false},
                        new boolean[]{true, true, false, false, false, false, false}),
                new Habit(
                        "saufen",
                        new boolean[]{true, false, false, true, false, false, false},
                        new boolean[]{true, false, false, true, false, false, false}),
                new Habit(
                        "lesen",
                        new boolean[]{true, false, false, false, false, false, false},
                        new boolean[]{true, false, false, false, false, false, false}),
                new Habit(
                        "lernen",
                        new boolean[]{true, false, false, false, false, false, false},
                        new boolean[]{true, false, false, false, false, false, false})
        );
    }

    // journal lineCount
    private static int countLines(String str) {
        String[] lines = str.split("\r\n|\r|\n");
        return lines.length;
    }

    @FXML
    public void switchScenes(ActionEvent e) {
        Main.getInstance().sceneSwitch(e, btnHabits, btnChallenge, btnSettings);
    }

    public void hideMsg(ActionEvent e) {
        lErrorMsg.setVisible(false);
        btnHide.setVisible(false);
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

    @FXML
    void openHabitDialog(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddHabitDialog.fxml"));
        Parent parent = fxmlLoader.load();
        AddHabitDialogController dialogController = fxmlLoader.getController();
        dialogController.setMainHabitList(habitObservableList);

        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
        Stage stage = new Stage();
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Journal
        lvJournal.setItems(JournalDAO.getJournalEntrys());
        lvJournal.setCellFactory(studentListView -> new JournalEntryListViewCell());
        // journal entry max length
        final int MAX_CHARS = 200;
        //journal entry max rows
        final int MAX_LINES = 7;
        taNewJournal.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS && countLines(change.getControlNewText()) <= MAX_LINES ? change : null));


        //journal wordCount
        wordCount.textProperty().bind(taNewJournal.textProperty().length().asString("%d/" + MAX_CHARS));

        // Habits
        dynamicallyAddTableCols();

        //tvHabits.setItems(HabitDAO.getHabits());
        tvHabits.setItems(habitObservableList);
        tvHabits.setEditable(true);

        // Set ProgressBar // TODO: rework
        for (Habit h: habitObservableList) {
            haveTodoCounter += h.repsProperty().getValue();
            for (int i = 0; i < 7; i++) {
                if (h.checkedDays(i).getValue() && h.hasToBeDone(i)) {
                    doneCounter++;
                }
            }
        }
        double percentage = (double)doneCounter / haveTodoCounter;
        habitsProgress.setProgress(percentage);
        progressDisplay.setText((int)(percentage*100)+"% achieved");
    }

    private void dynamicallyAddTableCols() {
        // Column: habit name
        TableColumn<Habit, String> tcName = new TableColumn<>("Habit");
        tcName.setPrefWidth(158);
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Columns: days checkboxes
        ArrayList<TableColumn<Habit, Boolean>> alCheckboxes = new ArrayList<>();
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (int i = 0; i < days.length; i++) {
            TableColumn<Habit, Boolean> tc = new TableColumn<>(days[i]);
            tc.setPrefWidth(60);
            final int day = i;
            tc.setCellValueFactory(habitBooleanCellDataFeatures -> habitBooleanCellDataFeatures.getValue().checkedDays(day));
            tc.setCellFactory(checkbox -> new DayCell(new ICheckBoxClickListener() {
                @Override
                public void onPositionClicked(boolean isChecked, Habit habit) {
                    checkboxClicked(isChecked, habit, day);
                }
            }));
            alCheckboxes.add(tc);
        }

        // Columns: repetitions
        TableColumn<Habit, Integer> tcReps = new TableColumn<>("Reps");
        tcReps.setCellValueFactory(new PropertyValueFactory<>("reps"));
        tcReps.setPrefWidth(85);

        // Cols
        ObservableList<TableColumn<Habit, ?>> cols = tvHabits.getColumns();

        // add cols to table
        cols.add(tcName);
        cols.addAll(alCheckboxes);
        cols.add(tcReps);
    }

    /**
     * @param isChecked if the checkbox is checked its true
     * @param habit     the habit which belongs to the clicked checkbox (same row)
     * @param day       (0=Monday 7=Sunday) shows wich checkbox is clicked
     */
    private void checkboxClicked(boolean isChecked, Habit habit, int day) {
        // System.out.println("Checked: " + isChecked);
        // System.out.println("Day: " + day);
        // System.out.println("Habit: " + habit.repsProperty().getValue());

        if (habit.hasToBeDone(day)) {
            doneCounter += isChecked ? 1 : -1;
            double percentage = (double)doneCounter / haveTodoCounter;
            habitsProgress.setProgress(percentage);
            progressDisplay.setText((int)(percentage*100)+"% achieved");
        }
    }
}