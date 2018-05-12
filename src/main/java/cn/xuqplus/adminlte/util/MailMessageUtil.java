package cn.xuqplus.adminlte.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailMessageUtil {

    public static SimpleMailMessage createMailMessage(JavaMailSenderImpl mailSender, String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailSender.getUsername());
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        return mailMessage;
    }
}
