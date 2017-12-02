package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    public void showNewNoteWindow(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/EditWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Добавление записи");
            stage.setScene(new Scene(root, 315, 170));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.initOwner(( (Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}
