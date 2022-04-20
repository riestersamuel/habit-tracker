package mainpackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class Controller {

    @FXML private TextArea habitInput;
    @FXML private Label lOutput;
    @FXML private Button btn;
    int counter = 0;
    @FXML private ListView lvHabits;

    @FXML
    private void addHabit(ActionEvent event) {
        counter++;
        System.out.println(habitInput.getText());
        lvHabits.getItems().add(habitInput.getText());
        lOutput.setText(lOutput.getText() + "\n" + habitInput.getText());
        habitInput.clear();
        btn.setText(Integer.toString(counter));
    }
}