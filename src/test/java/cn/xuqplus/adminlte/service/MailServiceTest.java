package cn.xuqplus.adminlte.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    MailService mailService;

    @Test
    public void send() {
        String to = "445172495@qq.com";
        String subject = "xuqplus.space 注册 - test";
        String content = String.format("点击链接确认注册, %s分钟内有效 %s?id=%s&verifyCode=%s", "min", "url", "id", "code");
        mailService.send(to, subject, content);
    }
}