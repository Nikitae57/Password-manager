package LogicClasses;

import Controllers.MainController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXML/MainWindow.fxml"));
        Parent fxmlParent = fxmlLoader.load();

        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(primaryStage);

        primaryStage.setTitle("Менеджер паролей");
        primaryStage.setMinWidth(420);
        primaryStage.setMinHeight(260);
        primaryStage.setScene(new Scene(fxmlParent, 300, 275));
        primaryStage.show();
        //OpenFile(primaryStage);
    }

    /*
    @FXML
    private void OpenFile(Stage stage) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open password file");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("PSS files (*.pss)", "*.pss");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);

        // If file is not chosen, close the program
        if (file == null) {
            System.exit(0);
        }
    }
    */

    public static void main(String[] args) {
        launch(args);
    }
}
