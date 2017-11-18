package org.filip.springbootstartstructure.services.mailservice;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;

@Component
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    /**
     * @param to      The email-address that the email has to be sended to
     * @param subject The subject of the Email
     * @param text    The content that has to be posted into the email.
     */
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * @param to
     * @param subject
     * @param template
     * @param templateArgs
     */
    @Override
    public void sendSimpleMessageUsingTemplate(String to, String subject, SimpleMailMessage template, String... templateArgs) {
        String text = String.format(Objects.requireNonNull(template.getText()), templateArgs);
        sendSimpleMessage(to, subject, text);
    }

    /**
     * @param to
     * @param subject
     * @param text
     * @param pathToAttachment
     */
    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message;
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            emailSender.send(message);
        } catch (MessagingException exc) {
            exc.printStackTrace();
        }
    }
}

