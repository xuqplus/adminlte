package cn.xuqplus.adminlte.context.listener;

import cn.xuqplus.adminlte.context.event.UserRegisterEvent;
import cn.xuqplus.adminlte.domain.UserRegister;
import cn.xuqplus.adminlte.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterEventListener implements ApplicationListener<UserRegisterEvent> {

    @Autowired
    MailService mailService;

    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        UserRegister userRegister = (UserRegister) event.getSource();
        long min = (userRegister.getExpiresAt() - System.currentTimeMillis()) / 1000L / 60;
        String url = userRegister.getVerifyUrl();
        String subject = "xuqplus.space 注册";
        String content = String.format("点击链接确认注册, %s分钟内有效 %s?id=%s&verifyCode=%s", min, url, userRegister.getId(), userRegister.getVerifyCode());
        mailService.send(userRegister.getEmail(), subject, content);
    }
}
