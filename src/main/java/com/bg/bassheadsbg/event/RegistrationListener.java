package com.bg.bassheadsbg.event;

import com.bg.bassheadsbg.model.entity.users.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final MessageSource messages;
    private final JavaMailSender mailSender;
    @Value("${myserver.address}")
    private String serverIp;
    @Value("${myserver.port}")
    private String serverPort;

    public RegistrationListener(MessageSource messages, JavaMailSender mailSender) {
        this.messages = messages;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        UserEntity user = event.getUser();
        String token = event.getToken();

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = "http://" + serverIp + ":" + serverPort + "/users/registrationConfirm?token=" + token;
        String message = messages.getMessage("message_regSuccess", null, event.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + confirmationUrl);
        mailSender.send(email);
    }
}
