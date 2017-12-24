package Controllers;

import Interfaces.impls.CollectionsPasswordManager;
import LogicClasses.Main;
import LogicClasses.Note;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
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

    private static CollectionsPasswordManager collectionsPasswordManager;
    private File file;

    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditController editController;
    private Stage editStage;
    private Parent fxmlEdit;

    /**
     * Loads FXML files and sets GUI
     */
    @FXML
    private void initialize() {

        column_ServiceName.setCellValueFactory(new PropertyValueFactory<Note, String>("service"));
        column_Login.setCellValueFactory(new PropertyValueFactory<Note, String>("login"));
        column_Password.setCellValueFactory(new PropertyValueFactory<Note, String>("password"));

        /*collectionsPasswordManager = new CollectionsPasswordManager();
        collectionsPasswordManager.enterTestData();*/

        collectionsPasswordManager.getNoteObservableList().addListener((ListChangeListener<Note>) c -> updateLabel());

        // Wrap noteObservableList into FilteredList so that we could manage displaying content of tableView (search)
        FilteredList<Note> noteFilteredList = new FilteredList<>(collectionsPasswordManager.getNoteObservableList());

        // Search function
        textField_Search.textProperty().addListener((observable, oldValue, newValue) -> {
            noteFilteredList.setPredicate(note -> {

                // If search textField is empty, display all notes
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }

                String lowereCaseFilter = newValue.toLowerCase();

                // If login or service name matches the searched word, display it
                if (note.getService().toLowerCase().contains(lowereCaseFilter) ||
                    note.getLogin().toLowerCase().contains(lowereCaseFilter)) {

                    return true;
                }

                // No matching result found
                return false;
            });
        });

        // Wrap noteList to SortedList so that user could sort table content
        SortedList<Note> noteSortedList = new SortedList<>(noteFilteredList);
        noteSortedList.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(noteSortedList);
        updateLabel();
        
        try {
            fxmlLoader.setLocation(getClass().getResource("/FXML/EditWindow.fxml"));
            fxmlEdit = fxmlLoader.load();
            editController = fxmlLoader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setFile(File file) {
        this.file = file;
    }

    public static void initializeList(ObservableList<Note> noteList) {
        collectionsPasswordManager = new CollectionsPasswordManager(noteList);
    }

    public static void initializeList() {
        collectionsPasswordManager = new CollectionsPasswordManager();
        Main.outPassCollection = collectionsPasswordManager.getNoteObservableList();
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
            editStage.initOwner(Main.STAGE);
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
     * <p>May be called by changes in collectionsPasswordManager.noteObservableList.</p>
     * Updates content of label according to number of notes
     */
    public void updateLabel() {
        label_NumberOfNotes.setText("Всего записей: " + collectionsPasswordManager.size());
    }

//    public static CollectionsPasswordManager getCollectionsPasswordManager() {
//        return collectionsPasswordManager;
//    }
}
