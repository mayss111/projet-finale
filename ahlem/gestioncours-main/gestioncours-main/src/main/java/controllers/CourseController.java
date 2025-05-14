package controllers;

import contenu.ContenuCours;
import controllers.ContenuCoursController;
import gestioncours.Course;
import jakarta.mail.MessagingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import services.Servicescours;
import utils.EmailSender;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseController {

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
    private final Servicescours service = new Servicescours(); // Un seul service
    private final ObservableList<Course> courseList = FXCollections.observableArrayList();
    public CourseController() throws SQLException {
    }
    @FXML
    public void initialize() {
        // Lier les colonnes aux propriétés des objets Course


        // Charger les cours dès le démarrage
    }

    @FXML
    public void ajouterCours() {
        try {
            int id = Integer.parseInt(idTF.getText());
            Course c = new Course(
                    id,
                    titreTF.getText(),
                    subjectTF.getText(),
                    niveauTF.getText(),
                    contentsTF.getText()
            );
            service.ajouter(c);
            courseList.add(c);
            clearFields();

            // ✅ Envoi de l'e-mail
            String recipient = "guesmiahlem365@gmail.com"; // <-- adresse à qui envoyer la notification
            String subject = "Nouveau cours ajouté : " + c.getTitre();
            String body = "Un nouveau cours vient d'être ajouté.\n\n" +
                    "ID : " + c.getId() + "\n" +
                    "Titre : " + c.getTitre() + "\n" +
                    "Sujet : " + c.getSubject() + "\n" +
                    "Niveau : " + c.getNiveau();
            EmailSender.sendSimpleEmail(recipient, subject, body);

        } catch (NumberFormatException e) {
            showError("L'identifiant doit être un nombre entier.");
        } catch (SQLException e) {
            showError("Erreur lors de l'ajout : " + e.getMessage());
        } catch (MessagingException e) {
            showError("Le cours a été ajouté, mais l'envoi d'email a échoué : " + e.getMessage());
        }
    }


//    @FXML
    // public void modifierCours() {
    //   Course selected = courseTable.getSelectionModel().getSelectedItem();
    // if (selected != null) {
    //   selected.setTitre(titreTF.getText());
    //selected.setSubject(subjectTF.getText());
    // selected.setNiveau(niveauTF.getText());
    //try {
    //  service.modifier(selected);  // Appeler la méthode modifier dans Servicescours
    //rafraichirListeCours();
    //clearFields();
    //} catch (SQLException e) {
    //  showError("Erreur de modification : " + e.getMessage());
    //}
    //}
    //}

//    public void modifierCours() {
//        Course selected = courseTable.getSelectionModel().getSelectedItem();
//        if (selected != null) {
//            selected.setTitre(titreTF.getText());
//            selected.setSubject(subjectTF.getText());
//            selected.setNiveau(niveauTF.getText());
//            try {
//                service.modifier(selected);
//                courseTable.refresh();  // Force la mise à jour visuelle
//                clearFields();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                showError("Erreur de modification : " + e.getMessage());
//            }
//        } else {
//            showError("Veuillez sélectionner un cours à modifier.");
//        }
//    }
//
//    @FXML
//    public void supprimerCours() {
//        Course selected = courseTable.getSelectionModel().getSelectedItem();
//        if (selected != null) {
//            try {
//                service.supprimer(selected.getId());  // Appeler la méthode supprimer dans Servicescours
//                courseList.remove(selected);  // Retirer le cours de la liste
//                clearFields();
//            } catch (SQLException e) {
//                showError("Erreur de suppression : " + e.getMessage());
//            }
//        }
//    }

  /*  @FXML
    public void afficherCours() {
        try {
            courseList.clear();
            courseList.addAll(service.afficher());
        } catch (SQLException e) {
            showError("Erreur lors de l'affichage des cours : " + e.getMessage());
        }
    }
*/
  @FXML
  public void afficherCours(ActionEvent event) {
      try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Treeviewcours.fxml"));
          Parent root = loader.load();
          Stage newStage = new Stage();
          newStage.setScene(new Scene(root));
          newStage.setTitle("Gestion des cours");
          newStage.show();
          // Fermer la fenêtre actuelle (celle de l'interface principale)
          Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          currentStage.close();

      } catch (IOException e) {
          e.printStackTrace();
      }
  }

//    public void rafraichirListeCours() {
//        try {
//            courseList.clear();
//            List<Course> liste = service.getAllCourses();  // Rafraîchir la liste des cours
//            courseList.addAll(liste);
//        } catch (SQLException e) {
//            showError("Erreur lors du rafraîchissement des cours : " + e.getMessage());
//        }
//    }
    private void clearFields() {
        idTF.clear();
        titreTF.clear();
        subjectTF.clear();
        niveauTF.clear();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(msg);
        alert.showAndWait();
    }

//    @FXML
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
//

}
