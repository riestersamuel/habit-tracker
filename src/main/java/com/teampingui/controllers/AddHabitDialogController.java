package com.teampingui.controllers;

import com.teampingui.dao.HabitDAO;
import com.teampingui.models.Habit;
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

    private HabitDAO mHabitDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Textformatter
        int MAX_CHARS = 15;
        tfNewHabitName.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= MAX_CHARS ? change : null));
    }

    @FXML
    void addNewHabit(ActionEvent e) {
        String name = tfNewHabitName.getText().trim();
        if (name.length() > 15 || name.length() <= 0) {
            // TODO: Add Error Message?
            return;
        } else {
            name = tfNewHabitName.getText().trim();
            tfNewHabitName.clear();
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        closeStage(e);
    }

    public void setHabitDAO(HabitDAO habitDAO) {
        this.mHabitDAO = habitDAO;
    }

    public void closeStage(ActionEvent e) {
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
