
package Entities;

import javafx.scene.control.Button;

import java.sql.Timestamp;

public class User {
    private int id;
    private String nom;
    private String password;
    private String email;
    private String prenom;
    private String role;
    private boolean is_verified;
    private String adress;
    private String ville;
    private int zipcode;
    private String reset_token;
    private Timestamp reset_token_expired_at;
    private String verified;

    private Button blockButton;



    // Constructors


    public User() {
    }

    public User(String nom, String password, String email, String prenom, String adress, String ville, int zipcode,String role) {
        this.nom = nom;
        this.password = password;
        this.email = email;
        this.prenom = prenom;
        this.adress = adress;
        this.ville = ville;
        this.zipcode = zipcode;
        this.role = role;}

    public User(int id,String nom, String password, String email, String prenom, String adress, String ville, int zipcode,String role,boolean is_verified,String verified) {
        this.id = id;
        this.nom = nom;
        this.password = password;
        this.email = email;
        this.prenom = prenom;
        this.adress = adress;
        this.ville = ville;
        this.zipcode = zipcode;
        this.role = role;
        this.is_verified = is_verified;
        this.verified=verified;
    }
    public User(int id, String nom, String password, String email, String prenom, String role, boolean is_verified, String adress, String ville, int zipcode, String reset_token, Timestamp reset_token_expired_at,String verified) {
        this.id = id;
        this.nom = nom;
        this.password = password;
        this.email = email;
        this.prenom = prenom;
        this.role = role;
        this.is_verified = is_verified;
        this.adress = adress;
        this.ville = ville;
        this.zipcode = zipcode;
        this.reset_token = reset_token;
        this.reset_token_expired_at = reset_token_expired_at;
        this.verified=verified;
    }

    public User(String nom, String password, String email, String prenom, String role, boolean is_verified, String adress, String ville, int zipcode, String reset_token, Timestamp reset_token_expired_at,String verified) {
        this.nom = nom;
        this.password = password;
        this.email = email;
        this.prenom = prenom;
        this.role = role;
        this.is_verified = is_verified;
        this.adress = adress;
        this.ville = ville;
        this.zipcode = zipcode;
        this.reset_token = reset_token;
        this.reset_token_expired_at = reset_token_expired_at;
        this.verified=verified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    public Timestamp getReset_token_expired_at() {
        return reset_token_expired_at;
    }

    public void setReset_token_expired_at(Timestamp reset_token_expired_at) {
        this.reset_token_expired_at = reset_token_expired_at;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public Button getBlockButton() {
        return blockButton;
    }

    public void setBlockButton(Button blockButton) {
        this.blockButton = blockButton;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", prenom='" + prenom + '\'' +
                ", role='" + role + '\'' +
                ", is_verified=" + is_verified +
                ", adress='" + adress + '\'' +
                ", ville='" + ville + '\'' +
                ", zipcode=" + zipcode +
                ", reset_token='" + reset_token + '\'' +
                ", reset_token_expired_at=" + reset_token_expired_at +
                '}';
    }

    public boolean hasRole(String role) {
        return this.role.equals(role);
    }
}
