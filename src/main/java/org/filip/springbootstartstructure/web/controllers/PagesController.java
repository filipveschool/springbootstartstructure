package org.filip.springbootstartstructure.web.controllers;

import org.filip.springbootstartstructure.utils.PageConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * This is a simple controller for all simple pages that only need a return to a page
 */
@Controller
public class PagesController {

    private static final Logger log = LoggerFactory.getLogger(PagesController.class);

    private LocalTime localDateTime = LocalTime.now(ZoneId.of("GMT+2"));
    private LocalDate localDate = LocalDate.now(ZoneId.of("GMT+2"));

    @GetMapping(PageConstants.ROOT_URL)
    public String redirectToHomePage(){
        return PageConstants.REDIRECT_START_PAGE;
    }

    @GetMapping(PageConstants.HOME_PAGE_URL)
    public String goToHomePage(){
        return PageConstants.HOME_PAGE_URL;
    }

    @GetMapping(PageConstants.ABOUT_PAGE_URL)
    public String goToAboutPage() {
        log.info("Op de datum: " + localDate + " om : " + localDateTime + " - is er naar de aboutPagina gesurft." );

        //return "pages/about";
        return PageConstants.ABOUT_PAGE_LOCATION_TEMPLATE;
    }

}
