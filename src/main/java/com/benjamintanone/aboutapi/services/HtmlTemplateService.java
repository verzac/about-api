package com.benjamintanone.aboutapi.services;

import com.benjamintanone.aboutapi.models.Session;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@NoArgsConstructor
@AllArgsConstructor
public class HtmlTemplateService {
    @Autowired
    private TemplateEngine templateEngine;

    public String buildConfirmationEmail(Session session) {
        Context context = new Context();
        context.setVariable("challenge", session.getChallenge());
        context.setVariable("firstName", session.getContactForm().getFirstName());
        return templateEngine.process("confirmation_email", context);
    }
}
