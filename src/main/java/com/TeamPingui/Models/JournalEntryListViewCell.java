package com.TeamPingui.Models;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class JournalEntryListViewCell extends ListCell<com.TeamPingui.Models.JournalEntry> {
    @FXML
    private Label dateLabel;

    @FXML
    private  Text entryText;

    @FXML
    private VBox vbox;

    @FXML
    private FXMLLoader mLLoader;


    @Override
    protected void updateItem(JournalEntry jEntry, boolean empty) {
        super.updateItem(jEntry, empty);

        if(empty || jEntry == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getClassLoader().getResource("JournalCell.fxml"));

                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            entryText.wrappingWidthProperty().bind(vbox.widthProperty());
            dateLabel.setText(jEntry.getDate().toString());
            entryText.setText(jEntry.getEntry());

            // set the width's
            vbox.setMinWidth(125);
            vbox.setMaxWidth(125);
            vbox.setPrefWidth(125);

            dateLabel.setText(jEntry.getDate().toString());
            entryText.setText(jEntry.getEntry());

            Text text = (Text)vbox.getChildren().get(1);
            text.setTextAlignment(TextAlignment.LEFT);


            setText(null);
            setGraphic(vbox);
        }
    }
}
