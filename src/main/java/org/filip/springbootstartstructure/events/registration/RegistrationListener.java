package org.filip.springbootstartstructure.events.registration;

import org.filip.springbootstartstructure.domain.User;
import org.filip.springbootstartstructure.services.mailservice.EmailService;
import org.filip.springbootstartstructure.services.userservice.IUserService;
import org.filip.springbootstartstructure.utils.PageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private IUserService service;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment env;

    @Autowired
    private EmailService emailService;


    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    /**
     * The event will be received in this method coming from the controller post method for registration data
     *
     * @param event
     */
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);
        String message = messageSource.getMessage("message.regSucc", null, event.getLocale());
        //String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        String confirmationUrl = event.getAppUrl() + PageConstants.REGISTRATION_CONFIRMATION_PAGE_URL + "?token=" + token;

        emailService.sendSimpleMessage(user.getEmail(), env.getProperty("support.email"), "Registration confirmation",
                message + "\r\n" + confirmationUrl

        );
    }
}
