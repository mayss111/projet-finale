package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import contenu.ContenuCours;
import gestioncours.Course;
import gestioncours.contenttype;
import services.ServicesContenuCours;

public class ContenuCoursController {
    @FXML private TextField idTF;
    @FXML private TextField titreTF;
    @FXML private ComboBox<String> typeCB;
    @FXML private TextField urlTF;
    @FXML private Label titleLabel;
    @FXML private TextArea contentArea;

    private final ServicesContenuCours service = new ServicesContenuCours();
    private final ObservableList<ContenuCours> contenuList = FXCollections.observableArrayList();
    private Course cours;

    public void initialize() {
        typeCB.getItems().addAll("VIDEO", "DOCUMENT", "QUIZ", "EXERCISE");
        //afficherContenu();
    }


    @FXML
    public void ajouterContenu() {
        String titre = titreTF.getText().trim();
        String selectedType = typeCB.getValue();
        String url = urlTF.getText().trim();

        if (titre.isEmpty()) {
            showError("Le titre ne peut pas être vide.");
            return;
        }

        if (selectedType == null || (!selectedType.equals("VIDEO") && !selectedType.equals("DOCUMENT") &&
                !selectedType.equals("QUIZ") && !selectedType.equals("EXERCISE"))) {
            showError("Le type doit être VIDEO, DOCUMENT, QUIZ ou EXERCISE.");
            return;
        }

        if (!isValidURL(url)) {
            showError("L'URL fournie n'est pas valide.");
            return;
        }

        try {
            int id = Integer.parseInt(idTF.getText());
            int idCours = cours.getId();

            if (service.checkCourseExists(idCours)) {
                ContenuCours contenu = new ContenuCours(id, titre, contenttype.valueOf(selectedType), url, idCours);
                service.ajouter(contenu);
                contenuList.add(contenu);
                clearFields();
            } else {
                showError("Cours non trouvé.");
            }

        } catch (NumberFormatException e) {
            showError("L'ID doit être un entier.");
        } catch (SQLException e) {
            showError("Erreur d'ajout : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            showError("Le type de contenu sélectionné n'est pas valide.");
        }
    }

    private boolean isValidURL(String url) {
        try {
            new java.net.URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @FXML
    public void retourVersCours() {
        try {
            Stage currentStage = (Stage) titleLabel.getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gerercontenu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestion des Cours");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showError("Erreur lors du retour aux cours : " + e.getMessage());
        }
    }

    public void setCours(Course cours) {
        if (this.cours != null && this.cours.getId() == cours.getId()) return;
        this.cours = cours;
        titleLabel.setText(cours.getTitre());

        String contents = cours.getContents();
        String[] contenusArray = contents.split("[\n;]+");

        StringBuilder sb = new StringBuilder();
        contenuList.clear();
        for (String contenu : contenusArray) {
            String trimmed = contenu.trim();
            sb.append(trimmed).append("\n");
            contenuList.add(new ContenuCours(0, trimmed, contenttype.DOCUMENT, "", cours.getId()));
        }

        contentArea.setText(sb.toString());
    }

    public Course getCours() {
        return cours;
    }


//    public void afficherContenu() {
//        contenuList.clear();
//        List<ContenuCours> list = service.getAll();
//        contenuList.addAll(list);
//    }


    @FXML
    public void afficherContenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/Treeviewcontenu.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("gestioncontenu");
            newStage.show();
            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void clearFields() {
        idTF.clear();
        titreTF.clear();
        typeCB.setValue(null);
        urlTF.clear();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void course(Course selected) {
        this.cours = selected;
        titleLabel.setText(selected.getTitre());

        String contents = selected.getContents();
        String[] contenusArray = contents.split("[\n;]+");

        StringBuilder sb = new StringBuilder();
        for (String contenu : contenusArray) {
            sb.append(contenu.trim()).append("\n");
        }

        contentArea.setText(sb.toString());

        contenuList.clear();
        for (String contenu : contenusArray) {
            contenuList.add(new ContenuCours(0, contenu.trim(), contenttype.DOCUMENT, "", selected.getId()));
        }
    }

}
