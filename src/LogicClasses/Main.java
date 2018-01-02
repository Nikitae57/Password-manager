package LogicClasses;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.crypto.KeyGenerator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Main extends Application {

    public static Stage STAGE;
    public static File file;
    public static ObservableList<Note> outPassCollection;
    public static String password;

    @Override
    public void start(Stage primaryStage) throws Exception {

        STAGE = primaryStage;
        STAGE.getIcons().add(new Image(Main.class.getResourceAsStream("/Resources/Icon-0.png")));

        Parent openParent = FXMLLoader.load(getClass().getResource("/FXML/OpenFile.fxml"));
        Stage openStage = new Stage();
        openStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Resources/Icon-0.png")));
        openStage.setScene(new Scene(openParent));
        openStage.setResizable(false);
        openStage.initModality(Modality.WINDOW_MODAL);
        openStage.initOwner(STAGE);
        openStage.showAndWait();

        Parent mainParent = FXMLLoader.load(getClass().getResource("/FXML/MainWindow.fxml"));
        STAGE.setTitle("Passworder");
        STAGE.setScene(new Scene(mainParent));
        STAGE.setResizable(true);
        STAGE.setMinWidth(420);
        STAGE.setMinHeight(260);

        STAGE.show();
    }

    @Override
    public void stop() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(outPassCollection.toArray(new Note[0]));
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
