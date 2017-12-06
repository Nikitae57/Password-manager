package tests;

import Interfaces.impls.CollectionsPasswordManager;
import LogicClasses.Note;
import org.junit.Test;

import static org.junit.Assert.*;

public class CollectionsPasswordManagerTest {

    @Test
    public void add() throws Exception {
        CollectionsPasswordManager cpwm = new CollectionsPasswordManager();

        Note note = new Note("google", "login", "password");
        Note note2 = new Note("yandex", "login2", "password2");

        cpwm.add(note);
        cpwm.add(note2);

        assertEquals(note, cpwm.getNoteByServiceName("google"));
        assertEquals(note2, cpwm.getNoteByServiceName("yandex"));
        assertNotEquals(note, note2);
        assertNotEquals(note, cpwm.getNoteByServiceName("yandex"));
        assertNotEquals(note2, cpwm.getNoteByServiceName("google"));
    }

    @Test
    public void remove() throws Exception {
        CollectionsPasswordManager cpwm = new CollectionsPasswordManager();

        Note note = new Note("google", "login", "password");
        Note note2 = new Note("yandex", "login2", "password2");

        cpwm.add(note);
        cpwm.add(note2);

        cpwm.remove(cpwm.getNoteByServiceName("google"));
        assertEquals(cpwm.getNoteByServiceName("google"), null);
    }

    @Test
    public void size() throws Exception {
        CollectionsPasswordManager cpwm = new CollectionsPasswordManager();

        Note note = new Note("google", "login", "password");
        Note note2 = new Note("yandex", "login2", "password2");

        cpwm.add(note);
        assertEquals(cpwm.size(), 1);

        cpwm.add(note2);
        assertEquals(cpwm.size(), 2);

        cpwm.remove(cpwm.getNoteByServiceName("google"));
        assertEquals(cpwm.size(), 1);

        cpwm.remove(cpwm.getNoteByServiceName("yandex"));
        assertEquals(cpwm.size(), 0);
    }

    @Test
    public void getNoteByServiceName() throws Exception {
        CollectionsPasswordManager cpwm = new CollectionsPasswordManager();

        Note note = new Note("google", "login", "password");
        Note note2 = new Note("yandex", "login2", "password2");

        cpwm.add(note);
        cpwm.add(note2);

        assertEquals(note, cpwm.getNoteByServiceName("google"));
        assertNotEquals(note, cpwm.getNoteByServiceName("yandex"));
    }

    @Test
    public void getNoteObservableList() throws Exception {
        CollectionsPasswordManager cpwm = new CollectionsPasswordManager();

        Note note = new Note("google", "login", "password");
        Note note2 = new Note("yandex", "login2", "password2");

        cpwm.add(note);
        cpwm.add(note2);

        assertEquals(note, cpwm.getNoteObservableList().get(0));
        assertNotEquals(note2, cpwm.getNoteObservableList().get(0));
    }

}