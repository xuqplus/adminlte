package cn.xuqplus.adminlte.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSenderImpl mailSender() {

        /**
         * use tls
         */
        Properties properties = new Properties();
        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(465));
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setPort(465);
        mailSender.setHost("smtp.163.com");
        mailSender.setDefaultEncoding("utf-8");
        mailSender.setUsername("xuqplus@163.com");
        mailSender.setPassword("qq1234");
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}
