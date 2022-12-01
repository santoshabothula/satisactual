package com.datawise.satisactual.utils;

import com.datawise.satisactual.exception.SatisActualProcessException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

@Component
public class MailSenderUtil {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration freemarkerConfig;

    public void sendMail(String templateName, String mailTo, String subject, Map<String, Object> props) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/mail-templates/");
        try {
            Template template = freemarkerConfig.getTemplate(templateName);
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, props);
            helper.setTo(mailTo);
            helper.setText(text, true);
            helper.setSubject(subject);
            mailSender.send(message);
        } catch (IOException | MessagingException | TemplateException e) {
            throw new SatisActualProcessException("Failed to send mail subject " + subject + " mailTo " + mailTo + " , Error: " + e.getMessage());
        }
    }
}
