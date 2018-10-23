package com.benjamintanone.aboutapi.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${form.mail.subject_prefix}")
    private String subjectPrefix;

    @Async
    public CompletableFuture sendMessageTo(String toEmail, String subject, String messageBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subjectPrefix + " " + subject);
        message.setText(messageBody);
        mailSender.send(message);
        return CompletableFuture.completedFuture(null);
    }
}
