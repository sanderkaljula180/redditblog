package com.example.sandergit.redditblog.service;

import com.example.sandergit.redditblog.exceptions.SpringRedditException;
import com.example.sandergit.redditblog.dto.NotificationEmailDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentService mailContentService;

    @Async
    void sendMail(NotificationEmailDto notificationEmailDto) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(notificationEmailDto.getRecipient());
            messageHelper.setSubject(notificationEmailDto.getSubject());
            messageHelper.setText(notificationEmailDto.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmailDto.getRecipient());
        }
    }

}
