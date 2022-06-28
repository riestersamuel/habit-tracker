package com.teampingui.controllers;

import com.teampingui.dao.HabitDAO;
import com.teampingui.models.Day;
import com.teampingui.models.Habit;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddHabitDialogController implements Initializable {
    @FXML
    CheckBox cbMonday, cbTuesday, cbWednesday, cbThursday, cbFriday, cbSaturday, cbSunday; // TODO: instead of adding each checkbox, just add the container and access checkboxes through .getItems()
    //@FXML
    //ListView<CheckBox> lvWeekdays;
    @FXML
    Label lAddHabitHeading, lErrorMsgHabit;
    @FXML
    TextField tfNewHabitName;
    @FXML
    VBox vbErrorContainer;
    @FXML
    ProgressBar pbErrorDuration;
    private static final Integer ERROR_DIALOG_TIME = 3;
    private Timeline mTimeline;
    private final IntegerProperty mDialogTime = new SimpleIntegerProperty(ERROR_DIALOG_TIME * 100);

    private ObservableList<CheckBox> checkBoxes = FXCollections.observableArrayList();

    private Thread mThreadErrorMsg;

    private static final Logger log = LogManager.getLogger(MainController.class);


    private HabitDAO mHabitDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Add Checkboxes
//        for (Day d : Day.values()) {
//            checkBoxes.add(new CheckBox(d.getDay()));
//            lvWeekdays.setItems(checkBoxes);
//        }

        //Progressbar
        pbErrorDuration.progressProperty().bind(mDialogTime.divide(ERROR_DIALOG_TIME * 100.0));
        // Textformatter
        int MAX_CHARS = 15;
        tfNewHabitName.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= MAX_CHARS ? change : null));
    }

    @FXML
    void addNewHabit(ActionEvent e) {
        String name = tfNewHabitName.getText().trim();
        if (name.length() > 15 || name.length() <= 0) {
            showError("Inputfield can not be empty!");
            log.warn("Inputfield can not be empty!");
            return;
        }
        else if(!(cbMonday.isSelected()||cbTuesday.isSelected()||cbWednesday.isSelected()||cbThursday.isSelected()||cbFriday.isSelected()||cbSaturday.isSelected()||cbSunday.isSelected())){
            showError("You have to select at least 1 day");
            log.warn("You have to select at least 1 day");
            tfNewHabitName.clear();
            return;
        }else {
            name = tfNewHabitName.getText().trim();
            tfNewHabitName.clear();
            log.info("New habit '" + name + "' was added successfully.");
        }

        boolean[] havetodos = new boolean[]{
                cbMonday.isSelected(),
                cbTuesday.isSelected(),
                cbWednesday.isSelected(),
                cbThursday.isSelected(),
                cbFriday.isSelected(),
                cbSaturday.isSelected(),
                cbSunday.isSelected()
        };

        try {
            mHabitDAO.insert(new Habit(name, havetodos));
            log.info("Successfully inserted " + name + " to database.");
        } catch (Exception ex) {
            log.error("Failed to insert habit!" + ex.getMessage());
        }
        closeStage(e);
    }

    public void setHabitDAO(HabitDAO habitDAO) {this.mHabitDAO = habitDAO;}

    public void showError(String msg) {
        vbErrorContainer.setVisible(true);
        lErrorMsgHabit.setText(msg);

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

        Runnable runnable = () -> {
            try {
                Thread.sleep(ERROR_DIALOG_TIME * 1000L);
                Platform.runLater(() -> vbErrorContainer.setVisible(false));
            } catch (InterruptedException e) {
                log.debug("Interrupted: " + e.getMessage());
            }
        };

        if (mThreadErrorMsg != null && mThreadErrorMsg.isAlive()) {
            mThreadErrorMsg.interrupt();
        }

        mThreadErrorMsg = new Thread(runnable);
        mThreadErrorMsg.start();
    }

    public void closeStage(ActionEvent e) {
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
