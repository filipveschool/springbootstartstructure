package org.filip.springbootstartstructure.web.controllers;

import org.filip.springbootstartstructure.security.ActiveUserStore;
import org.filip.springbootstartstructure.services.mailservice.EmailService;
import org.filip.springbootstartstructure.services.userservice.IUserService;
import org.filip.springbootstartstructure.utils.PageConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

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

    @GetMapping(PageConstants.OVERVIEW_LOGGED_USERS_URL)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getLoggedUsers(Locale locale, Model model){
        model.addAttribute("users", activeUserStore.getUsers());
        return PageConstants.OVERVIEW_LOGGED_USERS_LOCATION_TEMPLATE;
    }

    @GetMapping(PageConstants.OVERVIEW_LOGGED_USERS_FROM_SESSION_REGISTRY_URL)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getLoggedUsersFromSessionRegistry(Locale locale, Model model){
        model.addAttribute("users", userService.getUsersFromSessionRegistry());
        return PageConstants.OVERVIEW_LOGGED_USERS_FROM_SESSION_REGISTRY_LOCATION_TEMPLATE;
    }

    @GetMapping(PageConstants.OVERVIEW_ALL_USERS_URL)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAllUsersOverview(Model model){
        model.addAttribute("users", userService.findAll());
        return PageConstants.OVERVIEW_ALL_USERS_LOCATION_TEMPLATE;
    }









}
