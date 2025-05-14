package test;
import javafx.scene.control.TextField;
import services.ServicesContenuCours;

import java.util.List;
import gestioncours.contenttype;
import contenu.ContenuCours;
import gestioncours.Course;
import services.ServicesTreeviewcours;
import services.Servicescours;

import java.sql.SQLException;

public class Main{


    public static void main(String[] args) throws SQLException {
        // Création du contenu du cours avec un record
        ContenuCours contenu = new ContenuCours(0, "Introduction à Java", contenttype.VIDEO, "http://example.com/java-intro",100);
        ContenuCours  contenu1 = new ContenuCours(0, "reseau",contenttype.DOCUMENT,"https://fr.wikipedia.org/wiki/IPv4",100);
        ContenuCours  contenu2 = new ContenuCours(0, "reseau",contenttype.DOCUMENT ,"https://fr.wikipedia.org/wiki/IPv6",104);
        ContenuCours  contenu3 = new ContenuCours(0, "reseauIPv6",contenttype.EXERCISE,"https://www.studocu.com/row/document/the-private-higher-school-of-engineering-and-technology/switched-networks/td-ipv6-avec-correction/80631840",0);

        System.out.println(contenu.getId());
        System.out.println(contenu.getTitre());
        System.out.println(contenu.getType());
        System.out.println(contenu.getUrl());
        System.out.println(contenu.getIdCours());

        Course c = new Course(0, "Java pou débutants", "Informatique", "débutant", "java");
        Course c1 = new Course(0, "ipv4 ","Telecommunication","débutant", "reseau");
        Course c2 = new Course(0, "ipv6 ","Telecommunication","débutant", "reseau");
        Course c3 = new Course(0, "ipv6exerice ","reseau","Avancé", "reseau");
        // Utilisation du service pour ajouter le cours à la base de données
        Servicescours service = new Servicescours();
        try {
            service.ajouter(c);
            service.ajouter(c1);
            service.ajouter(c2);
            service.ajouter(c3);


        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        ServicesContenuCours serviceContenu = new ServicesContenuCours(); // Assurez-vous d'utiliser le bon service
        try {
            // Ajouter chaque contenu individuellement à la base de données
            serviceContenu.ajouter(contenu);
            serviceContenu.ajouter(contenu1);
            serviceContenu.ajouter(contenu2);


            // Optionnellement, ajouter le cours (si une méthode d'ajout pour Course existe)
            // servicesCours.ajouter(c);  // Assurez-vous que cette méthode existe et qu'elle fonctionne avec un objet Course
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du contenu : " + e.getMessage());
        }


                ServicesTreeviewcours service1= new ServicesTreeviewcours();

        // Exemple d'appel pour ouvrir les contenus du cours ayant l'ID 1
        try {
            service1.ouvrirContenu(1);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ouverture du contenu : " + e.getMessage());
        }

    }
}


    