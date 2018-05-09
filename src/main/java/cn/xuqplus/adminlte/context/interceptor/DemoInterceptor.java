package cn.xuqplus.adminlte.context.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class DemoInterceptor implements HandlerInterceptor {
    static int i = 0;
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.info("pre");
        Enumeration<String> names = request.getParameterNames();
        Map<String, String> params = new HashMap();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            params.put(name, request.getParameter(name));
        }
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        LOGGER.info(String.format("%s %s %s", method, url, params));

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("demo".equals(cookie.getName())) {
                cookie.setValue("value-" + i++);
                response.addCookie(cookie);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.info("post");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGGER.info("after");
    }
}
