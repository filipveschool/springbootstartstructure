package org.filip.springbootstartstructure.web.controllers;

import org.filip.springbootstartstructure.services.mailservice.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@Controller
@RequestMapping("/mail")
public class MailController {

    private static final Logger log = LoggerFactory.getLogger(MailController.class);

    private LocalTime localDateTime = LocalTime.now(ZoneId.of("GMT+2"));
    private LocalDate localDate = LocalDate.now(ZoneId.of("GMT+2"));

    @Autowired
    private EmailServiceImpl emailService;


}
