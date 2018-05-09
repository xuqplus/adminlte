package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.context.exception.InvalidRequestException;
import cn.xuqplus.adminlte.context.exception.WrongPasswordException;
import cn.xuqplus.adminlte.domain.User;
import cn.xuqplus.adminlte.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/public/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "";
    }

    @PostMapping("/public/login")
    @ResponseBody
    public String login(String name, String password) throws WrongPasswordException {
        AuthenticationToken token = new UsernamePasswordToken(name, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (Exception e) {
            throw new WrongPasswordException("密码错误");
        }
        return "login succeed";
    }

    @PostMapping("/public/register")
    @ResponseBody
    public String register(String name, String password) throws InvalidRequestException {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userRepository.save(user);
        return "succeed";
    }
}
