package com.teampingui.controllers;

import com.teampingui.models.Habit;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddHabitDialogController implements Initializable {
    @FXML
    CheckBox cbMonday, cbTuesday, cbWednesday, cbThursday, cbFriday, cbSaturday, cbSunday; // TODO: instead of adding each checkbox, just add the container and access checkboxes through .getItems()
    @FXML
    Label lAddHabitHeading;
    @FXML
    TextField tfNewHabitName;

    private ObservableList<Habit> mMainCtrlHabitList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Textformatter
        int MAX_CHARS = 15;
        tfNewHabitName.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= MAX_CHARS ? change : null));
    }

    @FXML
    void addNewHabit(ActionEvent e) {
        String name= tfNewHabitName.getText().trim();
        if (name.length() > 15 || name.length() <= 0) {
            // TODO: Add Error Message?
            return;
        } else {
            name = tfNewHabitName.getText().trim();
            tfNewHabitName.clear();
        }

        mMainCtrlHabitList.add(new Habit(
                name,
                new boolean[]{
                        cbMonday.isSelected(),
                        cbTuesday.isSelected(),
                        cbWednesday.isSelected(),
                        cbThursday.isSelected(),
                        cbFriday.isSelected(),
                        cbSaturday.isSelected(),
                        cbSunday.isSelected()
                })
        );

        closeStage(e);
    }

    public void setMainHabitList(ObservableList<Habit> habitObservableList) {
        this.mMainCtrlHabitList = habitObservableList;
    }

    public void closeStage(ActionEvent e) {
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
