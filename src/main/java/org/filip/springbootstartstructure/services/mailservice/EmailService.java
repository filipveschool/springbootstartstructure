package org.filip.springbootstartstructure.services.mailservice;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    /**
     *
     * @param to The email-address that the email has to be sended to
     * @param subject The subject of the Email
     * @param text The content that has to be posted into the email.
     */
    void sendSimpleMessage(String to, String subject, String text);

    /**
     *
     * @param to
     * @param subject
     * @param template
     * @param templateArgs
     */
    void sendSimpleMessageUsingTemplate(String to, String subject, SimpleMailMessage template, String ...templateArgs);

    /**
     *
     * @param to
     * @param subject
     * @param text
     * @param pathToAttachment
     */
    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);
}
