package Controllers;

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

    Main main = new Main();
    File file;
    Stage openStage;
    Mode mode;

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
        passwordField_password.requestFocus();
        btn_Apply.setDisable(false);
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

            file.createNewFile();
            MainController.initializeList();
            Main.file = file;
            passwordField_password.requestFocus();
            btn_Apply.setDisable(false);
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

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                Main.outPassCollection = FXCollections.observableArrayList((Note[]) ois.readObject());
                MainController.initializeList(Main.outPassCollection);

            } catch (FileNotFoundException fileEx) {
                fileEx.printStackTrace();
                // doSomething
            } catch (ClassNotFoundException cnfex) {
                cnfex.printStackTrace();
                System.exit(0);
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }

        }

        openStage.close();
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}

enum Mode {
    NEW_FILE, OPEN_FILE;
}
