package com.bg.bassheadsbg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.yaml")
public class MailConfiguration {
    protected static final String SPRING_MAIL_HOST = "spring.mail.host";
    protected static final String SPRING_MAIL_PORT = "spring.mail.port";
    protected static final String SPRING_MAIL_USERNAME = "spring.mail.username";
    protected static final String SPRING_MAIL_PASSWORD = "spring.mail.password";
    protected static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    protected static final String TRUE = "true";
    protected static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    protected static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    protected static final String SMTP = "smtp";
    protected static final String MAIL_DEBUG = "mail.debug";
    protected static final String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
    protected static final String ASTERISK = "*";
    @Autowired
    private Environment environment;
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(environment.getProperty(SPRING_MAIL_HOST));
        javaMailSender.setPort(Integer.valueOf(environment.getProperty(SPRING_MAIL_PORT)));
        javaMailSender.setUsername(environment.getProperty(SPRING_MAIL_USERNAME));
        javaMailSender.setPassword(environment.getProperty(SPRING_MAIL_PASSWORD));

        Properties javaMailProperties = new Properties();
        javaMailProperties.put(MAIL_SMTP_STARTTLS_ENABLE, TRUE);
        javaMailProperties.put(MAIL_SMTP_AUTH, TRUE);
        javaMailProperties.put(MAIL_TRANSPORT_PROTOCOL, SMTP);
        javaMailProperties.put(MAIL_DEBUG, TRUE);
        javaMailProperties.put(MAIL_SMTP_SSL_TRUST, ASTERISK);

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }
}