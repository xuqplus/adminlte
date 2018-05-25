package cn.xuqplus.adminlte.context.listener.user;

import cn.xuqplus.adminlte.context.event.user.UserResetEvent;
import cn.xuqplus.adminlte.domain.user.UserReset;
import cn.xuqplus.adminlte.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserResetEventListener implements ApplicationListener<UserResetEvent> {

    @Autowired
    MailService mailService;

    @Override
    @Async
    public void onApplicationEvent(UserResetEvent event) {
        UserReset userReset = (UserReset) event.getSource();
        long min = (userReset.getExpiresAt() - System.currentTimeMillis()) / 1000L / 60;
        String url = userReset.getUrl();
        String subject = "xuqplus.space 密码重置";
        String content = String.format("点击链接重置密码, %s分钟内有效 %s?id=%s&code=%s", min, url, userReset.getId(), userReset.getCode());
        mailService.send(userReset.getEmail(), subject, content);
    }
}
