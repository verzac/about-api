package com.benjamintanone.aboutapi.controllers;

import com.benjamintanone.aboutapi.models.ContactFormModel;
import com.benjamintanone.aboutapi.models.SimpleModel;
import com.benjamintanone.aboutapi.services.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class ContactMeController {
    @Autowired
    private MailService mailService;

    @Value("${form.mail.to}")
    private String destinationEmail;

    @GetMapping("/")
    public String helloWorld() {
        return "Hello world!";
    }

    @PostMapping("/simple")
    public SimpleModel postSimpleModel(@Valid @RequestBody SimpleModel model) {
        mailService.sendMessageTo("benjamin.tanone@gmail.com", "TEST SUBJECT", "HELLO THERE Wassup");
        return model;
    }

    @PostMapping("/contact")
    public void postContactForm(@Valid @RequestBody ContactFormModel contactFormModel) {
        mailService.sendMessageTo(destinationEmail, "Contact Form", contactFormModel.toString());
    }
}
