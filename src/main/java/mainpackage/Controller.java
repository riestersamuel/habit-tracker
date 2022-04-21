package mainpackage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class Controller {
    @FXML
    ListView lvJournal;
    @FXML
    TextArea taNewJournal; // Hier auslesen
    @FXML
    Label lWelcome;
    @FXML
    Button btnAddHabit;
    @FXML
    Button btnRemoveHabit;
    @FXML
    Label lMiniJournal;
    @FXML
    Button btnAddJournal;

    @FXML
    private void addNewEntry() {
        ArrayList<Habit> al = new ArrayList<>();
        al.add(new Habit("Könken", 100, 7));
        al.add(new Habit("Saufen", 100, 7));
        al.add(new Habit("Orgeln", 100, 7));
        al.add(new Habit("Julius duschen", 100, 7));

        System.out.println(al.get(0).getName());

        lvJournal.getItems().add(0, taNewJournal.getText());
        taNewJournal.clear();
    }
}