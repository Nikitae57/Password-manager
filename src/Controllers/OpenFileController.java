package Controllers;

import LogicClasses.Crypto;
import LogicClasses.Main;
import LogicClasses.Note;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import java.io.*;

public class OpenFileController {

    @FXML
    VBox VBox_openFile;

    @FXML
    HBox HBox_open_new;

    @FXML
    HBox HBox_password;

    @FXML
    Button btn_Apply;

    @FXML
    PasswordField passwordField_password;

    File file;
    Stage openStage;
    Mode mode;

    @FXML
    private void initialize() {
        passwordField_password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (passwordField_password.getText().trim().equals("")) {
                btn_Apply.setDisable(true);
            } else {
                btn_Apply.setDisable(false);
                passwordField_password.setStyle("-fx-text-fill: black");
            }
        });
    }

    public void openFile(ActionEvent actionEvent) {

        Node source = (Node) actionEvent.getSource();
        openStage = (Stage) source.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open password file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PSS files (*.pss)", "*.pss");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(Main.STAGE);

        // If file is not chosen, close the program
        if (file == null) {
            return;
        }

        Main.file = file;
        passwordField_password.setDisable(false);
        passwordField_password.requestFocus();
        mode = Mode.OPEN_FILE;
    }

    public void createNewFile(ActionEvent actionEvent) {

        Node source = (Node) actionEvent.getSource();
        openStage = (Stage) source.getScene().getWindow();

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Create password file");
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("PSS files (*.pss)", "*.pss");
            fileChooser.getExtensionFilters().add(extFilter);
            file = fileChooser.showSaveDialog(Main.STAGE);

            // If file is not chosen, close the program
            if (file == null) {
                return;
            }

            if (!file.getName().endsWith(".pss")) {
                file = new File(file.getAbsolutePath().concat(".pss"));
            }

            file.createNewFile();
            Main.file = file;

            MainController.initializeList();
            passwordField_password.setDisable(false);
            passwordField_password.requestFocus();
            mode = Mode.NEW_FILE;

        } catch (IOException ioex) {
            System.exit(0);
        }
    }

    public void apply(ActionEvent actionEvent) {

        if (passwordField_password.getText().trim().equals("")) {
            return;
        }
        Main.password = passwordField_password.getText().trim();

        if (mode == Mode.OPEN_FILE) {
            try {
                Crypto.decrypt(Main.password, file, file);

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                Main.outPassCollection = FXCollections.observableArrayList((Note[]) ois.readObject());
                MainController.initializeList(Main.outPassCollection);

            } catch (FileNotFoundException fileEx) {
                fileEx.printStackTrace();
                // doSomething

            } catch (BadPaddingException bpe) {
                passwordField_password.setStyle("-fx-text-fill: red");
                passwordField_password.requestFocus();
                return;

            } catch (ClassNotFoundException cnfex) {
                cnfex.printStackTrace();
                System.exit(0);
            } catch (IOException ioex) {
                ioex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        Main.started = true;
        openStage.close();
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}

enum Mode {
    NEW_FILE, OPEN_FILE
}
