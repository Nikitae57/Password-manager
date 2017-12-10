package LogicClasses;

public class Note {

    // Describes to which service the record belongs
    private String service;

    // Keeps a user's login
    private String login;

    // Keeps a user's password
    private String password;

    public Note(String service, String login, String password) {
        this.service = service;
        this.login = login;
        this.password = password;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getService() { return service; }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean allDataExist() {
        return !(this.getService().equals("")) && !(this.getLogin().equals("")) && !(this.getPassword().equals(""));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (!service.equals(note.service)) return false;
        if (!login.equals(note.login)) return false;
        return password.equals(note.password);
    }

    @Override
    public int hashCode() {
        int result = service.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
