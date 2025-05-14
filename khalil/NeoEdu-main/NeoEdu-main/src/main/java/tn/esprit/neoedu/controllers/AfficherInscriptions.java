package tn.esprit.neoedu.controllers;

import java.time.LocalDate;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.neoedu.models.Paiement;
import tn.esprit.neoedu.services.PaiementService;
import tn.esprit.neoedu.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AfficherInscriptions {
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField cinField;

    @FXML
    private TextField niveauEtudeField;
    @FXML
    private TextField atelierField;
    @FXML
    private TextField coursField;
    @FXML
    private TextField methodePaiementField;
    @FXML
    private TextField typeAbonnementField;
    @FXML
    private TextField montantField;
    @FXML
    private TextField statutPaiementField;
    @FXML
    private DatePicker finAbonnementField;





    @FXML
    private TextField idDeleteTF;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<Paiement, String> abonnementCol;

    @FXML
    private TableColumn<Paiement, String> atelierCol;

    @FXML
    private TableColumn<Paiement, Integer> cinCol;

    @FXML
    private TableColumn<Paiement, String> coursCol;

    @FXML
    private TableColumn<Paiement, String> finCol;

    @FXML
    private TableColumn<Paiement, String> methodeCol;

    @FXML
    private TableColumn<Paiement, Integer> montantCol;

    @FXML
    private TableColumn<Paiement, String> niveauCol;

    @FXML
    private TableColumn<Paiement, String> nomCol;

    @FXML
    private TableColumn<Paiement, String> prenomCol;

    @FXML
    private TableColumn<Paiement, String> statueCol;

    @FXML
    private TableView<Paiement> tableView;




    @FXML
    void supprimerPaiement(ActionEvent event) {
        String idText = idDeleteTF.getText();

        // Vérifier si le champ est vide ou non valide
        if (idText.isEmpty() || !idText.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez entrer un ID valide.");
            alert.showAndWait();
            return;
        }

        int id = Integer.parseInt(idText);
        PaiementService paiementService = new PaiementService();

        try {
            paiementService.supprimer(id);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Le paiement avec l'ID " + id + " a été supprimé.");
            alert.showAndWait();


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de suppression");
            alert.setContentText("Problème lors de la suppression : " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    private void rafraichirTable(ActionEvent event) {

        PaiementService paiementService = new PaiementService();
        try {
            List<Paiement> paiements = paiementService.recuperer();
            ObservableList<Paiement> observableList = FXCollections.observableList(paiements);
            tableView.setItems(observableList);

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            cinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
            niveauCol.setCellValueFactory(new PropertyValueFactory<>("niveau_etude"));
            atelierCol.setCellValueFactory(new PropertyValueFactory<>("atelier"));
            coursCol.setCellValueFactory(new PropertyValueFactory<>("cours"));
            abonnementCol.setCellValueFactory(new PropertyValueFactory<>("type_abonnement"));
            methodeCol.setCellValueFactory(new PropertyValueFactory<>("methode_paiement"));
            montantCol.setCellValueFactory(new PropertyValueFactory<>("montant"));
            statueCol.setCellValueFactory(new PropertyValueFactory<>("statut_paiement"));
            finCol.setCellValueFactory(new PropertyValueFactory<>("fin_abonnement"));
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }



    /*@FXML
    public void modifierPaiement(ActionEvent event) {
        Paiement selectedPaiement = tableView.getSelectionModel().getSelectedItem();
        if (selectedPaiement != null) {
            // Récupérer les valeurs des TextField
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            int cin = Integer.parseInt(cinField.getText());
            String niveauEtude = niveauEtudeField.getText();
            String atelier = atelierField.getText();
            String cours = coursField.getText();
            String methodePaiement = methodePaiementField.getText();
            String typeAbonnement = typeAbonnementField.getText();
            int montant = Integer.parseInt(montantField.getText());
            String statutPaiement = statutPaiementField.getText();



            // Créer un nouvel objet Paiement avec les nouvelles valeurs
            PaiementService paiementService = new PaiementService();
            Paiement paiement = new Paiement();
            paiement.setNom(nom);
            paiement.setPrenom(prenom);
            paiement.setCin(cin);
            paiement.setNiveau_etude(niveauEtude);
            paiement.setAtelier(atelier);
            paiement.setCours(cours);
            paiement.setMethode_paiement(methodePaiement);
            paiement.setType_abonnement(typeAbonnement);
            paiement.setMontant(montant);
            paiement.setStatut_paiement(statutPaiement);


            try {
                paiementService.modifier(paiement);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Inscription modifié");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }*/

    @FXML
    public void modifierPaiement(ActionEvent event) {
        Paiement selectedPaiement = tableView.getSelectionModel().getSelectedItem();
        if (selectedPaiement != null) {
            // Récupérer les valeurs des TextField
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            int cin = Integer.parseInt(cinField.getText());
            String niveauEtude = niveauEtudeField.getText();
            String atelier = atelierField.getText();
            String cours = coursField.getText();
            String methodePaiement = methodePaiementField.getText();
            String typeAbonnement = typeAbonnementField.getText();
            int montant = Integer.parseInt(montantField.getText());
            String statutPaiement = statutPaiementField.getText();

            // Mettre à jour l'objet Paiement existant
            selectedPaiement.setNom(nom);
            selectedPaiement.setPrenom(prenom);
            selectedPaiement.setCin(cin);
            selectedPaiement.setNiveau_etude(niveauEtude);
            selectedPaiement.setAtelier(atelier);
            selectedPaiement.setCours(cours);
            selectedPaiement.setMethode_paiement(methodePaiement);
            selectedPaiement.setType_abonnement(typeAbonnement);
            selectedPaiement.setMontant(montant);
            selectedPaiement.setStatut_paiement(statutPaiement);

            // Appeler la méthode de modification
            PaiementService paiementService = new PaiementService();
            try {
                paiementService.modifier(selectedPaiement);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succès");
                alert.setContentText("Inscription mise à jour avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("La mise à jour a échoué : " + e.getMessage());
                alert.showAndWait();
            }
        }
    }












    @FXML
    void initialize() {
        PaiementService paiementService = new PaiementService();
        try {
            List<Paiement> paiements = paiementService.recuperer();
            ObservableList<Paiement> observableList = FXCollections.observableList(paiements);
            tableView.setItems(observableList);

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            cinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
            niveauCol.setCellValueFactory(new PropertyValueFactory<>("niveau_etude"));
            atelierCol.setCellValueFactory(new PropertyValueFactory<>("atelier"));
            coursCol.setCellValueFactory(new PropertyValueFactory<>("cours"));
            abonnementCol.setCellValueFactory(new PropertyValueFactory<>("type_abonnement"));
            methodeCol.setCellValueFactory(new PropertyValueFactory<>("methode_paiement"));
            montantCol.setCellValueFactory(new PropertyValueFactory<>("montant"));
            statueCol.setCellValueFactory(new PropertyValueFactory<>("statut_paiement"));
            finCol.setCellValueFactory(new PropertyValueFactory<>("fin_abonnement"));
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }



        /*tableView.setOnMouseClicked(event -> {
            Paiement selectedPaiement = tableView.getSelectionModel().getSelectedItem();
            if (selectedPaiement != null) {
                // Afficher les informations dans les TextField
                nomField.setText(selectedPaiement.getNom());
                prenomField.setText(selectedPaiement.getPrenom());
                cinField.setText(String.valueOf(selectedPaiement.getCin()));
                niveauEtudeField.setText(selectedPaiement.getNiveau_etude());
                atelierField.setText(selectedPaiement.getAtelier());
                coursField.setText(selectedPaiement.getCours());
                methodePaiementField.setText(selectedPaiement.getMethode_paiement());
                typeAbonnementField.setText(selectedPaiement.getType_abonnement());
                montantField.setText(String.valueOf(selectedPaiement.getMontant()));
                statutPaiementField.setText(selectedPaiement.getStatut_paiement());
                if (selectedPaiement.getFin_abonnement() != null) {
                    finAbonnementField.setValue(selectedPaiement.getFin_abonnement().toLocalDate());
                } else {
                    finAbonnementField.setValue(null);
                }

            }

        });*/
        tableView.setOnMouseClicked(event -> {
            Paiement selectedPaiement = tableView.getSelectionModel().getSelectedItem();
            if (selectedPaiement != null) {
                // Afficher les informations dans les TextField
                nomField.setText(selectedPaiement.getNom());
                prenomField.setText(selectedPaiement.getPrenom());
                cinField.setText(String.valueOf(selectedPaiement.getCin()));
                niveauEtudeField.setText(selectedPaiement.getNiveau_etude());
                atelierField.setText(selectedPaiement.getAtelier());
                coursField.setText(selectedPaiement.getCours());
                methodePaiementField.setText(selectedPaiement.getMethode_paiement());
                typeAbonnementField.setText(selectedPaiement.getType_abonnement());
                montantField.setText(String.valueOf(selectedPaiement.getMontant()));
                statutPaiementField.setText(selectedPaiement.getStatut_paiement());

            }
        });




    }

}
