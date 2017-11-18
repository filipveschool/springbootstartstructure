package org.filip.springbootstartstructure.services.mailservice;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    /**
     *
     * @param to The email-address that the email has to be sended to
     * @param from      The email-address that the email comes from
     * @param subject The subject of the Email
     * @param text The content that has to be posted into the email.
     */
    void sendSimpleMessage(String to, String from, String subject, String text);

    /**
     *
     * @param to
     * @param from
     * @param subject
     * @param template
     * @param templateArgs
     */
    void sendSimpleMessageUsingTemplate(String to, String from, String subject, SimpleMailMessage template, String ...templateArgs);

    /**
     *
     * @param to
     * @param from
     * @param subject
     * @param text
     * @param pathToAttachment
     */
    void sendMessageWithAttachment(String to, String from, String subject, String text, String pathToAttachment);
}
