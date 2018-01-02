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
    private TextField textField_Service;

    @FXML
    private TextField textField_Login;

    @FXML
    private TextField textField_Password;

    private Note note;
    private Node source;
    private Stage stage;

    /**
     * <p>Adds or changes note depends on which button pressed.</p>
     * @param actionEvent
     */
    public void apply(ActionEvent actionEvent) {
        String service = textField_Service.getText().trim();
        String login = textField_Login.getText().trim();
        String password = textField_Password.getText().trim();

        // All textAreas are filled, could create new note
        if (!(service).equals("") && !(login.equals("")) && !(password.equals(""))) {

            note.setService(textField_Service.getText());
            note.setPassword(textField_Password.getText());
            note.setLogin(textField_Login.getText());

            cancel(actionEvent);
        }
    }

    /**
     * Closes modal window
     */
    public void cancel(ActionEvent actionEvent) {
        if (source == null && stage == null) {
            source = (Node) actionEvent.getSource();
            stage = (Stage) source.getScene().getWindow();
        }

        stage.hide();
    }

    /**
     * Getter for note
     * @return Note
     */
    public Note getNote() {
        return note;
    }

    /**
     * Fills all three textArea with provided data
     * @param note Provided note
     */
    public void setNote(Note note) {

        this.note = note;

        textField_Service.setText(note.getService());
        textField_Login.setText(note.getLogin());
        textField_Password.setText(note.getPassword());
    }
}
