package controllers;

import Entities.User;
import service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public  class SigninController implements Initializable {

    @FXML
    private TextField tfEmail;
    @FXML
    private Button btnForgetPass;
    @FXML
    private VBox vbox;
    @FXML
    private TextField tfPassword;
    Parent fxml;
    public static User user_connected;
    private controllers.MainController mainController; // Reference to MainController

    public void setMainController(controllers.MainController mainController) {
        this.mainController = mainController;
    }
    private UserService userService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userService = UserService.getInstance();
    }

    @FXML
    void fnSignIn(ActionEvent event) {
        // Your code for sign-in
        String email = tfEmail.getText();
        String password = tfPassword.getText();

        // Check if email and password are not empty
        if (validateFields(email, password)) {
            // Authenticate user
            User authenticatedUser = userService.authenticate(email, password);
            if (authenticatedUser != null) {
                // Set authenticated user in UserService
                System.out.println("test"+authenticatedUser.toString());
                userService.setAuthenticatedUser(authenticatedUser);
                System.out.println(userService.getAuthenticatedUser().toString()+"ttttttt");
                // Your existing code to navigate to the next scene...
            }
            if (authenticatedUser != null) {
                try {
                    if (authenticatedUser.isIs_verified()) {

                        try {
                            fxml = FXMLLoader.load(getClass().getResource("/MailConfirm.fxml"));
                            vbox.getChildren().setAll(fxml);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        fxml = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
                        vbox.getChildren().setAll(fxml); // Afficher la scène de bienvenue (Hello)
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Authentication failed, show error message
                showErrorAlert("Invalid email or password.");
            }
        } else {
            // Fields are empty, show error message
            showErrorAlert("Please enter email and password.");
        }
    }


    // Method to validate email and password fields
    private boolean validateFields(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }

    // Method to show error alert
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setTitle("Authentication Failed");
        alert.setHeaderText(null);
        alert.show();
    }

    @FXML
    void btnForgetPassAction(ActionEvent event) {
        try {
            // Load ForgetPassword.fxml
            fxml = FXMLLoader.load(getClass().getResource("/ForgetPassword.fxml"));
            vbox.getChildren().setAll(fxml);
            // Traverse through the loaded FXML hierarchy to find the VBox elements
            VBox vboxMain = null;
            VBox vboxEmail = null;
            VBox vboxCode = null;
            VBox vboxReset = null;

            if (fxml instanceof VBox) {
                VBox rootVBox = vbox;
                for (Node node : rootVBox.getChildren()) {
                    if (node instanceof VBox) {
                        VBox vbox = (VBox) node;
                        switch (vbox.getId()) {
                            case "vboxMain":
                                vboxMain = vbox;
                                break;
                            default:
                                // Handle other nodes if needed
                                break;
                        }
                    }
                }
            }
            for(Node node : vboxMain.getChildren()){
                if(node instanceof  VBox) {
                    VBox vbox = (VBox) node;
                    switch (vbox.getId()){
                        case "vboxEmail":
                            vboxEmail = vbox;
                            break;
                        case "vboxCode":
                            vboxCode = vbox;
                            break;
                        case "vboxReset":
                            vboxReset = vbox;
                            break;
                        default:
                            break;
                    }
                }
            }
            // Remove vboxCode and vboxReset from vboxMain
            if (vboxMain != null) {
                vboxMain.getChildren().removeAll(vboxCode, vboxReset);
            }

            // Resize vboxEmail to occupy the entire space previously occupied by vboxMain
            if (vboxEmail != null) {
                vboxEmail.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            }

            // Set the loaded FXML as the only child of vbox

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
