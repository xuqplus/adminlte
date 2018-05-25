package cn.xuqplus.adminlte.context.event.user;

import org.springframework.context.ApplicationEvent;

public class UserResetEvent extends ApplicationEvent {

    public UserResetEvent(Object source) {
        super(source);
    }
}
