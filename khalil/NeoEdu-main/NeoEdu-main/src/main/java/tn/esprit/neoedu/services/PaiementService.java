package tn.esprit.neoedu.services;

import com.itextpdf.layout.element.Image;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;


import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import tn.esprit.neoedu.models.Paiement;
import tn.esprit.neoedu.utils.MyDatabase;

import java.sql.ResultSet;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaiementService implements IService<Paiement> {

    private Connection connection;

    public PaiementService() {
        connection = MyDatabase.getInstance().getConnection();
    }


    @Override
    public void ajouter(Paiement paiement) throws SQLException {
        String req = "INSERT INTO paiement (nom,prenom,cin,num_telephone,niveau_etude,date_inscription," +
                "atelier,cours,methode_paiement,type_abonnement,montant,statut_paiement,fin_abonnement) VALUES " +
                "('"+ paiement.getNom() +"','"+ paiement.getPrenom() +"',"+ paiement.getCin() +","+ paiement.getNum_telephone() +"," +
                "'"+ paiement.getNiveau_etude() +"','"+ paiement.getDate_inscription() +"','"+ paiement.getAtelier() +"'," +
                "'"+ paiement.getCours() +"','"+ paiement.getMethode_paiement() +"','"+ paiement.getType_abonnement() +"'," +
                " "+ paiement.getMontant() +",'"+ paiement.getStatut_paiement() +"','"+ paiement.getFin_abonnement() +"') ";
        Statement st = connection.createStatement();
        st.executeUpdate(req);

    }

    @Override
    public void modifier(Paiement paiement) throws SQLException {
        String req = "UPDATE paiement SET nom = ?, prenom = ?, cin = ?,niveau_etude = ?," +
                "atelier = ?,cours = ?,methode_paiement = ?,type_abonnement = ?,montant = ?,statut_paiement = ? WHERE " +
                "id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, paiement.getNom());
        ps.setString(2, paiement.getPrenom());
        ps.setInt(3, paiement.getCin());
        ps.setString(4, paiement.getNiveau_etude());
        ps.setString(5, paiement.getAtelier());
        ps.setString(6, paiement.getCours());
        ps.setString(7, paiement.getMethode_paiement());
        ps.setString(8, paiement.getType_abonnement());
        ps.setInt(9, paiement.getMontant());
        ps.setString(10, paiement.getStatut_paiement());
        ps.setInt(11, paiement.getId());
        ps.executeUpdate();

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM paiement WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();

    }


    public void generateInvoice(Paiement paiement) {
        try {
            // Chemin du fichier PDF
            String filePath = "facture_" + paiement.getId() + ".pdf";

            String imagePath = "C:/Users/DELL/Desktop/sign.png";
            String imagePath2 = "C:/Users/DELL/Desktop/neoeducc.png";


            // Création du PDF
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Ajouter l'image au PDF
            try {
                ImageData imageData = ImageDataFactory.create(imagePath);
                Image image = new Image(imageData);
                image.setWidth(200);  // Largeur de l'image
                image.setHeight(200); // Hauteur de l'image
                image.setFixedPosition(210, 20);
                document.add(image);
            } catch (Exception e) {
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }
            try {
                ImageData imageData = ImageDataFactory.create(imagePath2);
                Image image = new Image(imageData);
                image.setWidth(100);  // Largeur de l'image
                image.setHeight(100); // Hauteur de l'image
                image.setFixedPosition(480, 750);
                document.add(image);
            } catch (Exception e) {
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }

            // Contenu de la facture
            document.add(new Paragraph("Facture de Paiement"));
            document.add(new Paragraph("Nom: " + paiement.getNom()));
            document.add(new Paragraph("Prénom: " + paiement.getPrenom()));
            document.add(new Paragraph("CIN: " + paiement.getCin()));
            document.add(new Paragraph("Niveau d'étude: " + paiement.getNiveau_etude()));
            document.add(new Paragraph("Atelier: " + paiement.getAtelier()));
            document.add(new Paragraph("Cours: " + paiement.getCours()));
            document.add(new Paragraph("Méthode de paiement: " + paiement.getMethode_paiement()));
            document.add(new Paragraph("Type d'abonnement: " + paiement.getType_abonnement()));
            document.add(new Paragraph("Montant: " + paiement.getMontant() + " TND"));
            document.add(new Paragraph("Statut de paiement: " + paiement.getStatut_paiement()));

            if (paiement.getFin_abonnement() != null) {
                document.add(new Paragraph("Date de fin d'abonnement: " + paiement.getFin_abonnement().toString()));
            }

            // Fermer le document
            document.close();
            System.out.println("Facture générée : " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public class EmailSender {

        public static void sendEmailWithAttachment(String recipient, String subject, String body, String filePath) throws MessagingException {
            // Paramètres de la session de messagerie
            String myAccountEmail = "rengod3002@gmail.com";
            String myAccountPassword = "tdun jcdx jdbg lqcd";

            // Configuration des propriétés pour l'email
            Properties properties = new Properties();
            properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            // Créer la session de messagerie
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, myAccountPassword);
                }
            });

            // Créer le message
            Message message = prepareMessage(session, myAccountEmail, recipient, subject, body, filePath);

            // Envoyer le message
            Transport.send(message);
            System.out.println("Email envoyé avec succès");
        }

        private static Message prepareMessage(Session session, String myAccountEmail, String recipient, String subject, String body, String filePath) throws MessagingException {
            Message message = new MimeMessage(session);

            try {
                message.setFrom(new InternetAddress(myAccountEmail));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                message.setSubject(subject);
                message.setText(body);

                // Attacher le fichier PDF
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.attachFile(new File(filePath));

                // Créer une multipart pour ajouter le fichier
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);
                message.setContent(multipart);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return message;
        }

        public static void main(String[] args) {
            try {
                // Spécifie le chemin du fichier PDF généré
                String pdfPath = "C:/Users/DELL/IdeaProjects/NeoEdu/facture_0.pdf";

                // Envoie un email avec le fichier PDF en pièce jointe
                sendEmailWithAttachment("chammakhikhalil@gmail.com", "Facture de Paiement", "Veuillez trouver la facture en pièce jointe.", pdfPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }






    @Override
    public List<Paiement> recuperer() throws SQLException {
        List<Paiement> paiements = new ArrayList<>();
        String req = "SELECT * FROM paiement";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Paiement paiement = new Paiement();
            paiement.setId(rs.getInt("id"));
            paiement.setNom(rs.getString("nom"));
            paiement.setPrenom(rs.getString("prenom"));
            paiement.setCin(rs.getInt("cin"));
            paiement.setNum_telephone(rs.getInt("num_telephone"));
            paiement.setNiveau_etude(rs.getString("niveau_etude"));
            paiement.setDate_inscription(rs.getDate("date_inscription").toLocalDate());
            paiement.setAtelier(rs.getString("atelier"));
            paiement.setCours(rs.getString("cours"));
            paiement.setMethode_paiement(rs.getString("methode_paiement"));
            paiement.setType_abonnement(rs.getString("type_abonnement"));
            paiement.setMontant(rs.getInt("montant"));
            paiement.setStatut_paiement(rs.getString("statut_paiement"));
            paiement.setFin_abonnement(rs.getDate("fin_abonnement").toLocalDate());

            paiements.add(paiement);



        }
        return paiements;
    }
}
