package Controllers;

import Interfaces.impls.CollectionsPasswordManager;
import LogicClasses.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.*;

public class OpenFileController {

    Main main = new Main();
    CollectionsPasswordManager cpm = new CollectionsPasswordManager();
    MainController mc = new MainController();
    File file;

    public void openFile(ActionEvent actionEvent) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open password file");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("PSS files (*.pss)", "*.pss");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(Main.STAGE);

        // If file is not chosen, close the program
        if (file == null) {
            Platform.exit();
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
        main.showMainWindow();
    }

    public void createNewFile(ActionEvent actionEvent) {

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Create password file");
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("PSS files (*.pss)", "*.pss");
            fileChooser.getExtensionFilters().add(extFilter);
            file = fileChooser.showSaveDialog(Main.STAGE);

            // If file is not chosen, close the program
            if (file == null) {
                Platform.exit();
            }

            file.createNewFile();
            MainController.initializeList();
            //mc.setFile(file);
            main.setFile(file);
            main.showMainWindow();

        } catch (IOException ioex) {
            Platform.exit();
        }
    }
}
