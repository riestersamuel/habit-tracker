package com.teampingui.models;

import com.teampingui.interfaces.ICheckBoxClickListener;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

public class DayCell extends TableCell<Habit, Boolean> {

    private final CheckBox checkBox;

    public DayCell(ICheckBoxClickListener clickListener) {
        checkBox = new CheckBox();
        checkBox.setOnAction(evt ->
                clickListener.onPositionClicked(checkBox.isSelected(), getTableView().getItems().get(getIndex()))
        );

        // TODO: add color for haveToDo Days
        this.setGraphic(checkBox);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setEditable(true);
    }

    @Override
    public void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            if (item != null) {
                checkBox.setAlignment(Pos.CENTER);
                checkBox.setSelected(item);
            }
            setAlignment(Pos.CENTER);
            setGraphic(checkBox);
        }
    }
}
