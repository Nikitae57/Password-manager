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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
        if (Main.password == null || Main.password.equals("")) {
            System.exit(0);
        }

        column_ServiceName.setCellValueFactory(new PropertyValueFactory<Note, String>("service"));
        column_Login.setCellValueFactory(new PropertyValueFactory<Note, String>("login"));
        column_Password.setCellValueFactory(new PropertyValueFactory<Note, String>("password"));

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

                String loweredCaseFilter = newValue.toLowerCase();

                // If login or service name matches the searched word, display it
                return note.getService().toLowerCase().startsWith(loweredCaseFilter) ||
                        note.getLogin().toLowerCase().startsWith(loweredCaseFilter);

                // No matching result found
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
    public void controlButtonPressed(ActionEvent actionEvent) {

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
            editStage.getIcons().add(new Image(getClass().getResourceAsStream("/Resources/Icon-0.png")));
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

    public void mouseEntered(MouseEvent mouseEvent) {
        btn_ClearTextField.setOpacity(0.8);
    }

    public void mouseExited(MouseEvent mouseEvent) {
        btn_ClearTextField.setOpacity(0.5);
    }
}
