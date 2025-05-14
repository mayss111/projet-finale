package controllers;
import service.JavaMailUtil;

import Entities.User;
import service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ConfirmMailController implements Initializable {

    @FXML
    private Button btnConfirmerCode;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnResendCode;

    @FXML
    private Label lbCodeError;

    @FXML
    private TextField tfCode;

    @FXML
    private VBox vbox;

    @FXML
    private VBox vboxCode;
    private UserService userService;

    private int code=2222;

    @FXML
    void fnConfirmerCode(ActionEvent event) {
        if(code == Integer.parseInt(tfCode.getText())){

            User authenticatedUser = userService.getAuthenticatedUser();
            authenticatedUser.setIs_verified(true);
            userService.update(authenticatedUser);
            try {
                if (authenticatedUser.hasRole("[\"ROLE_ADMIN\"]")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardAdmin.fxml"));
                    Parent root = loader.load();

                    // Pass the user to the DashboardAdminController
                       /* DashboardAdminController dashboardAdminController = loader.getController();
                        dashboardAdminController.setUser(authenticatedUser);*/

                    // Create a new stage for the new scene
                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(root));
                    newStage.show();

                    // Close the current stage
                    Stage currentStage = (Stage) tfCode.getScene().getWindow();
                    currentStage.close();
                } else if (authenticatedUser.hasRole("[\"ROLE_ENSEIGNAT\"]") || authenticatedUser.hasRole("[\"ROLE_ETUDIANT\"]") || authenticatedUser.hasRole("[\"ROLE_ADMIN\"]") ) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
                    Parent root = loader.load();

                    // Pass the user to the DashboardClientController




                    Stage newStage = new Stage();
                    newStage.initStyle(StageStyle.TRANSPARENT);
                    Scene scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    newStage.setScene(scene);
                    newStage.show();

                    // Close the current stage
                    Stage currentStage = (Stage) tfCode.getScene().getWindow();
                    currentStage.close();
                } else {
                    // User has no role assigned, show error message
                    showErrorAlert("User role not assigned.");
                }
            }catch (IOException e) {
                e.printStackTrace();
            }}
        else{
            showErrorAlert("Code incorrect try again");
            btnResendCode.setVisible(true);
        }
    }

    @FXML
    void fnLogOut(ActionEvent event) {
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
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void fnResendCode(ActionEvent event) {
        User authenticatedUser = userService.getAuthenticatedUser();
        Random random = new Random();

        // Generate a random 4-digit number
        int randomNumber = random.nextInt(9000) + 1000;
        code = randomNumber;
        // Convert the number to a string
        String randomString = String.valueOf(randomNumber);
        try {
            JavaMailUtil.sendMail(authenticatedUser.getEmail(),"Confirm your Mail","Please use this code to confirm your mail "+randomString);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        btnResendCode.setVisible(false);

    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setTitle("Authentication Failed");
        alert.setHeaderText(null);
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userService = UserService.getInstance();
    }
}
