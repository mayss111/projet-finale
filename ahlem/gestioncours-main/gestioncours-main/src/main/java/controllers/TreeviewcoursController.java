package controllers;

import gestioncours.Course;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServicesTreeviewcours;
import services.Servicescours;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TreeviewcoursController {

    @FXML
    private TextField idTF;
    @FXML
    private TextField titreTF;

    @FXML
    private TextField subjectTF;
    @FXML
    private TextField niveauTF;
    @FXML
    private TextField contentsTF;



    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> idCol;
    @FXML
    private TableColumn<Course, String> titreCol;
    @FXML
    private TableColumn<Course, String> subjectCol;
    @FXML
    private TableColumn<Course, String> niveauCol;
    @FXML
    private TableColumn<Course, String> contentsCol;

    private final ServicesTreeviewcours service1 = new ServicesTreeviewcours();
    private final ObservableList<Course> courseList = FXCollections.observableArrayList();

    public TreeviewcoursController() throws SQLException {
    }

    @FXML
    public void initialize() {
        // Lier les colonnes aux propriétés des objets Course
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        titreCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitre()));
        subjectCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubject()));
        niveauCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNiveau()));
       // contentsCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getContents().size())));
        contentsCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContents()));

        // Lier la table aux données de cours
        courseTable.setItems(courseList);

    }



    public void modifierCours() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setTitre(titreTF.getText());
            selected.setSubject(subjectTF.getText());
            selected.setNiveau(niveauTF.getText());
            selected.setContents(contentsTF.getText());  // Correction ici

            try {
                service1.modifier(selected);
                courseTable.refresh();  // Force la mise à jour visuelle
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
                showError("Erreur de modification : " + e.getMessage());
            }
        } else {
            showError("Veuillez sélectionner un cours à modifier.");
        }
    }


    @FXML
    public void supprimerCours() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                service1.supprimer(selected.getId());  // Appeler la méthode supprimer dans Servicescours
                courseList.remove(selected);  // Retirer le cours de la liste
                clearFields();
            } catch (SQLException e) {
                showError("Erreur de suppression : " + e.getMessage());
            }
        }
    }

    private void clearFields() {
        idTF.clear();
        titreTF.clear();
        subjectTF.clear();
        niveauTF.clear();
        contentsTF.clear();


    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    
    public void ouvrirContenu() {
        // Récupérer l'élément sélectionné dans la table
        Course selected = courseTable.getSelectionModel().getSelectedItem();

        // Vérifier si un cours est sélectionné
        if (selected != null) {
            try {
                // Charger le fichier FXML de la vue des contenus du cours
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gerercontenu.fxml"));

                Parent root = loader.load();

                // Récupérer le contrôleur de la vue des contenus
                ContenuCoursController contenuController = loader.getController();

                // Passer le cours sélectionné au contrôleur des contenus
                contenuController.course(selected);  // Passe l'objet Course au contrôleur des contenus

                // Créer une nouvelle fenêtre pour afficher les contenus du cours
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Contenus du Cours - " + selected.getTitre());
                stage.show();
            } catch (IOException e) {
                showError("Erreur lors de l'ouverture du contenu : " + e.getMessage());
            }
        } else {
            showError("Veuillez sélectionner un cours pour ouvrir son contenu.");
        }
    }

//    public void ouvrirContenu() {
//        // Récupérer l'élément sélectionné dans la table
//        Course selected = courseTable.getSelectionModel().getSelectedItem();
//
//        // Vérifier si un cours est sélectionné
//        if (selected != null) {
//            try {
//                // Charger le fichier FXML de la vue des contenus du cours
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gerercontenu.fxml"));
//
//                Parent root = loader.load();
//
//                // Récupérer le contrôleur de la vue des contenus
//                ContenuCoursController contenuController = loader.getController();
//
//                // Passer le cours sélectionné au contrôleur des contenus
//                contenuController.setCours(selected);
//
//                // Créer une nouvelle fenêtre pour afficher les contenus du cours
//                Stage stage = new Stage();
//
//                stage.setScene(new Scene(root));
//                stage.setTitle("Contenus du Cours - " + selected.getTitre());
//                stage.show();
//            } catch (IOException e) {
//                showError("Erreur lors de l'ouverture du contenu : " + e.getMessage());
//            }
//        } else {
//            showError("Veuillez sélectionner un cours pour ouvrir son contenu.");
//        }
//    }


    public void rafraichirListeCours() {
        try {
            courseList.clear();
            List<Course> liste = service1.getAllCourses();  // Rafraîchir la liste des cours
            courseList.addAll(liste);
        } catch (SQLException e) {
            showError("Erreur lors du rafraîchissement des cours : " + e.getMessage());
        }
    }

}




