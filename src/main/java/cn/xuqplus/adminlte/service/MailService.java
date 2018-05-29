package cn.xuqplus.adminlte.service;

import cn.xuqplus.adminlte.util.MailMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    JavaMailSenderImpl mailSender;

    public String send(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = MailMessageUtil.createMailMessage(mailSender, to, subject, content);
        mailSender.send(simpleMailMessage);
        return "succeed";
    }
}
