package controllers;

import Entities.ResetPassword;
import Entities.User;
import service.JavaMailUtil;
import service.ResetPasswordService;
import service.SmsService;
import service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.ResourceBundle;

public class OTPController  {

    @FXML
    private Button btnConfirmerCode;

    @FXML
    private Label lbCodeError;

    @FXML
    private TextField tfCode;

    @FXML
    private VBox vboxCode;

    @FXML
    private VBox vboxMain;

    private UserService userService;

    private User authenticatedUser;

    @FXML
    void fnConfirmerCode(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();

        // Subtract 3 hours from the current date and time
        LocalDateTime threeHoursAgo = now.minusHours(3);

        ResetPasswordService rsp = new ResetPasswordService();
        User user = authenticatedUser;
        ResetPassword rp = rsp.get(user);

        if (rp != null && rp.getCode() == Integer.parseInt(tfCode.getText())) {
            if (rp.getDateCreation().toLocalDateTime().isBefore(threeHoursAgo)) {
                lbCodeError.setText("Code has expired. Please request a new one.");
            } else {
                lbCodeError.setText("");
                try {
                    if (authenticatedUser.hasRole("[\"ROLE_ADMIN\"]")) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardAdmin.fxml"));
                        Parent root = loader.load();
                        Stage newStage = new Stage();
                        newStage.setScene(new Scene(root));
                        newStage.show();

                        // Close the current stage
                        Stage currentStage = (Stage) lbCodeError.getScene().getWindow();
                        currentStage.close();
                    } else if (authenticatedUser.hasRole("[\"ROLE_ELEVEE\"]") ||authenticatedUser.hasRole("[\"ROLE_ENSEIGNANT\"]")) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardtry.fxml"));
                        Parent root = loader.load();



                        // Create a new stage for the new scene
                        Stage newStage = new Stage();
                        newStage.setScene(new Scene(root));
                        newStage.show();

                        // Close the current stage
                        Stage currentStage = (Stage) lbCodeError.getScene().getWindow();
                        currentStage.close();
                    } else {
                        // User has no role assigned, show error message
                        showErrorAlert("User role not assigned.");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            lbCodeError.setText("Please enter a valid code");
        }



    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setTitle("Authentication Failed");
        alert.setHeaderText(null);
        alert.show();
    }


}
