package com.hvt.ultimatespy.utils.mail;

import com.hvt.ultimatespy.config.Config;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailUtils {

    private static final Logger logger = Logger.getLogger(MailUtils.class.getName());

    public static CompletableFuture<Boolean> send(String toMail, String subject, String msg) {
        return CompletableFuture.supplyAsync(() -> {
            boolean sendSuccess = true;

            try {
                Properties prop = new Properties();
                prop.put("mail.smtp.auth", Config.prop.getProperty("mail.smtp.auth"));
                prop.put("mail.smtp.starttls.enable", Config.prop.getProperty("mail.smtp.starttls.enable"));
                prop.put("mail.smtp.host", Config.prop.getProperty("mail.smtp.host"));
                prop.put("mail.smtp.port", Config.prop.getProperty("mail.smtp.port"));
                prop.put("mail.smtp.ssl.trust", Config.prop.getProperty("mail.smtp.ssl.trust"));

                Session session = Session.getInstance(prop, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                Config.prop.getProperty("mail.smtp.username"),
                                Config.prop.getProperty("mail.smtp.password"));
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(Config.prop.getProperty("mail.smtp.from")));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
                message.setSubject(subject);
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(msg, "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);
                message.setContent(multipart);

                Transport.send(message);
            } catch (Exception e) {
                sendSuccess = false;
                logger.log(Level.SEVERE, "", e);
            }

            return sendSuccess;
        });
    }

}
