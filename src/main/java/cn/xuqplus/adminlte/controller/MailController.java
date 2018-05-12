package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.util.MailMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mail")
public class MailController {

    @Autowired
    JavaMailSenderImpl mailSender;

    @GetMapping("send")
    public String send(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = MailMessageUtil.createMailMessage(mailSender, to, subject, content);
        mailSender.send(simpleMailMessage);
        return "succeed";
    }
}
