package cn.xuqplus.adminlte.controller.login;

import cn.xuqplus.adminlte.context.exception.WrongPasswordException;
import cn.xuqplus.adminlte.domain.user.User;
import cn.xuqplus.adminlte.repository.user.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Value("${context.controller.login.passwordSaltLength}")
    int passwordSaltLength;

    @RequestMapping("/logout")
    @ResponseBody
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "succeed";
    }

    @PostMapping("/public/login")
    @ResponseBody
    public String login(String name, String password) throws WrongPasswordException {
        AuthenticationToken token = new UsernamePasswordToken(name, password.substring(0, 32).toUpperCase());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (Exception e) {
            throw new WrongPasswordException("密码错误");
        }
        return "login succeed";
    }

    @GetMapping("/public/salt0")
    @ResponseBody
    public String salt0(String name) {
        User user = name.contains("@") ? userRepository.getByEmail(name) : userRepository.getByName(name);
        String[] passwordAtSalt0AtSalt1 = user.getPassword().split("@");
        return passwordAtSalt0AtSalt1[1];
    }
}
