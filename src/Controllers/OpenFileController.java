package Controllers;

import LogicClasses.Main;
import LogicClasses.Note;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class OpenFileController {

    Main main = new Main();
    File file;
    Stage openStage;

    public void openFile(ActionEvent actionEvent) throws IOException {

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

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Main.outPassCollection = FXCollections.observableArrayList((Note[]) ois.readObject());
            MainController.initializeList(Main.outPassCollection);
        } catch (ClassNotFoundException cnfex) {
            cnfex.printStackTrace();
            Platform.exit();
        }

        main.setFile(file);
        openStage.close();
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
            main.setFile(file);
            openStage.close();

        } catch (IOException ioex) {
            Platform.exit();
        }
    }
}
