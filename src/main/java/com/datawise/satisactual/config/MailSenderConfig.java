package com.datawise.satisactual.config;

import com.datawise.satisactual.model.ApplicationConfigDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Import(ApplicationConfig.class)
public class MailSenderConfig {

    @Autowired
    private ApplicationConfigDetails details;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(details.getAlertsEMailClient());
        mailSender.setUsername(details.getAlertsEMailID());
        mailSender.setPassword(details.getAlertsEMailPWD());
        mailSender.setPort(587);
        mailSender.setProtocol("smtp");

        Properties properties = new Properties();
        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.ssl.trust", "*");
        properties.setProperty("mail.smtp.EnableSSL.enable", details.getAlertsEMailSSL());
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "true");
        properties.setProperty("mail.smtp.port", "25");
        properties.setProperty("mail.smtp.socketFactory.port", "25");

        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

}
