package Interfaces.impls;

import Interfaces.PasswordManager;
import LogicClasses.Note;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CollectionsPasswordManager implements PasswordManager {

    private ObservableList<Note> noteObservableList;

    public CollectionsPasswordManager() {
        noteObservableList = FXCollections.observableArrayList();
    }

    public CollectionsPasswordManager(ObservableList<Note> noteObservableList) {
        this.noteObservableList = noteObservableList;
    }

    public ObservableList<Note> getNoteObservableList() {
        return noteObservableList;
    }

    public Note getNoteByServiceName(String service) {
        int size = noteObservableList.size();
        for (int i = 0; i < size; i++) {
            if (noteObservableList.get(i).getService().equals(service)) {
                return noteObservableList.get(i);
            }
        }
        return null;
    }

    @Override
    public void add(Note note) {
        noteObservableList.add(note);
    }

    @Override
    public void remove(Note note) {
        noteObservableList.remove(getNoteByServiceName(note.getService()));
    }

    public int size() {
        return noteObservableList.size();
    }

    public void enterTestData() {
        noteObservableList.add(new Note("google", "nikita", "123"));
        noteObservableList.add(new Note("yandex", "nastya", "321"));
        noteObservableList.add(new Note("catmail", "cat", "890"));
    }
}
