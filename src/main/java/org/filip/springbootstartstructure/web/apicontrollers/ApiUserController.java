package org.filip.springbootstartstructure.web.apicontrollers;

import org.filip.springbootstartstructure.domain.User;
import org.filip.springbootstartstructure.exceptions.InvalidOldPasswordException;
import org.filip.springbootstartstructure.security.ActiveUserStore;
import org.filip.springbootstartstructure.services.mailservice.EmailService;
import org.filip.springbootstartstructure.services.userservice.IUserService;
import org.filip.springbootstartstructure.utils.APIConstants;
import org.filip.springbootstartstructure.utils.GenericResponse;
import org.filip.springbootstartstructure.web.controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Annotation @RestController is a shortcut from having always to define @ResponseBody
 */
@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    private static final Logger log = LoggerFactory.getLogger(ApiUserController.class);

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

    /*************************************************
     * CREATE Mappings
     *************************************************/

    /*************************************************
     * Read Mappings
     *************************************************/

    @GetMapping("/")
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/enabled")
    public List<User> getAllEnabledUsers(){
        return userService.getUsersByEnabledTrue();
    }

    @GetMapping("/disabled")
    public List<User> getAllDisabledUsers(){
        return userService.getUsersByEnabledFalse();
    }

    @GetMapping("/{email}")
    public User findUserByEmail(@PathVariable("email")String email){
        return userService.findUserByEmail(email);
    }

    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable("id")Long id){
        return userService.findById(id);
    }

    @GetMapping("/{passwordResetToken}")
    public User getUserByPasswordResetToken(@PathVariable("passwordResetToken")String token){
        return userService.getUserByPasswordResetToken(token);
    }

    @GetMapping("/qrurl/{email}")
    public String generateQRUrl(@PathVariable("email")String email) throws UnsupportedEncodingException {
        User user = userService.findUserByEmail(email);
        return userService.generateQRUrl(user);
    }

    @GetMapping("/sessionusers")
    public List<String> getUserFromSessionRegistry(){
        return userService.getUserFromSessionRegistry();
    }


    /*************************************************
     * Update Mapping
     * *************************************************/

    @PostMapping(APIConstants.UPDATE_PASSWORD_USER_URL)
    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    @ResponseBody
    public GenericResponse changeUserPassword(Locale locale, @RequestParam("password")String password,
                                              @RequestParam("oldpassword") String oldPassword){
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!userService.checkIfValidOldPassword(user, oldPassword)){
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, password);
        return new GenericResponse(messageSource.getMessage("message.updatePasswordSuccessful", null, locale));
    }

    /*************************************************
     * Delete Mappings
     *************************************************/





}
