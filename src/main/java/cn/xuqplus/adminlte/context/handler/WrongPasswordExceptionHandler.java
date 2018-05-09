package cn.xuqplus.adminlte.context.handler;

import cn.xuqplus.adminlte.context.exception.WrongPasswordException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@ResponseBody
public class WrongPasswordExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(value = WrongPasswordException.class)
    public ResponseEntity handle(HttpServletRequest request, HttpServletResponse response, WrongPasswordException e) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();

        Integer wrongPassword = (Integer) session.getAttribute("wrongPassword");
        if (null == wrongPassword) {
            session.setAttribute("wrongPassword", 1);
        } else {
            session.setAttribute("wrongPassword", wrongPassword + 1);
        }
        session.setAttribute("lastWrongPassword", System.currentTimeMillis());
        return returnable(request, response, e);
    }
}
