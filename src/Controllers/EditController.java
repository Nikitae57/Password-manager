package Controllers;

import LogicClasses.Note;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditController {

    @FXML
    Button btn_Apply;

    @FXML
    Button btn_Cancel;

    @FXML
    TextField textField_Service;

    @FXML
    TextField textField_Login;

    @FXML
    TextField textField_Password;

    private Note note;

    /**
     * <p>Adds or changes note depends on which button pressed.</p>
     * @param actionEvent
     * @return void
     */
    public void apply(ActionEvent actionEvent) {
        String service = textField_Service.getText();
        String login = textField_Login.getText();
        String password = textField_Password.getText();

        if (!(service).equals("") && !(login.equals("")) && !(password.equals(""))) {
            System.out.println("all fields");
        } else {
            // Not all textFields filled
            System.out.println("not all fields");
        }
    }

    /**
     * Closes modal window
     * @return void
     */
    public void cancel(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setNote(Note note) {
        this.note = note;

        textField_Service.setText(note.getService());
        textField_Login.setText(note.getLogin());
        textField_Password.setText(note.getPassword());
    }
}
