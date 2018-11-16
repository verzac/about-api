package com.benjamintanone.aboutapi.controllers;

import com.benjamintanone.aboutapi.exceptions.BadRequestException;
import com.benjamintanone.aboutapi.exceptions.DataIntegrityException;
import com.benjamintanone.aboutapi.models.ContactForm;
import com.benjamintanone.aboutapi.models.Session;
import com.benjamintanone.aboutapi.models.SimpleModel;
import com.benjamintanone.aboutapi.models.SimpleResponse;
import com.benjamintanone.aboutapi.services.MailService;
import com.benjamintanone.aboutapi.repositories.SessionRepository;
import com.benjamintanone.aboutapi.services.RandomizerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ContactMeController {

    @Value("${form.mail.to}")
    private String destinationEmail;

    @Autowired
    private MailService mailService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RandomizerService randomizerService;

    @GetMapping("/")
    public SimpleResponse helloWorld() {
        return SimpleResponse.builder().message("Hey there!").build();
    }

    @PostMapping("/contact")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void postContactForm(@Valid @RequestBody ContactForm contactForm) {
        // mailService.sendSimpleMessageTo(destinationEmail, "Contact Form", contactForm.toString());
        String challengeToken = randomizerService.getNewChallengeToken();
        Session session = Session.builder()
                .challenge(challengeToken)
                .contactForm(contactForm)
                .build();
        mailService.sendConfirmationEmail(session).join(); // since this is the last thing anyways
        sessionRepository.save(session);
        // return SimpleResponse.builder().message("Contact form submitted. Please see email for confirmation.").build();
    }

    @PostMapping("/contact/confirm/{challengeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void postContactFormConfirmation(@PathVariable("challengeId") String challenge) {
        List<Session> sessions = sessionRepository.findByChallenge(challenge);
        if (sessions.size() > 1) {
            throw new DataIntegrityException("Detected more than one session with the same challenge!");
        } else if (sessions.size() == 0) {
            throw new BadRequestException(String.format("No sessions detected with challenge %s", challenge));
        }
        ContactForm formToBeSent = sessions.get(0).getContactForm();
        CompletableFuture mailFuture = mailService.sendSimpleMessageTo(destinationEmail,
                "Message from " + formToBeSent.getFirstName() + " " + formToBeSent.getLastName(),
                formToBeSent.toString());
        CompletableFuture repoFuture = CompletableFuture.runAsync(() -> sessionRepository.delete(sessions.get(0)));
        CompletableFuture.allOf(mailFuture, repoFuture).join();
    }
}
