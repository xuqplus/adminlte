package cn.xuqplus.adminlte.context.listener.user;

import cn.xuqplus.adminlte.context.event.user.UserRegisterEvent;
import cn.xuqplus.adminlte.domain.user.UserRegister;
import cn.xuqplus.adminlte.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterEventListener implements ApplicationListener<UserRegisterEvent> {

    @Autowired
    MailService mailService;

    @Override
    @Async
    public void onApplicationEvent(UserRegisterEvent event) {
        UserRegister userRegister = (UserRegister) event.getSource();
        long min = (userRegister.getExpiresAt() - System.currentTimeMillis()) / 1000L / 60;
        String url = userRegister.getUrl();
        String subject = "xuqplus.space 注册";
        String content = String.format("点击链接确认注册, %s分钟内有效 %s?id=%s&code=%s", min, url, userRegister.getId(), userRegister.getCode());
        mailService.send(userRegister.getEmail(), subject, content);
    }
}
