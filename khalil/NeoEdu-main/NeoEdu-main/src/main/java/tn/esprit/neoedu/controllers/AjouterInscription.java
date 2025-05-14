package tn.esprit.neoedu.controllers;

import java.time.LocalDate;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import tn.esprit.neoedu.models.Paiement;
import tn.esprit.neoedu.services.PaiementService;
import tn.esprit.neoedu.test.HelloApplication;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;

import static tn.esprit.neoedu.services.PaiementService.EmailSender.sendEmailWithAttachment;

public class AjouterInscription {

    @FXML
    private Label dateFinLabel;

    @FXML
    private Label montantLabel;



    @FXML
    private ComboBox<String> atelierComboBox;

    @FXML
    private TextField cinTF;

    @FXML
    private ComboBox<String> coursComboBox;


    @FXML
    private DatePicker dateTF;


    @FXML
    private TextField methodeTF;



    @FXML
    private TextField niveauTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private CheckBox payeCheckBox;


    @FXML
    private TextField telTF;

    @FXML
    private TextField typeTF;

    @FXML
    void afficherInscriptions(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/neoedu/AfficherInscriptions.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }


    @FXML
    void ajouterIncription(ActionEvent event) {
        PaiementService paiementService = new PaiementService();
        Paiement paiement = new Paiement();
        paiement.setNom(nomTF.getText());
        paiement.setPrenom(prenomTF.getText());
        paiement.setCin(Integer.parseInt(cinTF.getText()));
        paiement.setNum_telephone(Integer.parseInt(telTF.getText()));
        paiement.setNiveau_etude(niveauTF.getText());
        paiement.setDate_inscription(dateTF.getValue());
        //paiement.setAtelier(ateliersTF.getText());
        //paiement.setCours(coursTF.getText());
        paiement.setMethode_paiement(methodeTF.getText());
        paiement.setType_abonnement(typeTF.getText());
        //paiement.setStatut_paiement(statueTF.getText());
        //paiement.setFin_abonnement(finTF.getValue());
        //paiement.setMontant(Integer.parseInt(montantTF.getText()));

        // Récupérer le statut de paiement depuis la CheckBox
        String statutPaiement = payeCheckBox.isSelected() ? "Payé" : "Non payé";
        paiement.setStatut_paiement(statutPaiement);


        String typeAbonnement = typeTF.getText().toLowerCase();

        // Récupérer les valeurs des ComboBox correctement
        String atelier = atelierComboBox.getSelectionModel().getSelectedItem();
        if (atelier != null) {
            paiement.setAtelier(atelier);  // Enregistrer dans l'objet Paiement
        } else {
            paiement.setAtelier("Pas d'atelier");  // Valeur par défaut
        }

        String cours = coursComboBox.getSelectionModel().getSelectedItem();
        if (cours != null) {
            paiement.setCours(cours);  // Enregistrer dans l'objet Paiement
        } else {
            paiement.setCours("Pas de cours");  // Valeur par défaut
        }






        LocalDate dateInscription = dateTF.getValue();
        LocalDate dateFin;

        switch (typeTF.getText().toLowerCase()) {
            case "mensuel":
                dateFin = dateInscription.plusMonths(1);
                break;
            case "annuel":
                dateFin = dateInscription.plusYears(1);
                break;
            case "6mois":
            case "6 mois":
                dateFin = dateInscription.plusMonths(6);
                break;
            default:
                // Si le type d'abonnement est inconnu, on ajoute un mois par défaut
                dateFin = dateInscription.plusMonths(1);
                break;
        }

        paiement.setDate_inscription(dateInscription);
        paiement.setFin_abonnement(dateFin);


        int baseMontant;

        switch (typeAbonnement) {
            case "mensuel":
                baseMontant = 150;
                break;
            case "6 mois":
            case "6mois":
                baseMontant = 500;
                break;
            case "annuel":
                baseMontant = 800;
                break;
            default:
                baseMontant = 150;
                break;
        }

        int dureeMois = switch (typeAbonnement) {
            case "mensuel" -> 1;
            case "6 mois", "6mois" -> 6;
            case "annuel" -> 12;
            default -> 1;
        };

        int atelierMontant = 0;
        if (atelier.contains("2 ateliers par semaine")) {
            atelierMontant = 100 * dureeMois;
        } else if (atelier.contains("1 atelier par semaine")) {
            atelierMontant = 50 * dureeMois;
        }

        int coursMontant = 0;
        if (cours.contains("Cours matinal")) {
            coursMontant = 80 * dureeMois;
        } else if (cours.contains("Cours du soir")) {
            coursMontant = 120 * dureeMois;
        }

        int montantTotal = baseMontant + atelierMontant + coursMontant;
        montantLabel.setText("Montant total : " + montantTotal + " TND");


        paiement.setMontant(montantTotal);



        try {
            paiementService.ajouter(paiement);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Inscription enregistrée et une facture PDF a été crée");
            alert.showAndWait();
            paiementService.generateInvoice(paiement);
            try {
                sendEmailWithAttachment("destinataire_email@gmail.com", "Facture de Paiement", "Veuillez trouver la facture en pièce jointe.", "C:/Users/DELL/IdeaProjects/NeoEdu/facture_0.pdf");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }




    @FXML
    public void initialize() {

        coursComboBox.getItems().addAll("Pas de cours", "Cours matinal", "Cours du soir");

        atelierComboBox.getItems().addAll("Pas d'atelier", "1 atelier par semaine", "2 ateliers par semaine");
        // Ajouter un listener pour détecter quand l'utilisateur quitte le champ
        typeTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // Si l'utilisateur a quitté le champ (focus perdu)
            if (!newValue) {
                // Récupérer la date d'inscription
                LocalDate dateInscription = dateTF.getValue();
                LocalDate dateFin;

                if (dateInscription != null && !typeTF.getText().isEmpty()) {
                    // Calculer la date de fin en fonction du type d'abonnement
                    switch (typeTF.getText().toLowerCase()) {
                        case "mensuel":
                            dateFin = dateInscription.plusMonths(1);
                            break;
                        case "annuel":
                            dateFin = dateInscription.plusYears(1);
                            break;
                        case "6mois":
                        case "6 mois":
                            dateFin = dateInscription.plusMonths(6);
                            break;
                        default:
                            dateFin = dateInscription.plusMonths(1);
                            break;
                    }

                    // Afficher la date de fin dans le Label
                    dateFinLabel.setText("Date de fin d'abonnement : " + dateFin.toString());
                }
            }
        });

        coursComboBox.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Lorsque le focus est perdu
                try {
                    // Récupérer le type d'abonnement et l'atelier
                    String typeAbonnement = typeTF.getText().toLowerCase();
                    String atelier = atelierComboBox.getSelectionModel().getSelectedItem().toLowerCase();

                    String cours = coursComboBox.getSelectionModel().getSelectedItem().toLowerCase();
                    int baseMontant;

                    // Déterminer le montant de base en fonction du type d'abonnement
                    switch (typeAbonnement) {
                        case "mensuel":
                            baseMontant = 150;
                            break;
                        case "6 mois":
                        case "6mois":
                            baseMontant = 500;
                            break;
                        case "annuel":
                            baseMontant = 800;
                            break;
                        default:
                            baseMontant = 150;
                            break;
                    }

                    // Calculer la durée en mois
                    int dureeMois = switch (typeAbonnement) {
                        case "mensuel" -> 1;
                        case "6 mois", "6mois" -> 6;
                        case "annuel" -> 12;
                        default -> 1;
                    };

                    // Ajouter le coût des ateliers
                    int atelierMontant = 0;
                    if (atelier.contains("2 ateliers par semaine")) {
                        atelierMontant = 100 * dureeMois;
                    } else if (atelier.contains("1 atelier par semaine")) {
                        atelierMontant = 50 * dureeMois;
                    }

                    // Ajouter le coût des cours
                    int coursMontant = 0;
                    if (cours.contains("matinal")) {
                        coursMontant = 80 * dureeMois;
                    } else if (cours.contains("soir")) {
                        coursMontant = 120 * dureeMois;
                    }

                    // Calcul du montant total
                    int montantTotal = baseMontant + atelierMontant + coursMontant;

                    // Afficher le montant dans le Label
                    montantLabel.setText("Montant total : " + montantTotal + " TND");

                } catch (Exception e) {
                    montantLabel.setText("Erreur de calcul !");
                    System.err.println("Erreur lors du calcul du montant : " + e.getMessage());
                }
            }
        });
    }





}
