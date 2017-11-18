package org.filip.springbootstartstructure.web.controllers;

import org.filip.springbootstartstructure.utils.PageConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This is a simple controller for all simple pages that only need a return to a page
 */
@Controller
public class PagesController {

    private static final Logger log = LoggerFactory.getLogger(PagesController.class);

    @GetMapping(PageConstants.ABOUT_PAGE_URL)
    public String goToAboutPage() {
        //return "pages/about";
        return PageConstants.ABOUT_PAGE_LOCATION_TEMPLATE;
    }

}
