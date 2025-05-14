package controllers;

import Entities.User;
import service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DashboardAdminController {

    @FXML
    private TableView<User> tableViewUsers;
    @FXML
    private TableColumn<User, String> colName;
    @FXML
    private TableColumn<User, String> colLastName;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, String> colAddress;
    @FXML
    private TableColumn<User, String> colPhone;
    @FXML
    private TableColumn<User, String> colVerified;
    @FXML
    private TableColumn<User, Void> colAction;

    @FXML
    private TextField tfNameAdmin;
    @FXML
    private TextField tfLastNameAdmin;
    @FXML
    private TextField tfEmailAdmin;
    @FXML
    private TextField tfAdress;
    @FXML
    private TextField tfPhoneAdmin;
    @FXML
    private PasswordField tfPasswordAdmin;
    @FXML
    private PasswordField tfConfirmPasswordAdmin;
    @FXML
    private ComboBox<String> comboFilter;

    private final UserService us = UserService.getInstance();
    private final ObservableList<User> userList = FXCollections.observableArrayList();

    private boolean bEqualPassword = true;
    private boolean bAdd = false;

    @FXML
    public void initialize() {
        comboFilter.getItems().addAll("All", "Verified", "Not Verified");
        comboFilter.setOnAction(e -> FnReloadDataFiltered());
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        userList.clear();
        List<User> allUsers = us.getAll();
        for (User user : allUsers) {
            user.setVerified(user.isIs_verified() ? "Verified" : "Not Verified");
            userList.add(user);
        }

        colName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("zipcode")); // ou champ tel s’il existe
        colVerified.setCellValueFactory(new PropertyValueFactory<>("verified"));

        colAction.setCellFactory(createActionButtons());

        tableViewUsers.setItems(userList);
    }

    private Callback<TableColumn<User, Void>, TableCell<User, Void>> createActionButtons() {
        return param -> new TableCell<>() {
            private final Button blockButton = new Button("Block");

            {
                blockButton.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    us.delete(user);
                    userList.remove(user); // Mise à jour rapide
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : blockButton);
            }
        };
    }

    public void FnReloadDataFiltered() {
        String selected = comboFilter.getValue();
        List<User> users;

        if ("Verified".equals(selected)) {
            users = us.getAll().stream().filter(User::isIs_verified).toList();
        } else if ("Not Verified".equals(selected)) {
            users = us.getAll().stream().filter(u -> !u.isIs_verified()).toList();
        } else {
            users = us.getAll();
        }

        userList.clear();
        for (User user : users) {
            user.setVerified(user.isIs_verified() ? "Verified" : "Not Verified");
            userList.add(user);
        }

        tableViewUsers.setItems(userList);
    }

    public void fnSignup(ActionEvent event) throws SQLException {
        String address = tfAdress.getText();
        Pattern pattern = Pattern.compile("^(\\d{4}),\\s*([^,]+),\\s*(.+)$");
        Matcher matcher = pattern.matcher(address);
        bAdd = matcher.matches();

        bEqualPassword = tfPasswordAdmin.getText().equals(tfConfirmPasswordAdmin.getText());

        if (!bAdd || !bEqualPassword) {
            showErrorAlertAdmin();
            return;
        }

        User u = new User();
        u.setNom(tfNameAdmin.getText());
        u.setPrenom(tfLastNameAdmin.getText());
        u.setEmail(tfEmailAdmin.getText());
        u.setAdress(tfAdress.getText());
        u.setPassword(tfPasswordAdmin.getText());
        u.setRole("admin");
        u.setIs_verified(false);
        u.setVerified("Not Verified");

        boolean inserted = us.SignUp(u);
        if (inserted) {
            clearForm();
            loadDataFromDatabase();
        } else {
            showSignupError("Erreur lors de l'ajout de l'utilisateur (email existant ?)");
        }
    }

    private void clearForm() {
        tfNameAdmin.clear();
        tfLastNameAdmin.clear();
        tfEmailAdmin.clear();
        tfAdress.clear();
        tfPhoneAdmin.clear();
        tfPasswordAdmin.clear();
        tfConfirmPasswordAdmin.clear();
    }

    private void showErrorAlertAdmin() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur formulaire");
        alert.setHeaderText("Vérification des champs");
        StringBuilder content = new StringBuilder();

        if (!bEqualPassword) {
            content.append("- Les mots de passe ne correspondent pas.\n");
        }

        if (!bAdd) {
            content.append("- L'adresse doit être au format : 'code postal, ville, rue'.\n");
        }

        alert.setContentText(content.toString());
        alert.showAndWait();
    }

    private void showSignupError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur inscription");
        alert.setHeaderText("Échec de la création de l'utilisateur");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
