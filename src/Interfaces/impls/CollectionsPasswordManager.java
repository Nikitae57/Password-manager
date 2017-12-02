package Interfaces.impls;

import Interfaces.PasswordManager;
import LogicClasses.Note;

import java.util.HashMap;

public class CollectionsPasswordManager implements PasswordManager {

    private HashMap<String, Note> noteHashMap;

    public CollectionsPasswordManager() {
        noteHashMap = new HashMap<>();
    }

    public CollectionsPasswordManager(HashMap<String, Note> noteHashMap) {
        this.noteHashMap = noteHashMap;
    }

    public Note getNoteByServiceName(String service) {
        return noteHashMap.get(service);
    }

    @Override
    public void add(Note note) {
        noteHashMap.put(note.getService(), note);
    }

    @Override
    public void remove(Note note) {
        noteHashMap.remove(note.getService());
    }

    public int size() {
        return noteHashMap.size();
    }
}
