package Interfaces.impls;

import Interfaces.PasswordManager;
import LogicClasses.Note;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import java.util.HashMap;

public class CollectionsPasswordManager implements PasswordManager {

    private ObservableMap<String, Note> noteMap;

    public CollectionsPasswordManager() {
        noteMap = FXCollections.observableMap(new HashMap<String, Note>());
    }

    public CollectionsPasswordManager(ObservableMap<String, Note> noteMap) {
        this.noteMap = noteMap;
    }

    public Note getNoteByServiceName(String service) {
        return noteMap.get(service);
    }

    @Override
    public void add(Note note) {
        noteMap.put(note.getService(), note);
    }

    @Override
    public void remove(Note note) {
        noteMap.remove(note.getService());
    }

    public int size() {
        return noteMap.size();
    }
}
