package models;

public class Quiz {
    private int id;

    public Quiz(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Quiz{id=" + id + '}';
    }
}
