public class JavaMailUtil {
    public static void sendMail(String recipient, String subject, String text) throws MessagingException {
        // même configuration que tu as déjà
        String myAccountEmail = "mayssjaballi@gmail.com";
        String myAccountPassword = "jocv otqj asxd nclw";

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, myAccountPassword);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, recipient, subject, text);
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String from, String to, String subject, String text) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}
