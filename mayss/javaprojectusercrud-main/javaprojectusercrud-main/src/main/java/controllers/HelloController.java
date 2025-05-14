package controllers;

import Entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloController {

    @FXML
    private Label welcomeText;

    private User user;


    public void setUser(User newUser) {
        this.user = new User();
        welcomeText.setText("Hello, " + user.getNom() +" "+ user.getPrenom());
    }

    @FXML
    private void initialize() {

    }

    @FXML
    private void onHelloButtonClick(ActionEvent event) {
        try {
            // Load the new page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
            Parent root = loader.load();


            // Create a new stage for the new scene
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.show();

            // Close the current stage
            Stage currentStage = (Stage) welcomeText.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Other methods and event handlers can be added here as needed
}
