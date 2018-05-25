package cn.xuqplus.adminlte.context.schedule.interceptor;

import cn.xuqplus.adminlte.context.handler.InvalidRequestExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;

@Component
public class InterceptorCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterceptorCleaner.class);

    @Resource
    ServletContext servletContext;

    @Scheduled(initialDelay = 1000L * 60, fixedDelay = 1000L * 60 * 10)
    public void invalidRequest() {
        final String k = InvalidRequestExceptionHandler.class.getName();

        Enumeration<String> attributeNames = servletContext.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            if (name.startsWith(k)) {
                Map map = (Map) servletContext.getAttribute(name);
                if (System.currentTimeMillis() - (Long) map.get("lastInvalidRequest") > 1000L * 60 * 10) {
                    LOGGER.info(String.format("清除ip=%s", k.replace(InvalidRequestExceptionHandler.class.getName(), "")));
                    servletContext.removeAttribute(name);
                }
            }
        }
    }

    @Scheduled(fixedDelay = 1000L * 60 * 2)
    public void print() {
        final String k = InvalidRequestExceptionHandler.class.getName();

        Enumeration<String> attributeNames = servletContext.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            if (name.startsWith(k))
                LOGGER.info(String.format("name=%s, value=%s", name, servletContext.getAttribute(name)));
        }
    }
}