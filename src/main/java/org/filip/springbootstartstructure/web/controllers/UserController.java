package org.filip.springbootstartstructure.web.controllers;

import org.filip.springbootstartstructure.security.ActiveUserStore;
import org.filip.springbootstartstructure.services.mailservice.EmailService;
import org.filip.springbootstartstructure.services.userservice.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ActiveUserStore activeUserStore;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Environment env;

    @Autowired
    private IUserService userService;



}
