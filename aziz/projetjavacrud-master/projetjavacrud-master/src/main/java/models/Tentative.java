package models;

public class Tentative {
    private int idt;        // ID de la tentative
    private int idquiz;     // ID du quiz
    private int idq;        // ID de la question
    private int idetud;     // ID de l'étudiant
    private Boolean reponse; // true si bonne réponse, false sinon

    // Constructeur vide
    public Tentative() {
    }

    // Constructeur sans ID de tentative (cas d'insertion)
    public Tentative(int idquiz, int idq, int idetud, Boolean reponse) {
        this.idquiz = idquiz;
        this.idq = idq;
        this.idetud = idetud;
        this.reponse = reponse;
    }

    // Constructeur complet
    public Tentative(int idt, int idquiz, int idq, int idetud, Boolean reponse) {
        this.idt = idt;
        this.idquiz = idquiz;
        this.idq = idq;
        this.idetud = idetud;
        this.reponse = reponse;
    }

    // Getters et setters
    public int getIdt() {
        return idt;
    }

    public void setIdt(int idt) {
        this.idt = idt;
    }

    public int getIdquiz() {
        return idquiz;
    }

    public void setIdquiz(int idquiz) {
        this.idquiz = idquiz;
    }

    public int getIdq() {
        return idq;
    }

    public void setIdq(int idq) {
        this.idq = idq;
    }

    public int getIdetud() {
        return idetud;
    }

    public void setIdetud(int idetud) {
        this.idetud = idetud;
    }

    public Boolean getReponse() {
        return reponse;
    }

    public void setReponse(Boolean reponse) {
        this.reponse = reponse;
    }

    @Override
    public String toString() {
        return "Tentative{" +
                "idt=" + idt +
                ", idquiz=" + idquiz +
                ", idq=" + idq +
                ", idetud=" + idetud +
                ", reponse=" + reponse +
                '}';
    }
}
