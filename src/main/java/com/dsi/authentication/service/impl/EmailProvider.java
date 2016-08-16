package com.dsi.authentication.service.impl;

import org.apache.log4j.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sabbir on 11/24/15.
 */

public class EmailProvider {

    private static final Logger logger = Logger.getLogger(EmailProvider.class);

    public static final String EMAIL_URLS_FILE = "Email.properties";
    private static final Properties emailProp= new Properties();

    static{
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream propIS = loader.getResourceAsStream(EMAIL_URLS_FILE);
            emailProp.load(propIS);
           } catch (IOException e) {
            logger.error("An error occurred while loading urls.", e);
        }
    }

    public static final String EMAIL_TRANSPORT = emailProp.getProperty("mail.smtp.transport");
    public static final String EMAIL_HOST = emailProp.getProperty("mail.smtp.host");
    public static final String EMAIL_USERNAME = emailProp.getProperty("mail.your_mail_username");
    public static final String EMAIL_PASSWORD = emailProp.getProperty("mail.your_mail_password");

    public static boolean constructResetPasswordRequestToken(String recipientEmail, String url){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String subject = "Reset Password";
                    logger.info("Confirm Url: " + url);

                    Session session = Session.getDefaultInstance(emailProp, null);
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(EmailProvider.EMAIL_USERNAME));
                    message.addRecipient(Message.RecipientType.TO,new InternetAddress(recipientEmail));
                    message.setSubject(subject);
                    message.setText("<b> Reset Password: </b>" + " \r\t " + url);

                    //send message
                    Transport transport = session.getTransport(EmailProvider.EMAIL_TRANSPORT);
                    transport.connect(EmailProvider.EMAIL_HOST, EmailProvider.EMAIL_USERNAME, EmailProvider.EMAIL_PASSWORD);
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();

                } catch (Exception e){
                    logger.error("Email confirmation failed: " + e.getMessage());
                }
            }
        }).start();
        return true;
    }

    public static boolean constructPasswordCreate(String recipientEmail, String password){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String subject = "User Info";

                    Session session = Session.getDefaultInstance(emailProp, null);
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(EmailProvider.EMAIL_USERNAME));
                    message.addRecipient(Message.RecipientType.TO,new InternetAddress(recipientEmail));
                    message.setSubject(subject);
                    message.setText("Your account create successfully."
                            + " \r\t " + "<b> Password: </b>" + password);

                    //send message
                    Transport transport = session.getTransport(EmailProvider.EMAIL_TRANSPORT);
                    transport.connect(EmailProvider.EMAIL_HOST, EmailProvider.EMAIL_USERNAME, EmailProvider.EMAIL_PASSWORD);
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();

                } catch (Exception e){
                    logger.error("Email confirmation failed: " + e.getMessage());
                }
            }
        }).start();
        return true;
    }
}
