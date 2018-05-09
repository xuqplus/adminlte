package cn.xuqplus.adminlte.context.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class AbstractExceptionHandler {

    ResponseEntity returnable(HttpServletRequest request, HttpServletResponse response, Exception e) {
        String body = String.format(
                "{\"path\":\"%s\",\"message\":\"%s\",\"timestamp\":\"%s\"}", request.getRequestURI(), e.getMessage(), new Date());
        return new ResponseEntity(body, HttpStatus.valueOf(response.getStatus()));
    }
}
