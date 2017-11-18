package org.filip.springbootstartstructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class MailConfig {

    /**
     * This is a template for the emails, because SimpleMailMessage class supports text formatting
     */
    @Bean
    public SimpleMailMessage templateSimpleMessage(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("This is the test email template for your email:\n%s\n");
        return message;
    }

}
