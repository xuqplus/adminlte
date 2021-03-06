package cn.xuqplus.adminlte.context.handler;

import cn.xuqplus.adminlte.context.exception.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class InvalidRequestExceptionHandler extends AbstractExceptionHandler {

    @Resource
    ServletContext servletContext;

    @ExceptionHandler(value = InvalidRequestException.class)
    public ResponseEntity handle(HttpServletRequest request, HttpServletResponse response, InvalidRequestException e) {
        String k = getRemoveID(request);
        Object o = servletContext.getAttribute(k);
        if (null == o) {
            Map map = new HashMap(4);
            map.put("invalidRequest", 1);
            map.put("lastInvalidRequest", System.currentTimeMillis());
            servletContext.setAttribute(k, map);
        } else {
            Map map = (Map) o;
            map.put("invalidRequest", (Integer) map.get("invalidRequest") + 1);
            map.put("lastInvalidRequest", System.currentTimeMillis());
            servletContext.setAttribute(k, map);
        }
        return returnable(request, response, e);
    }

    @Override
    ResponseEntity returnable(HttpServletRequest request, HttpServletResponse response, Exception e) {
        String body = String.format(
                "{\"path\":\"%s\",\"message\":\"%s\",\"timestamp\":\"%s\"}",
                request.getRequestURI(), e.getMessage(), new Date());
        return new ResponseEntity(body, HttpStatus.valueOf(response.getStatus()));
    }

    public static String getRemoveID(HttpServletRequest request) {
        return String.format("%s-%s-%s", InvalidRequestExceptionHandler.class.getName(), request.getRemoteAddr(), request.getHeader("X-Forwarded-For"));
    }
}
