package controllers;

import contenu.ContenuCours;
import gestioncours.Course;
import gestioncours.contenttype;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import services.ServicesContenuCours;
import services.Servicestreeviewcontenu;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Treeviewconteucontroller {

    @FXML private TextField idTF;
    @FXML private TextField titreTF;
    @FXML private ComboBox<String> typeCB;
    @FXML private TextField urlTF;
    @FXML private TextField idCoursTF;
    @FXML private TableView<ContenuCours> contenuTable;

    @FXML private TableColumn<ContenuCours, Integer> idCol;
    @FXML private TableColumn<ContenuCours, String> titreCol;
    @FXML private TableColumn<ContenuCours, String> typeCol;
    @FXML private TableColumn<ContenuCours, String> urlCol;
    @FXML private TableColumn<ContenuCours, Integer> idCoursCol;

    @FXML private Label titleLabel;
    @FXML private TextArea contentArea;

    private final Servicestreeviewcontenu service = new Servicestreeviewcontenu();
    private final ObservableList<ContenuCours> contenuList = FXCollections.observableArrayList();

    private Course cours; // cours actuellement sélectionné

    @FXML
    public void initialize() {
        titreCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitre()));
        typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
        urlCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUrl()));
        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        idCoursCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdCours()).asObject());

        typeCB.getItems().addAll("VIDEO", "DOCUMENT", "QUIZ", "EXERCISE");

        contenuTable.setItems(contenuList);

        // Chargement initial (si pas de filtre de cours)
        loadContenuData();
    }

    private void loadContenuData() {
        try {
            List<ContenuCours> contenus = service.afficher(); // Tu peux ajouter un filtre dans le service si besoin
            contenuList.clear();
            contenuList.addAll(contenus);
        } catch (SQLException e) {
            showError("Erreur lors du chargement des contenus : " + e.getMessage());
        }
    }

    @FXML
    void modifierContenu() {
        ContenuCours selected = contenuTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String titre = titreTF.getText().trim();
            if (titre.isEmpty()) {
                showError("Le titre ne peut pas être vide.");
                return;
            }

            String selectedType = typeCB.getValue();
            if (selectedType == null) {
                showError("Le type de contenu doit être sélectionné.");
                return;
            }

            String url = urlTF.getText().trim();
            int idCours = selected.getIdCours();

            try {
                ContenuCours modifie = new ContenuCours(
                        selected.getId(),
                        titre,
                        contenttype.valueOf(selectedType),
                        url,
                        idCours
                );

                service.modifier(modifie);
                contenuList.set(contenuList.indexOf(selected), modifie);
                clearFields();
            } catch (SQLException e) {
                showError("Erreur de modification : " + e.getMessage());
            } catch (IllegalArgumentException e) {
                showError("Le type de contenu sélectionné n'est pas valide.");
            }
        } else {
            showError("Veuillez sélectionner un contenu à modifier.");
        }
    }

    @FXML
    void supprimerContenu() {
        ContenuCours selected = contenuTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Supprimer ce contenu ?");
            confirm.setContentText("Êtes-vous sûr de vouloir supprimer ce contenu ?");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    service.supprimer(selected.getId());
                    contenuList.remove(selected);
                    clearFields();
                } catch (SQLException e) {
                    showError("Erreur de suppression : " + e.getMessage());
                }
            }
        } else {
            showError("Veuillez sélectionner un contenu à supprimer.");
        }
    }

    @FXML
    public void telechargerContenu() {
        ContenuCours selected = contenuTable.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getUrl() != null && !selected.getUrl().isEmpty()) {
            try {
                Desktop.getDesktop().browse(new URI(selected.getUrl()));
                System.out.println("Téléchargement du contenu : " + selected.getUrl());
            } catch (IOException | URISyntaxException e) {
                showError("Erreur lors du téléchargement : " + e.getMessage());
            }
        } else {
            showError("Veuillez sélectionner un contenu avec une URL valide.");
        }
    }

    @FXML
    public void selectionnerContenu() {
        ContenuCours selected = contenuTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            idTF.setText(String.valueOf(selected.getId()));
            titreTF.setText(selected.getTitre());
            typeCB.setValue(selected.getType().name());
            urlTF.setText(selected.getUrl());
            idCoursTF.setText(String.valueOf(selected.getIdCours()));
        } else {
            showError("Aucun contenu sélectionné.");
        }
    }

    public void course(Course selected) {
        this.cours = selected;
        if (cours != null) {
            try {
                List<ContenuCours> filtres = service.afficherParCours(cours.getId());
                contenuList.setAll(filtres);
                titleLabel.setText("Contenus du cours : " + cours.getcourse());
            } catch (SQLException e) {
                showError("Erreur lors du chargement des contenus pour le cours : " + e.getMessage());
            }
        }
    }

    private void clearFields() {
        idTF.clear();
        titreTF.clear();
        typeCB.setValue(null);
        urlTF.clear();
        idCoursTF.clear();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
