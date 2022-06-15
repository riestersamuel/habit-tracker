package com.teampingui.models;

import com.teampingui.interfaces.ICheckBoxClickListener;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

public class DayCell extends TableCell<Habit, Boolean> {

    private final CheckBox checkBox = new CheckBox();
    private final Day day;

    public DayCell(ICheckBoxClickListener clickListener, final Day day) {
        this.day = day;

        checkBox.setOnAction(evt -> {
            clickListener.onPositionClicked(checkBox.isSelected(), getTableView().getItems().get(getIndex()));
            switchStyle();
        });

        this.setGraphic(checkBox);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setEditable(true);
    }

    private void switchStyle() {
        boolean hasTodo = getTableView().getItems().get(getIndex()).hasToBeDone(day);
        checkBox.getStyleClass().removeAll("cb-haveto", "cb-done", "cb-donthaveto");
        if (hasTodo && !checkBox.isSelected()) {
            checkBox.getStyleClass().add("cb-haveto");
        } else if (hasTodo && checkBox.isSelected()) {
            checkBox.getStyleClass().add("cb-done");
        } else {
            checkBox.getStyleClass().add("cb-donthaveto");
        }
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
            switchStyle();
        }
    }
}
