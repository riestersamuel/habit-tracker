package com.teampingui.controllers;

import com.teampingui.dao.HabitDAO;
import com.teampingui.interfaces.IDao;
import com.teampingui.models.Day;
import com.teampingui.models.ErrorDialog;
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
import java.util.ResourceBundle;

public class AddHabitDialogController implements Initializable {
    private static final Integer ERROR_DIALOG_TIME = 3;
    private static final Logger log = LogManager.getLogger(MainController.class);
    private final IntegerProperty mDialogTime = new SimpleIntegerProperty(ERROR_DIALOG_TIME * 100);
    @FXML
    DialogPane dpRoot;
    @FXML
    ListView<CheckBox> lvWeekdays;
    @FXML
    Label lAddHabitHeading, lErrorMsgHabit;
    @FXML
    TextField tfNewHabitName;

    private final ObservableList<CheckBox> checkBoxes = FXCollections.observableArrayList();

    private IDao<Habit> mHabitDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Add Checkboxes
        for (Day d : Day.values()) {
            checkBoxes.add(new CheckBox(d.getDay()));
        }
        lvWeekdays.setItems(checkBoxes);

        // Textformatter
        int MAX_CHARS = 15;
        tfNewHabitName.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= MAX_CHARS ? change : null));
    }

    @FXML
    void addNewHabit(ActionEvent e) {
        String name = tfNewHabitName.getText().trim();
        if (name.length() == 0) {
            ErrorDialog eDialog = new ErrorDialog(dpRoot, "Inputfield can not be empty!");
            eDialog.show();
            //showError("Inputfield can not be empty!");
            log.warn("Inputfield can not be empty!");
            return;
        }

        boolean someSelected = false;
        for (CheckBox cb : checkBoxes) {
            if (cb.isSelected()) {
                someSelected = true;
                break;
            }
        }
        if (!someSelected) {
            ErrorDialog eDialog = new ErrorDialog(dpRoot, "You have to select at least one day");
            eDialog.show();
            //showError("You have to select at least 1 day");
            log.warn("You have to select at least 1 day");
            return;
        }
        name = tfNewHabitName.getText().trim();
        tfNewHabitName.clear();
        log.info("New habit '" + name + "' was added successfully.");


        boolean[] havetodos = new boolean[7];
        for (int i = 0; i < havetodos.length; i++) {
            havetodos[i] = checkBoxes.get(i).isSelected();
        }

        try {
            int dbID = mHabitDAO.insert(new Habit(name, havetodos));
            log.info("Successfully inserted habit to database. ID=" + dbID);
        } catch (Exception ex) {
            log.error("Failed to insert habit!" + ex.getMessage());
        }
        closeStage(e);
    }

    public void setHabitDAO(IDao<Habit> habitDAO) {
        this.mHabitDAO = habitDAO;
    }

    public void closeStage(ActionEvent e) {
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}

