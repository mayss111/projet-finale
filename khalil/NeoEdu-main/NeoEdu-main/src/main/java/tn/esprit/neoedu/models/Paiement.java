package tn.esprit.neoedu.models;

import java.time.LocalDate;

public class Paiement {

    private int id,cin,num_telephone,montant;
    private String nom,prenom ,niveau_etude,atelier,cours,methode_paiement,type_abonnement,statut_paiement;
    private LocalDate  date_inscription,fin_abonnement;

    public Paiement(int id, int cin, int num_telephone, int montant,
                    String nom, String prenom, String niveau_etude,
                    String atelier, String cours, String methode_paiement,
                    String type_abonnement, String statut_paiement,
                    LocalDate date_inscription, LocalDate fin_abonnement) {
        this.id = id;
        this.cin = cin;
        this.num_telephone = num_telephone;
        this.montant = montant;
        this.nom = nom;
        this.prenom = prenom;
        this.niveau_etude = niveau_etude;
        this.atelier = atelier;
        this.cours = cours;
        this.methode_paiement = methode_paiement;
        this.type_abonnement = type_abonnement;
        this.statut_paiement = statut_paiement;
        this.date_inscription = date_inscription;
        this.fin_abonnement = fin_abonnement;
    }

    public Paiement( int cin, int num_telephone, int montant,
                    String nom, String prenom, String niveau_etude,
                    String atelier, String cours, String methode_paiement,
                    String type_abonnement, String statut_paiement,
                    LocalDate date_inscription, LocalDate fin_abonnement) {
        this.cin = cin;
        this.num_telephone = num_telephone;
        this.montant = montant;
        this.nom = nom;
        this.prenom = prenom;
        this.niveau_etude = niveau_etude;
        this.atelier = atelier;
        this.cours = cours;
        this.methode_paiement = methode_paiement;
        this.type_abonnement = type_abonnement;
        this.statut_paiement = statut_paiement;
        this.date_inscription = date_inscription;
        this.fin_abonnement = fin_abonnement;
    }

    public Paiement() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public int getNum_telephone() {
        return num_telephone;
    }

    public void setNum_telephone(int num_telephone) {
        this.num_telephone = num_telephone;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNiveau_etude() {
        return niveau_etude;
    }

    public void setNiveau_etude(String niveau_etude) {
        this.niveau_etude = niveau_etude;
    }

    public String getAtelier() {
        return atelier;
    }

    public void setAtelier(String atelier) {
        this.atelier = atelier;
    }

    public String getCours() {
        return cours;
    }

    public void setCours(String cours) {
        this.cours = cours;
    }

    public String getMethode_paiement() {
        return methode_paiement;
    }

    public void setMethode_paiement(String methode_paiement) {
        this.methode_paiement = methode_paiement;
    }

    public String getType_abonnement() {
        return type_abonnement;
    }

    public void setType_abonnement(String type_abonnement) {
        this.type_abonnement = type_abonnement;
    }

    public String getStatut_paiement() {
        return statut_paiement;
    }

    public void setStatut_paiement(String statut_paiement) {
        this.statut_paiement = statut_paiement;
    }

    public LocalDate getDate_inscription() {
        return date_inscription;
    }

    public void setDate_inscription(LocalDate date_inscription) {
        this.date_inscription = date_inscription;
    }

    public LocalDate getFin_abonnement() {
        return fin_abonnement;
    }

    public void setFin_abonnement(LocalDate fin_abonnement) {
        this.fin_abonnement = fin_abonnement;
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", cin=" + cin +
                ", num_telephone=" + num_telephone +
                ", montant=" + montant +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", niveau_etude='" + niveau_etude + '\'' +
                ", atelier='" + atelier + '\'' +
                ", cours='" + cours + '\'' +
                ", methode_paiement='" + methode_paiement + '\'' +
                ", type_abonnement='" + type_abonnement + '\'' +
                ", statut_paiement='" + statut_paiement + '\'' +
                ", date_inscription=" + date_inscription +
                ", fin_abonnement=" + fin_abonnement +
                '}';
    }
}

