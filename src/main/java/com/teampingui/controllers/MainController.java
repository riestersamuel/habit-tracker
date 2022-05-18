package com.teampingui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import com.teampingui.models.Habit;
import com.teampingui.models.JournalEntry;
import com.teampingui.models.JournalEntryListViewCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
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
    @FXML
    private TableView<Habit> tvHabits = new TableView<>();

    public ObservableList<Habit> habitObservableList;

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
            new JournalEntry("13.05.2022","Today was a nice day. I learned that sometimes, you just have to stay positive."),
            new JournalEntry("12.05.2022","Insight: Coding isn't as hard as I thought it would be."),
            new JournalEntry("11.05.2022","Very stressful day, waiting for the weekend."),
            new JournalEntry("10.05.2022","Started a project today - I'm excited for what it turns out to become!")
    );
    public MainController() {

        // Habits
        habitObservableList = FXCollections.observableArrayList();

        habitObservableList.addAll(
                new Habit("Könken", true, false, false, 1),
                new Habit("Lesen", true, true, true,4),
                new Habit("Lernen", false, false, false, 5),
                new Habit("Sport", true, true, true, 7),
                new Habit("Ordentlich abschießen", false, false, true, 4),
                new Habit("Wasser trinken", true, true, false, 3)
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
    //journal linesCount
    private static int countLines(String str){
        String[] lines = str.split("\r\n|\r|\n");
        return  lines.length;
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
        lvJournal.setItems(journalObservableList);
        lvJournal.setCellFactory(studentListView -> new JournalEntryListViewCell());
        // journal entry max length
        final int MAX_CHARS = 200;
        //journal entry max lines
        final int MAX_LINES = 7;

        taNewJournal.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS && countLines(change.getControlNewText()) <= MAX_LINES  ? change : null));

        //journal wordCount
        wordCount.textProperty().bind(taNewJournal.textProperty().length().asString("%d/"+MAX_CHARS));

        // Habits
       TableColumn tcName = tvHabits.getColumns().get(0);
       tcName.setCellValueFactory(new PropertyValueFactory<Habit, String>("name"));

        TableColumn tcDay;

        tcDay = tvHabits.getColumns().get(1);
        tcDay.setCellValueFactory(new PropertyValueFactory<Habit, Boolean>("checkedMon"));
        tcDay.setCellFactory(tc -> new CheckBoxTableCell<>());

        tcDay = tvHabits.getColumns().get(2);
        tcDay.setCellValueFactory(new PropertyValueFactory<Habit, Boolean>("checkedTue"));
        tcDay.setCellFactory(tc -> new CheckBoxTableCell<>());

        tcDay = tvHabits.getColumns().get(3);
        tcDay.setCellValueFactory(new PropertyValueFactory<Habit, Boolean>("checkedWed"));
        tcDay.setCellFactory(tc -> new CheckBoxTableCell<>());

      /* for (int i = 1; i <= 7; i++) {
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
            }));
        } */

        TableColumn tcReps = tvHabits.getColumns().get(8);
        tcReps.setCellValueFactory(new PropertyValueFactory<Habit, Integer>("reps"));

        tvHabits.setItems(habitObservableList);
        tvHabits.setEditable(true);
    }

    public void setName(String newName){
        lWelcome.setText(newName);
    }
}