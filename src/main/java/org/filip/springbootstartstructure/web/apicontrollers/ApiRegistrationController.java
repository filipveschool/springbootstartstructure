package org.filip.springbootstartstructure.web.apicontrollers;

import org.filip.springbootstartstructure.domain.User;
import org.filip.springbootstartstructure.domain.VerificationToken;
import org.filip.springbootstartstructure.events.registration.OnRegistrationCompleteEvent;
import org.filip.springbootstartstructure.exceptions.InvalidOldPasswordException;
import org.filip.springbootstartstructure.security.ActiveUserStore;
import org.filip.springbootstartstructure.services.mailservice.EmailService;
import org.filip.springbootstartstructure.services.security.ISecurityUserService;
import org.filip.springbootstartstructure.services.userservice.IUserService;
import org.filip.springbootstartstructure.utils.GenericResponse;
import org.filip.springbootstartstructure.utils.PageConstants;
import org.filip.springbootstartstructure.web.dto.PasswordDto;
import org.filip.springbootstartstructure.web.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ApiRegistrationController {

    private final Logger LOGGER = LoggerFactory.getLogger(ApiRegistrationController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private ISecurityUserService securityUserService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private Environment env;

    @Autowired
    private ActiveUserStore activeUserStore;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserDetailsService userDetailsService;

    public ApiRegistrationController() {
        super();
    }

    @PostMapping(PageConstants.REGISTRATION_PAGE_URL)
    @ResponseBody
    public GenericResponse registerUserAccount(@Valid UserDto accountDto, HttpServletRequest request) {
        LOGGER.debug("Registering user account with information: {}", accountDto);

        final User registered = userService.registerNewUserAccount(accountDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
        return new GenericResponse("success");
    }

    @GetMapping(PageConstants.REGISTRATION_CONFIRMATION_PAGE_URL)
    public String confirmRegistration(Locale locale, Model model, @RequestParam("token") String token) throws UnsupportedEncodingException {
        String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            User user = userService.getUser(token);
            System.out.println(user);
            /*
            if (user.isUsing2FA()) {
                model.addAttribute("qr", userService.generateQRUrl(user));
                return "redirect:/qrcode.html?lang=" + locale.getLanguage();
                return PageConstants.REDIRECT_QRCODE + "?lang=" + locale.getLanguage();
            }
            */


            model.addAttribute("message", messageSource.getMessage("message.accountVerified", null, locale));
            //return "redirect:/login?lang=" + locale.getLanguage();
            return PageConstants.REDIRECT_LOGIN + "?lang=" + locale.getLanguage();
        }

        model.addAttribute("message", messageSource.getMessage("auth.message." + result, null, locale));
        model.addAttribute("expired", "expired".equals(result));
        model.addAttribute("token", token);
        //return "redirect:/badUser.html?lang=" + locale.getLanguage();
        return PageConstants.REDIRECT_BADUSER_ERROR + "?lang=" + locale.getLanguage();
    }

    // user activation - verification

    @GetMapping(PageConstants.RESEND_REGISTRATION_TOKEN_PAGE_URL)
    @ResponseBody
    public GenericResponse resendRegistrationToken(HttpServletRequest request, @RequestParam("token") String existingToken) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        User user = userService.getUser(newToken.getToken());
        sendConstructedResetVerificationTokenEmail(getAppUrl(request), request.getLocale(), newToken, user);
        return new GenericResponse(messageSource.getMessage("message.resendToken", null, request.getLocale()));
    }

    // Reset password

    /**
     * Create a new passwordResetToken and send it via email to the user
     *
     * @param request
     * @param userEmail
     * @return
     */
    //@RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
    @PostMapping(PageConstants.RESET_PASSWORD_PAGE_URL)
    @ResponseBody
    public GenericResponse resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            sendConstructedResetTokenEmail(getAppUrl(request), request.getLocale(), token, user);
        }
        return new GenericResponse(messageSource.getMessage("message.resetPasswordEmail", null, request.getLocale()));
    }

    /**
     * The user gets the email with the unique link for resetting their password,
     * and clicks the link to process the PasswordResetToken
     *
     * @param locale
     * @param model
     * @param id
     * @param token
     * @return
     */
    @GetMapping(PageConstants.CHANGE_PASSWORD_PAGE_URL)
    public String showChangePasswordPage(Locale locale, Model model, @RequestParam("id") long id, @RequestParam("token") String token) {
        String result = securityUserService.validatePasswordResetToken(id, token);
        if (result != null) {
            model.addAttribute("message", messageSource.getMessage("auth.message." + result, null, locale));
            //return "redirect:/login?lang=" + locale.getLanguage();
            return PageConstants.REDIRECT_LOGIN + "?lang=" + locale.getLanguage();
        }
        //return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
        return PageConstants.REDIRECT_UPDATE_PASSWORD + "?lang=" + locale.getLanguage();
    }

    /**
     *
     * @param locale
     * @param passwordDto
     * @return
     */
    @PostMapping(PageConstants.SAVE_PASSWORD_PAGE_URL)
    @ResponseBody
    public GenericResponse savePassword(Locale locale, @Valid PasswordDto passwordDto) {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return new GenericResponse(messageSource.getMessage("message.resetPasswordSuc", null, locale));
    }

    /**
     * Change user password
     *
     *
     *
     * @param locale
     * @param passwordDto
     * @return
     */
    @PostMapping(PageConstants.UPDATE_PASSWORD_PAGE_URL)
    @ResponseBody
    public GenericResponse changeUserPassword(Locale locale, @Valid PasswordDto passwordDto) {
        User user = userService.findUserByEmail(((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getEmail());
        if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return new GenericResponse(messageSource.getMessage("message.updatePasswordSuc", null, locale));
    }


    /*
    @PostMapping(PageConstants.USER_TWO_FACTOR_AUTH_PAGE_URL)
    @ResponseBody
    public GenericResponse modifyUser2FA(@RequestParam("use2FA") boolean use2FA) throws UnsupportedEncodingException {
        final User user = userService.updateUser2FA(use2FA);
        if (use2FA) {
            return new GenericResponse(userService.generateQRUrl(user));
        }
        return null;
    }
    */

    // UTIL FUNCTIONS

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private void sendConstructedResetVerificationTokenEmail(String contextPath, Locale locale, VerificationToken newToken, User user) {
        String confirmationUrl = contextPath + "/registrationConfirm?token=" + newToken.getToken();
        String message = messageSource.getMessage("message.resendToken", null, locale);

        emailService.sendSimpleMessage(user.getEmail(),env.getProperty("support.email"),"Resend Registration Token", message + " \r\n" + confirmationUrl);
    }

    private void sendConstructedResetTokenEmail(String contextPath, Locale locale, String token, User user) {
        String url = contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
        String message = messageSource.getMessage("message.resetPassword", null, locale);
        emailService.sendSimpleMessage(user.getEmail(), env.getProperty("support.email"), "Reset Password", message + " \r\n" + url);
    }


}
