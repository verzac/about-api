package com.benjamintanone.aboutapi.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    private Queue<SimpleMailMessage> mailQueue = new LinkedList<>();

    @Value("${form.mail.subject_prefix}")
    private String subjectPrefix;

    @Async
    public CompletableFuture sendMessageTo(String toEmail, String subject, String messageBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subjectPrefix + " " + subject);
        message.setText(messageBody);
        mailQueue.add(message);
        return CompletableFuture.completedFuture(null);
    }

    @Scheduled(fixedDelay = 1000)
    public void dispatchMessage() {
        List<CompletableFuture> futures = new ArrayList<>();
        while (!mailQueue.isEmpty()) {
            // because it takes forever just to send a simple email
            SimpleMailMessage mail = mailQueue.remove();
            futures.add(CompletableFuture.runAsync(() -> {
                mailSender.send(mail);
            }));
        }
        futures.forEach(CompletableFuture::join);
    }
}
