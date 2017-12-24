package LogicClasses;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Main extends Application {

    public static Stage STAGE;
    public static File file;
    public static ObservableList<Note> outPassCollection;

    @Override
    public void start(Stage primaryStage) throws Exception {

        STAGE = primaryStage;

        FXMLLoader openFXMLLoader = new FXMLLoader();
        openFXMLLoader.setLocation(getClass().getResource("/FXML/OpenFile.fxml"));
        Parent openParent = openFXMLLoader.load();

        primaryStage.setTitle("Passworder");
        primaryStage.setScene(new Scene(openParent));
        primaryStage.setResizable(false);
        primaryStage.show();


        //OpenFile(primaryStage);
    }

    @Override
    public void stop() {
        // Здесь надо сохранить файл (переменная file)
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(outPassCollection.toArray(new Note[0]));
        } catch (IOException ioex) {
            ioex.printStackTrace();
            Platform.exit();
        }
    }

    public void showMainWindow() throws IOException {
        FXMLLoader mainFXMLLoader = new FXMLLoader();
        mainFXMLLoader.setLocation(getClass().getResource("/FXML/MainWindow.fxml"));
        Parent mainParent = mainFXMLLoader.load();

        STAGE.setScene(new Scene(mainParent));
        STAGE.setResizable(true);
        STAGE.setMinWidth(420);
        STAGE.setMinHeight(260);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
