package tn.esprit.neoedu.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import tn.esprit.neoedu.models.Paiement;
import tn.esprit.neoedu.services.PaiementService;
import tn.esprit.neoedu.utils.MyDatabase;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        PaiementService ps = new PaiementService();

        try {
            //ps.ajouter(new Paiement(787626364,883748364,200,"eezdin","ziduh","lycee","Programmation Java","Introduction à Java","Carte Bancaire","Mensuel","Payé",LocalDate.parse("2025-05-12"),LocalDate.parse("2025-06-12")));
            //ps.ajouter(new Paiement(723236364,885748364,250,"khalil","boom","faculte","Programmation Java","ai","espece","annuel","Payé",LocalDate.parse("2025-04-12"),LocalDate.parse("2025-05-12")));
            //ps.supprimer(1);
            System.out.println(ps.recuperer());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }
}
