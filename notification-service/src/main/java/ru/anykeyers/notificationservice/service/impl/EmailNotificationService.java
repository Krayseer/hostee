package ru.anykeyers.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.anykeyers.notificationservice.service.NotificationService;
import ru.krayseer.domain.UserDTO;

/**
 * Реализация отправки уведомлений по Email
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationService implements NotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void notify(UserDTO user, Object message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(sender);
        msg.setTo(user.getEmail());
        msg.setText((String) message);
        try {
            mailSender.send(msg);
            log.info("Send email message to <{}> with content: {}", user.getEmail(), message);
        } catch (
                MailAuthenticationException mailAuthenticationException) {
            log.error("Failed to send message to <{}>", user.getEmail());
        }
    }

}
