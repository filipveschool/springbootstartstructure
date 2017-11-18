package org.filip.springbootstartstructure.utils;

public class PageConstants {

    // PREFIX's FOR GENERAL CONTROLLER REQUESTMAPPINGS
    public static final String MAIL_PREFIX_CONTROLLER = "/mail";

    //URL's
    public static final String ROOT_URL = "/";
    public static final String ABOUT_PAGE_URL = "/about";
    public static final String LOGIN_PAGE_URL = "/login";
    public static final String HOME_PAGE_URL = "/home";
    public static final String REGISTRATION_PAGE_URL = "/user/registration";
    public static final String REGISTRATION_CONFIRMATION_PAGE_URL = "/user/registrationConfirm";

    public static final String ERROR_404_PAGE_URL = "error/404";
    public static final String ERROR_403_PAGE_URL = "error/403";
    public static final String ACCESS_DENIED_PAGE_URL = "error/access-denied";
    public static final String ERROR_500_PAGE_URL = "error/500";

    public static final String OVERVIEW_LOGGED_USERS_URL = "user/loggedUsers";
    public static final String OVERVIEW_LOGGED_USERS_FROM_SESSION_REGISTRY_URL = "user/loggedUsersFromSessionRegistry";
    public static final String OVERVIEW_ALL_USERS_URL= "user/overview";


    //PAGES
    public static final String ABOUT_PAGE_LOCATION_TEMPLATE = "pages/about";
    public static final String LOGIN_PAGE_LOCATION_TEMPLATE = "user/login";
    public static final String HOME_PAGE_LOCATION_TEMPLATE = "pages/home";
    public static final String REGISTRATION_PAGE_LOCATION_TEMPLATE = "user/registration";
    //TODO html file template nog aanmaken
    public static final String SUCCESS_REGISTRATION_PAGE_LOCATION_TEMPLATE = "user/successRegister";
    public static final String FORGOT_PASSWORD_LOCATION_TEMPLATE = "user/forgotPassword";

    public static final String ERROR_404_PAGE_LOCATION_TEMPLATE = "error/404";
    public static final String ERROR_403_PAGE_LOCATION_TEMPLATE = "error/403";
    public static final String ACCESS_DENIED_PAGE_LOCATION_TEMPLATE = "error/access-denied";
    public static final String ERROR_500_PAGE_LOCATION_TEMPLATE = "error/500";
    //TODO html file template nog aanmaken
    public static final String EMAIL_ERROR_PAGE_LOCATION_TEMPLATE = "error/emailError";


    public static final String OVERVIEW_LOGGED_USERS_LOCATION_TEMPLATE = "user/overviewLoggedUsers";
    public static final String OVERVIEW_LOGGED_USERS_FROM_SESSION_REGISTRY_LOCATION_TEMPLATE = "user/overviewLoggedUsers";
    public static final String OVERVIEW_ALL_USERS_LOCATION_TEMPLATE = "user/overview";


    // REDIRECT pages

    public static final String REDIRECT_START_PAGE = "redirect:/";
    public static final String REDIRECT_HOME = "redirect:/home";
    public static final String REDIRECT_BADUSER_ERROR = "redirect:/badUser";
    public static final String REDIRECT_LOGIN = "redirect:/login";


}
