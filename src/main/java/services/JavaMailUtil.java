import com.google.protobuf.Message;
import com.mysql.cj.Session;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class JavaMailUtil {

    public static void sendMail(String recipient, String subject, String text) throws MessagingException {
        // Configuration des propriétés pour la connexion SMTP avec Gmail
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");  // Pour utiliser STARTTLS
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");  // Sécuriser la connexion
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");  // Assurer la compatibilité avec TLS 1.2

        // L'email et mot de passe du compte qui envoie l'email
        String myAccountEmail = "mrazizgamerlol00@gmail.com";  // Ton email
        String myAccountPassword = "ibrq ncps zhwz lpfk";  // Mot de passe d'application Gmail

        // Création d'une session avec les informations d'authentification
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, myAccountPassword);
            }
        });

        // Préparer le message à envoyer
        Message message = prepareMessage(session, myAccountEmail, recipient, subject, text);

        // Envoyer le message
        Transport.send(message);
        System.out.println("📧 Email envoyé à " + recipient);
    }

    // Préparer le message (format et destinataire)
    private static Message prepareMessage(Session session, String from, String to, String subject, String text) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));  // L'email de l'expéditeur
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));  // L'email du destinataire
        message.setSubject(subject);  // Sujet de l'email
        message.setText(text);  // Contenu de l'email
        return message;
    }
}
