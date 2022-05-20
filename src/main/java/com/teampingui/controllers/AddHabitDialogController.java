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
    CheckBox cbMonday,cbTuesday,cbWednesday,cbThursday,cbFriday,cbSaturday,cbSunday;
    @FXML
    Label lAddHabitHeading;
    @FXML
    TextField tfNewHabitName;

    private ObservableList<Habit> tempHabitList;

    @FXML
    void addNewHabit(ActionEvent e) {
        int length = tfNewHabitName.getText().trim().length();
        String name = "";
        if (length > 15 || length <= 0) {
            //lErrorMsg.setVisible(true);
            // btnHide.setVisible(true);
            // lErrorMsg.setText(length == 0 ? "The text can not be empty" : "Text is too long (max. 200 chars)");
        } else {
            name = tfNewHabitName.getText().trim();
            tfNewHabitName.clear();
        }

        tempHabitList.add(new Habit(
                name,
                new boolean[]{cbMonday.isSelected(), cbTuesday.isSelected(), cbWednesday.isSelected(), cbThursday.isSelected(), cbFriday.isSelected(), cbSaturday.isSelected(), cbSunday.isSelected()},
                new boolean[]{false, true, false, true, false, true, false}));

        closeStage(e);
    }

    public void setMainHabitList(ObservableList<Habit> habitObservableList) {
        this.tempHabitList = habitObservableList;
    }

    public void closeStage(ActionEvent e) {
        Node source = (Node)  e.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Textformater
        int MAX_CHARS=15;
        tfNewHabitName.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= MAX_CHARS  ? change : null));



    }
}
