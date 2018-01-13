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

import javax.crypto.Cipher;
import java.io.*;

public class Main extends Application {

    public static Stage STAGE;
    public static File file;
    public static ObservableList<Note> outPassCollection;
    public static String password;

    public static boolean started = false;

    @Override
    public void start(Stage primaryStage) {
        try {
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
        } catch (EOFException eofex) {

        } catch (IOException ioex) {

        }

    }

    @Override
    public void stop() {
        try {
            if (!started) {
                return;
            }

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(outPassCollection.toArray(new Note[0]));
            Crypto.encrypt(password, file, file);

        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
