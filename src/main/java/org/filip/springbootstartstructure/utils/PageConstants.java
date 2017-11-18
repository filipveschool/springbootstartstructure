package org.filip.springbootstartstructure.utils;

public class PageConstants {

    // PREFIX's FOR GENERAL CONTROLLER REQUESTMAPPINGS
    public static final String MAIL_PREFIX_CONTROLLER = "/mail";
    public static final String LOCALE_PARAM_FOR_URL = "?lang=";

    //URL's
    public static final String ROOT_URL = "/";
    public static final String ABOUT_PAGE_URL = "/about";
    public static final String LOGIN_PAGE_URL = "/login";
    public static final String HOME_PAGE_URL = "/home";
    public static final String REGISTRATION_PAGE_URL = "/user/registration";
    public static final String REGISTRATION_POST_PAGE_URL = "/registration";
    public static final String REGISTRATION_CONFIRMATION_PAGE_URL = "/user/registrationConfirm";
    public static final String RESEND_REGISTRATION_TOKEN_PAGE_URL = "/user/resendRegistrationToken";
    public static final String RESET_PASSWORD_PAGE_URL = "/user/resetPassword";
    public static final String SAVE_PASSWORD_PAGE_URL = "/user/savePassword";
    public static final String CHANGE_PASSWORD_PAGE_URL = "/user/changePassword";
    public static final String UPDATE_PASSWORD_PAGE_URL = "/user/updatePassword";
    public static final String USER_TWO_FACTOR_AUTH_PAGE_URL = "/user/update/2fa";

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
    public static final String INDEX_LOCATION_TEMPLATE = "index";
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
    public static final String BAD_USER_PAGE_LOCATION_TEMPLATE = "error/badUser";


    public static final String OVERVIEW_LOGGED_USERS_LOCATION_TEMPLATE = "user/overviewLoggedUsers";
    public static final String OVERVIEW_LOGGED_USERS_FROM_SESSION_REGISTRY_LOCATION_TEMPLATE = "user/overviewLoggedUsers";
    public static final String OVERVIEW_ALL_USERS_LOCATION_TEMPLATE = "user/overview";


    // REDIRECT pages

    public static final String REDIRECT_START_PAGE = "redirect:/";
    public static final String REDIRECT_HOME = "redirect:/home";
    public static final String REDIRECT_BADUSER_ERROR = "redirect:/badUser";
    public static final String REDIRECT_LOGIN = "redirect:/login";
    public static final String REDIRECT_EMAIL_ERROR = "redirect:/emailError";
    public static final String REDIRECT_UPDATE_PASSWORD = "redirect:/updatePassword";
    public static final String REDIRECT_QRCODE = "redirect:/qrcode";


}
