package org.filip.springbootstartstructure.web.controllers;

import org.filip.springbootstartstructure.domain.User;
import org.filip.springbootstartstructure.domain.VerificationToken;
import org.filip.springbootstartstructure.events.registration.OnRegistrationCompleteEvent;
import org.filip.springbootstartstructure.security.ActiveUserStore;
import org.filip.springbootstartstructure.services.mailservice.EmailService;
import org.filip.springbootstartstructure.services.userservice.IUserService;
import org.filip.springbootstartstructure.utils.PageConstants;
import org.filip.springbootstartstructure.web.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sun.jvm.hotspot.debugger.Page;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;

@Controller
public class RegistrationController {

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

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserDetailsService userDetailsService;

    /*************************************************
     * GET Mappings
     *************************************************/

    /**
     * This comes from the request from the SIGN-UP link on the LOGIN page
     *
     * When the controller receives the request “/user/registration”,
     * it creates the new UserDto object that will back the registration form,
     * binds it and returns – pretty straightforward.
     *
     * @param model
     * @return
     */
    @GetMapping(PageConstants.REGISTRATION_PAGE_URL)
    @PreAuthorize("!isAuthenticated()")
    public String showRegistrationForm(Model model){
        log.debug("Rendering registration page.");
        UserDto accountDto = new UserDto();
        model.addAttribute("user", accountDto);
        return PageConstants.REGISTRATION_PAGE_LOCATION_TEMPLATE;
    }

    /**
     * When the user receives the “Confirm Registration” link they should click on it.
     * Once they do – the controller will extract the value of the token parameter in the resulting GET request
     * and will use it to enable the User.
     *
     * @param request
     * @param model
     * @param token
     * @return
     */
    @GetMapping(PageConstants.REGISTRATION_CONFIRMATION_PAGE_URL)
    public String confirmRegistration(HttpServletRequest request, Model model, @RequestParam("token") String token){
        Locale locale = request.getLocale();
        VerificationToken verificationToken = userService.getVerificationToken(token);
        // the user will be redirected to an error page with the corresponding message if the verification does not exist, for some reason


        if (verificationToken == null) {
            String message = messageSource.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            //return "redirect:/badUser.html?lang=" + locale.getLanguage();
            return PageConstants.REDIRECT_BADUSER_ERROR + "?lang=" + locale.getLanguage();
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        // the user will be redirected to an error page with the corresponding message if the verification has expired
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", messageSource.getMessage("auth.message.expired", null, locale));
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
            //return "redirect:/badUser.html?lang=" + locale.getLanguage();
            return PageConstants.REDIRECT_BADUSER_ERROR + "?lang=" + locale.getLanguage();
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        model.addAttribute("message", messageSource.getMessage("message.accountVerified", null, locale));
        //return "redirect:/login.html?lang=" + locale.getLanguage();
        return PageConstants.REDIRECT_LOGIN + "?lang=" + locale.getLanguage();
        }

    /*************************************************
     * POST Mappings
     *************************************************/

    @PostMapping(PageConstants.REGISTRATION_PAGE_URL)
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, HttpServletRequest request, Errors errors) {

        log.debug("Registering user account with information: {}", userDto);

        User registered = userService.registerNewUserAccount(userDto);
        if(registered == null){
            // result.rejectValue("email", "message.regError");
            return new ModelAndView(PageConstants.REGISTRATION_PAGE_LOCATION_TEMPLATE, "user", userDto);
        }

        try {
            final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            /*
             * The controller will publish a Spring ApplicationEvent to trigger
             * the execution of the creation of a token and to send the verification email.
             * This is as simple as injecting the ApplicationEventPublisher and then using it
             * to publish the registration completion.
             */
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
        } catch (final Exception ex) {
            // Here will be displayed an Error page whenever there is an exception in the logic executed after publishing the event.
            log.warn("Unable to register user", ex);
            return new ModelAndView(PageConstants.EMAIL_ERROR_PAGE_LOCATION_TEMPLATE, "user", userDto);
        }
        return new ModelAndView(PageConstants.SUCCESS_REGISTRATION_PAGE_LOCATION_TEMPLATE, "user", userDto);
    }

}
