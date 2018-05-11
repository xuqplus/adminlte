package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.context.exception.InvalidRequestException;
import cn.xuqplus.adminlte.context.exception.WrongPasswordException;
import cn.xuqplus.adminlte.domain.User;
import cn.xuqplus.adminlte.repository.UserRepository;
import cn.xuqplus.adminlte.util.MessageDigestUtil;
import cn.xuqplus.adminlte.util.RandomUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.NoSuchAlgorithmException;

@Controller
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @Value("${context.controller.login.passwordSaltLength}")
    int passwordSaltLength;

    @RequestMapping("/public/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "";
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

    @PostMapping("/public/register")
    @ResponseBody
    public String register(String name, String email, String password) throws InvalidRequestException, NoSuchAlgorithmException {
        /**
         * password=md5(md5(password+salt0)+salt1)@salt0@salt1
         */
        String salt0 = RandomUtil.getString(passwordSaltLength);
        String salt1 = RandomUtil.getString(passwordSaltLength);
        password = MessageDigestUtil.md5(MessageDigestUtil.md5(password + salt0).toUpperCase() + salt1) + "@" + salt0 + "@" + salt1;
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return "succeed";
    }
}
