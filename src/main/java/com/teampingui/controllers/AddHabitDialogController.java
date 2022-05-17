package com.teampingui.controllers;

import com.teampingui.models.Habit;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddHabitDialogController implements Initializable {
    @FXML
    Label lAddHabitHeading;
    @FXML
    TextArea taNewHabitName;
    @FXML
    TableView<Habit> tvHabitFrequency;

    @FXML
    TableColumn colMon, colTue, colWed, colThu, colFri, colSat, colSun;

    private ObservableList<Habit> tempHabitList;

    @FXML
    void addNewHabit(ActionEvent e) {
        String name = taNewHabitName.getText().trim();

        tempHabitList.add(new Habit(name, false, false, false, 7));

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
        // Wie Checkboxen anzeigen lassen?
        colMon.setCellValueFactory(new PropertyValueFactory<Habit, Boolean>("checkedMon"));
        colMon.setCellFactory(CheckBoxTableCell.forTableColumn(colMon));



    }
}
