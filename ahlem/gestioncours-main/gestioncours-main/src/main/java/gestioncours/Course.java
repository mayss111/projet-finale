package gestioncours;

import contenu.ContenuCours;
import javafx.beans.value.ObservableValue;

import java.util.List;

public class Course {
    private final int id;
    private String titre;
    private String subject;
    private String niveau;
    private  String contents;

    public Course(int id, String titre, String subject, String niveau, String contents ) {
        this.id = id;
        this.titre = titre;
        this.subject = subject;
        this.niveau = niveau;
        this.contents = contents;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getSubject() {
        return subject;
    }

    public String getNiveau() {
        return niveau;
    }

    public String getContents() {
        return contents;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }


    private int nbContenu;

    public int getNbContenu() { return nbContenu; }
    public void setNbContenu(int nbContenu) { this.nbContenu = nbContenu; }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", title='" + titre + '\'' +
                ", subject='" + subject + '\'' +
                ", niveau='" + niveau + '\'' +
                ", contents=" + contents +
                '}';
    }

    public String getcourse() {
        String getcourse = new String();
        return getcourse; }
}
