package cn.xuqplus.adminlte.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MainConfig {
    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("utf-8");
        mailSender.setHost("smtp.163.com");
        mailSender.setUsername("xuqplus@163.com");
        mailSender.setPassword("qq1234");
        return mailSender;
    }
}
