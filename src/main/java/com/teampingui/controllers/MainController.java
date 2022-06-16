package com.teampingui.controllers;

import com.teampingui.Main;
import com.teampingui.dao.HabitDAO;
import com.teampingui.dao.JournalDAO;
import com.teampingui.models.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    private static final Logger log = LogManager.getLogger(MainController.class);
    // Error Message
    private static final Integer ERROR_DIALOG_TIME = 3;
    //General Layout
    @FXML
    Label lWelcome;
    @FXML
    Label lMiniJournal;
    @FXML
    VBox vbErrorContainer;
    @FXML
    Label lErrorMsg;
    @FXML
    ProgressBar pbErrorDuration;
    //Journal
    @FXML
    TextArea taNewJournal; // Hier auslesen
    @FXML
    Button btnAddJournal;
    @FXML
    Label wordCount;
    @FXML
    ListView<JournalEntryItem> lvJournal;
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
    private Timeline mTimeline;
    private IntegerProperty mDialogTime = new SimpleIntegerProperty(ERROR_DIALOG_TIME * 100);
    private Thread mThreadErrorMsg;


    // DAO
    private final JournalDAO mJournalDAO = new JournalDAO();
    private final HabitDAO mHabitDAO = new HabitDAO();

    public MainController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Bind Error Msg Time to Progress Bar
        pbErrorDuration.progressProperty().bind(mDialogTime.divide(ERROR_DIALOG_TIME * 100.0));

        // Welcome Message
        lWelcome.setText(Settings.getUsername().isEmpty() ?  "Welcome!" : "Welcome " + Settings.getUsername() + "!");

        // Journal

        lvJournal.setItems(mJournalDAO.getAll());
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
        tvHabits.setItems(mHabitDAO.getAll());
        tvHabits.setEditable(true);

        updateProgressBar();
        mHabitDAO.getAll().addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                updateProgressBar();
            }
        });
    }

    private void updateProgressBar() {
        if (progressDisplay == null)
            return;

        // Set ProgressBar // TODO: rework
        doneCounter = 0;
        haveTodoCounter = 0;
        for (Habit h : mHabitDAO.getAll()) {
            haveTodoCounter += h.repsProperty().getValue();
            for (Day day : Day.values()) {
                if (h.checkedDays(day).getValue() && h.hasToBeDone(day)) {
                    doneCounter++;
                }
            }
        }
        double percentage = (double) doneCounter / haveTodoCounter;
        habitsProgress.setProgress(percentage);
        progressDisplay.setText((int) (percentage * 100) + "% achieved");
    }

    private static int countLines(String str) {
        String[] lines = str.split("\r\n|\r|\n");
        return lines.length;
    }

    @FXML
    public void switchScenes(ActionEvent e) {
        Main.getInstance().sceneSwitch(e, btnHabits, btnChallenge, btnSettings);
    }

    @FXML
    private void addNewEntry() {
        String sEntry = taNewJournal.getText().trim();

        // Text too long
        if (sEntry.length() > 200) {
            showError("Text is too long (max. 200 chars)");
            return;
        }

        // Text empty
        if (sEntry.length() <= 0) {
            showError("Input field can not be empty!");
            return;
        }

        String sCurrentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        JournalEntryItem newJournalEntry = new JournalEntryItem(sCurrentDate, sEntry);

        try {
            mJournalDAO.insert(newJournalEntry);
            lvJournal.getItems().add(0, newJournalEntry);
            taNewJournal.clear();
        } catch (Exception exception) {
            log.error(exception);
        }

    }

    @FXML
    void openHabitDialog(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddHabitDialog.fxml"));
        Parent parent = fxmlLoader.load();
        AddHabitDialogController dialogController = fxmlLoader.getController();
        dialogController.setMainHabitList(mHabitDAO.getAll());

        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
        Stage stage = new Stage();
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void dynamicallyAddTableCols() {
        // Column: habit name
        TableColumn<Habit, String> tcName = new TableColumn<>("Habit");
        tcName.setPrefWidth(158);
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Columns: days checkboxes
        ArrayList<TableColumn<Habit, Boolean>> alCheckboxes = new ArrayList<>();
        for (final Day day : Day.values()) {
            TableColumn<Habit, Boolean> tc = new TableColumn<>(day.getDay());
            tc.setPrefWidth(60);
            tc.setCellValueFactory(habitBooleanCellDataFeatures -> habitBooleanCellDataFeatures.getValue().checkedDays(day));
            tc.setCellFactory(checkbox -> new DayCell((isChecked, habit) -> checkboxClicked(isChecked, habit, day), day));
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
     * @param day       shows wich checkbox is clicked
     */
    private void checkboxClicked(boolean isChecked, Habit habit, Day day) {
        // System.out.println("Checked: " + isChecked);
        // System.out.println("Day: " + day);
        // System.out.println("Habit: " + habit.repsProperty().getValue());

        if (habit.hasToBeDone(day)) { // TODO: cleanup
            doneCounter += isChecked ? 1 : -1;
            double percentage = (double) doneCounter / haveTodoCounter;
            habitsProgress.setProgress(percentage);
            progressDisplay.setText((int) (percentage * 100) + "% achieved");

            // Observable
            int habitIndex = mHabitDAO.indexOf(habit);
            Optional<Habit> updateHabit = mHabitDAO.get(habitIndex);
            updateHabit.ifPresent(value -> value.setChecked(day, isChecked));
        }
    }

    private void showError(String msg) { // TODO: exclude?
        vbErrorContainer.setVisible(true);
        lErrorMsg.setText(msg);

        if (mTimeline != null) {
            mTimeline.stop();
        }

        mDialogTime.set(ERROR_DIALOG_TIME * 100);
        mTimeline = new Timeline();
        mTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(ERROR_DIALOG_TIME),
                        new KeyValue(mDialogTime, 0))
        );
        mTimeline.playFromStart();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(ERROR_DIALOG_TIME * 1000L);
                    Platform.runLater(() -> vbErrorContainer.setVisible(false));
                } catch (InterruptedException e) {
                    log.debug("Interrupted");
                }
            }
        };

        if (mThreadErrorMsg != null && mThreadErrorMsg.isAlive()) {
            mThreadErrorMsg.interrupt();
        }

        mThreadErrorMsg = new Thread(runnable);
        mThreadErrorMsg.start();
    }

}