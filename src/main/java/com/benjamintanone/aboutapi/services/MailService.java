package com.benjamintanone.aboutapi.services;

import com.benjamintanone.aboutapi.models.Session;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

    @Autowired
    private HtmlTemplateService htmlTemplateService;

    private Queue<SimpleMailMessage> simpleMailMessageQueue = new LinkedList<>();

    private Queue<MimeMessage> mimeMessageQueue = new LinkedList<>();

    @Value("${form.mail.subject_prefix}")
    private String subjectPrefix;

    @Async
    public CompletableFuture sendSimpleMessageTo(String toEmail, String subject, String messageBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subjectPrefix + " " + subject);
        message.setText(messageBody);
        simpleMailMessageQueue.add(message);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture sendConfirmationEmail(Session session) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setTo(session.getContactForm().getEmail());
            messageHelper.setSubject(subjectPrefix + " " + "Benjamin Tanone - Confirmation Email for Contact Form Submission");
            messageHelper.setText(htmlTemplateService.buildConfirmationEmail(session), true);
        } catch (MessagingException e) {
            log.error("Unable to craft confirmation email.", e);
        }
        mimeMessageQueue.add(mimeMessage);
        log.info(String.format("Adding email with recipient <%s> to queue.", session.getContactForm().getEmail()));
        return CompletableFuture.completedFuture(null);
    }

    @Scheduled(fixedDelay = 1000)
    public void dispatchMessage() {
        List<CompletableFuture> futures = new ArrayList<>();
        while (!simpleMailMessageQueue.isEmpty()) {
            // because it takes forever just to send a simple email
            SimpleMailMessage mail = simpleMailMessageQueue.remove();
            futures.add(CompletableFuture.runAsync(() -> {
                mailSender.send(mail);
            }));
        }
        while (!mimeMessageQueue.isEmpty()) {
            // because it takes forever just to send a simple email
            MimeMessage mail = mimeMessageQueue.remove();
            futures.add(CompletableFuture.runAsync(() -> {
                mailSender.send(mail);
            }));
        }
        futures.forEach(CompletableFuture::join);
    }
}
