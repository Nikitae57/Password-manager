package Controllers;

import Interfaces.impls.CollectionsPasswordManager;
import LogicClasses.Note;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<Note, String> column_ServiceName;

    @FXML
    private TableColumn<Note, String> column_Login;

    @FXML
    private TableColumn<Note, String> column_Password;

    @FXML
    private Button btn_Add;

    @FXML
    private Button btn_Change;

    @FXML
    private Button btn_Delete;

    @FXML
    private Button btn_Search;

    @FXML
    private Button btn_ClearTextField;

    @FXML
    private Label label_NumberOfNotes;

    @FXML
    private TextField textField_Search;

    private CollectionsPasswordManager collectionsPasswordManager = new CollectionsPasswordManager();

    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditController editController;
    private Stage editStage, mainStage;
    private Parent fxmlEdit;

    /**
     * Loads FXML files and sets GUI
     */
    @FXML
    private void initialize() {

        column_ServiceName.setCellValueFactory(new PropertyValueFactory<Note, String>("service"));
        column_Login.setCellValueFactory(new PropertyValueFactory<Note, String>("login"));
        column_Password.setCellValueFactory(new PropertyValueFactory<Note, String>("password"));

        collectionsPasswordManager = new CollectionsPasswordManager();
        collectionsPasswordManager.enterTestData();

        tableView.setItems(collectionsPasswordManager.getNoteObservableList());
        updateLabel();

        collectionsPasswordManager.getNoteObservableList().addListener((ListChangeListener<Note>) c -> updateLabel());
        
        try {
            fxmlLoader.setLocation(getClass().getResource("../FXML/EditWindow.fxml"));
            fxmlEdit = fxmlLoader.load();
            editController = fxmlLoader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    /**
     * <p>May be called by pressing "add" or "change" button</p>
     * Opens a new dialog window.
     * Depending on which button pressed, adds or changes note
     *
     * @return void
     */
    public void controllButtonPressed(ActionEvent actionEvent) {

        // Return, if method called by pressing not button
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }

        Button btn = (Button) source;
        Note selectedNote = (Note) tableView.getSelectionModel().getSelectedItem();
        String selectedBtnId = btn.getId();
        if (selectedBtnId.equals("btn_Delete")) {

            // Return if nothing selected to delete
            if (selectedNote == null) {
                System.out.println("null");
                return;
            }

            // Remove note, do not show the edit dialog
            collectionsPasswordManager.remove(selectedNote);
            return;

        } else if (selectedBtnId.equals("btn_Change") && selectedNote != null) {
            editController.setNote(selectedNote);
            showDialog();
            tableView.refresh();

        } else if (selectedBtnId.equals("btn_Add")) {
            editController.setNote(new Note("", "", ""));
            showDialog();

            if (editController.getNote().allDataExist()) {
                collectionsPasswordManager.add(editController.getNote());
            }
        }
    }

    private void showDialog() {

        // Initializing stage only once
        if (editStage == null) {

            editStage = new Stage();
            editStage.setTitle("Редактор");
            editStage.setScene(new Scene(fxmlEdit, 315, 170));
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.setResizable(false);
            editStage.initOwner(mainStage);
        }

        editStage.showAndWait();
    }

    /**
     * <p>May be called by pressing "X" button.</p>
     * Simply removes text from search textField.
     */
    public void clearTextField(ActionEvent actionEvent) {
        textField_Search.setText("");
    }

    /**
     * <p>May be called by pressing "search" button.</p>
     * Updates content of the tableView according to entered text in search textField
     */
    public void search(ActionEvent actionEvent) {

    }

    /**
     * <p>May be called by changes in collectionsPasswordManager.noteObservableList.</p>
     * Updates content of label according to number of notes
     */
    public void updateLabel() {
        label_NumberOfNotes.setText("Кол-во записей: " + collectionsPasswordManager.size());
    }
}
