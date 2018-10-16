package com.benjamintanone.aboutapi.controllers;

import com.benjamintanone.aboutapi.models.ContactFormModel;
import com.benjamintanone.aboutapi.models.SimpleModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ContactMeController {
    @GetMapping("/")
    public String helloWorld() {
        return "Hello world!";
    }

    @PostMapping("/simple")
    public SimpleModel postSimpleModel(@Valid @RequestBody SimpleModel model) {
        return model;
    }

    @PostMapping("/contact")
    public void postContactForm(@Valid @RequestBody ContactFormModel contactFormModel) {

    }
}
