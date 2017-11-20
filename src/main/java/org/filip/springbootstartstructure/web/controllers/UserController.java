package org.filip.springbootstartstructure.web.controllers;

import org.filip.springbootstartstructure.domain.User;
import org.filip.springbootstartstructure.exceptions.UserNotFoundException;
import org.filip.springbootstartstructure.security.ActiveUserStore;
import org.filip.springbootstartstructure.services.mailservice.EmailService;
import org.filip.springbootstartstructure.services.userservice.IUserService;
import org.filip.springbootstartstructure.utils.PageConstants;
import org.filip.springbootstartstructure.web.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

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
    public String getLoggedUsers(Locale locale, Model model) {
        model.addAttribute("users", activeUserStore.getUsers());
        return PageConstants.OVERVIEW_LOGGED_USERS_LOCATION_TEMPLATE;
    }

    @GetMapping(PageConstants.OVERVIEW_LOGGED_USERS_FROM_SESSION_REGISTRY_URL)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getLoggedUsersFromSessionRegistry(Locale locale, Model model) {
        model.addAttribute("users", userService.getUsersFromSessionRegistry());
        return PageConstants.OVERVIEW_LOGGED_USERS_FROM_SESSION_REGISTRY_LOCATION_TEMPLATE;
    }

    @GetMapping(PageConstants.OVERVIEW_ALL_USERS_URL)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAllUsersOverview(Model model) {
        model.addAttribute("users", userService.findAll());
        return PageConstants.OVERVIEW_ALL_USERS_LOCATION_TEMPLATE;
    }

    /**
     * CRUD
     */


    /**
     * Not by registration form
     *
     * @return
     */
    @GetMapping("/users/create")
    public String createNewUser(Model model) {
        model.addAttribute("user", new User());
        return "crud/user/create";
    }

    /**
     * Not by registration form
     *
     * @return
     */
    @PostMapping("/users/save")
    public String saveNewUser(@Valid User user) {
        System.out.println(user.toString());
        userService.saveRegisteredUser(user);
        return "redirect:/" + PageConstants.OVERVIEW_ALL_USERS_URL;
    }

    //

    @GetMapping(value = "/users/edit/{id}")
    public String updateAccount(Model model, @PathVariable("id") Long id) {

        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }

        UserDto accountDto = new UserDto();
        accountDto.setId(user.get().getId());
        accountDto.setFirstName(user.get().getFirstName());
        accountDto.setLastName(user.get().getLastName());
        accountDto.setEmail(user.get().getEmail());
        accountDto.setRoles(user.get().getRoles());
        accountDto.setPassword(user.get().getPassword());
        accountDto.setMatchingPassword(user.get().getPassword());
        accountDto.setTimezone(user.get().getTimezone());

/*
        User user = userService.getOne(id);

        UserDto accountDto = new UserDto();
        accountDto.setFirstName(user.getFirstName());
        accountDto.setLastName(user.getLastName());
        accountDto.setEmail(user.getEmail());
        accountDto.setRoles(user.getRoles());
        accountDto.setPassword(user.getPassword());
        accountDto.setMatchingPassword(user.getPassword());
        accountDto.setTimezone(user.getTimezone());
        */

        model.addAttribute("user", accountDto);

        return "crud/user/edit";
    }

    @PostMapping("/users/update")
    //@PostMapping("/users/update/{id}")
    //@PostMapping("/users/update/{user}")
    //public String updateUser(@ModelAttribute("user") @Valid UserDto userDto) {
    public String updateUser(@ModelAttribute UserDto user, BindingResult result) {

        if(result.hasErrors()){
            return "crud/users/edit";
        }

        userService.updateExistingUser(user);
        return "redirect:/" + PageConstants.OVERVIEW_ALL_USERS_URL;

    }

//    //-------
//
//    @GetMapping(value = "/users/editdirect/{id}")
//    public String updateAccountDirect(Model model, @PathVariable("id") long id) {
//
//        Optional<User> user = userService.findById(id);
//        if (!user.isPresent()) {
//            throw new UserNotFoundException();
//        }
//        model.addAttribute("user", user.get());
//
//        return "crud/user/edit";
//    }
//
//    @PostMapping("/users/updatedirect")
//    //public User updateUserDirectly(@ModelAttribute("user") @Valid User user) {
//    public String updateUserDirectly(@ModelAttribute User user) {
//
//        User user1 = userService.updateExistingUserDirectly(user);
//        System.out.println(user.toString());
//        System.out.println("");
//        return "redirect:/" + PageConstants.OVERVIEW_ALL_USERS_URL;
//    }


    // DEZE WERKT

    //DELETE
    @GetMapping("/users/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/" + PageConstants.OVERVIEW_ALL_USERS_URL;
    }


}
