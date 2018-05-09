package cn.xuqplus.adminlte.context.interceptor.attack;

import cn.xuqplus.adminlte.context.exception.InvalidRequestException;
import cn.xuqplus.adminlte.context.exception.WrongPasswordException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class WrongPasswordInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(WrongPasswordInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws WrongPasswordException, InvalidRequestException {
        Session session = SecurityUtils.getSubject().getSession();

        Integer wrongPassword = (Integer) session.getAttribute("wrongPassword");
        if (null == wrongPassword) return true;
        if (wrongPassword < 10) return true;
        if (20 < wrongPassword) throw new InvalidRequestException("请求过于频繁,请稍候再试");
        if (1000L * 60 < System.currentTimeMillis() - (Long) session.getAttribute("lastWrongPassword")) {
            return true;
        } else {
            throw new WrongPasswordException("密码错误次数过多");
        }
    }
}
