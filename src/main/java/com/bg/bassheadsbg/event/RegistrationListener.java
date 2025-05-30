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
    private static final String REGISTRATION_CONFIRMATION = "Registration Confirmation";
    private static final String HTTP = "http://";
    private static final String COLON = ":";
    private static final String USERS_REGISTRATION_CONFIRM_TOKEN = "/users/registrationConfirm?token=";
    private static final String MESSAGE_REG_SUCCESS = "message_regSuccess";
    private static final String CRLF = "\r\n";
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
        String subject = REGISTRATION_CONFIRMATION;
        String confirmationUrl = HTTP + serverIp + COLON + serverPort + USERS_REGISTRATION_CONFIRM_TOKEN + token;
        String message = messages.getMessage(MESSAGE_REG_SUCCESS, null, event.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + CRLF + confirmationUrl);
        mailSender.send(email);
    }
}
