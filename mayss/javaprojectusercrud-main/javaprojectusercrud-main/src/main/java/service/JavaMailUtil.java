package service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaMailUtil {
    public static void sendMail(String recipient, String subject, String text) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        String myAccountEmail = "mayssjaballi@gmail.com";
        String myAccountPassword = "jocv otqj asxd nclw"; // Utilisez un mot de passe d'application si la vérification en deux étapes est activée
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, myAccountPassword);
            }
        });
        session.setDebug(true);

        Message message = prepareMessage(session, myAccountEmail, recipient, subject, text);
        System.out.println(message);
        Transport.send(message);
        System.out.println("Message envoyé avec succès");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient, String subject, String text) throws MessagingException {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            System.out.println(message.getFrom());
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            System.out.println(message.getRecipients(Message.RecipientType.TO));
            message.setSubject(subject);
            System.out.println(message.getSubject());
            message.setText(text);
            System.out.println();
        } catch (AddressException ex) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }
}
