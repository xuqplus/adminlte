package cn.xuqplus.adminlte.context.interceptor.attack;

import cn.xuqplus.adminlte.context.handler.InvalidRequestExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static cn.xuqplus.adminlte.context.handler.InvalidRequestExceptionHandler.getRemoveID;

@Component
public class InvalidRequestInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidRequestInterceptor.class);

    @Resource
    ServletContext servletContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        /**
         * ip层面拦截
         */
        String k = getRemoveID(request);
        Object o = servletContext.getAttribute(k);
        if (null == o) return true;
        Map invalid = (Map) o;
        Integer invalidRequest = (Integer) invalid.get("invalidRequest");
        Long lastInvalidRequest = (Long) invalid.get("lastInvalidRequest");
        if (invalidRequest > 20) return false;
        if (System.currentTimeMillis() - lastInvalidRequest < 1000L * 60) return false;
        return true;
    }
}
