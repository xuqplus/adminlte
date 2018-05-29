package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.service.ShellService;
import cn.xuqplus.adminlte.util.MailMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
public class ShellController {

    @Autowired
    ShellService shellService;

    @GetMapping("shell")
    public String shell(String script) throws IOException {
        shellService.exec(script);
        return "succeed";
    }
}