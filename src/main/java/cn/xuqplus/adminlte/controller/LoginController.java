package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.context.event.UserRegisterEvent;
import cn.xuqplus.adminlte.context.exception.InvalidRequestException;
import cn.xuqplus.adminlte.context.exception.WrongPasswordException;
import cn.xuqplus.adminlte.domain.User;
import cn.xuqplus.adminlte.domain.UserRegister;
import cn.xuqplus.adminlte.repository.UserRegisterRepository;
import cn.xuqplus.adminlte.repository.UserRepository;
import cn.xuqplus.adminlte.util.MessageDigestUtil;
import cn.xuqplus.adminlte.util.RandomUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

@Controller
public class LoginController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRegisterRepository userRegisterRepository;

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

    @PostMapping("/public/register")
    @ResponseBody
    public String register(String name, String email, String password, HttpServletRequest request) throws InvalidRequestException, NoSuchAlgorithmException {
        if (userRepository.existsByName(name)) {
            return "exists";
        }
        /**
         * password=md5(md5(password+salt0)+salt1)@salt0@salt1
         */
        String salt0 = RandomUtil.getString(passwordSaltLength);
        String salt1 = RandomUtil.getString(passwordSaltLength);
        String verifyCode = RandomUtil.getString(passwordSaltLength * 2);
        password = MessageDigestUtil.md5(MessageDigestUtil.md5(password + salt0).toUpperCase() + salt1) + "@" + salt0 + "@" + salt1;
        UserRegister userRegister = new UserRegister();
        userRegister.setName(name);
        userRegister.setEmail(email);
        userRegister.setPassword(password);
        userRegister.setExpiresAt(System.currentTimeMillis() + 1000L * 60 * 20);
        userRegister.setVerifyCode(verifyCode);
        userRegister.setVerifyCount(0);
        userRegister.setVerifyUrl(String.format("%s://%s:%s/public/verify/", request.getScheme(), request.getServerName(), request.getServerPort()));
        userRegisterRepository.save(userRegister);
        eventPublisher.publishEvent(new UserRegisterEvent(userRegister));
        return "succeed";
    }

    @GetMapping("/public/verify")
    @ResponseBody
    public String verify(Long id, String verifyCode) throws InvalidRequestException {
        if (!userRegisterRepository.existsById(id)) {
            throw new InvalidRequestException("verify id error");
        }
        UserRegister userRegister = userRegisterRepository.getOne(id);
        if (System.currentTimeMillis() > userRegister.getExpiresAt()) {
            throw new InvalidRequestException("verify expired error");
        }
        userRegister.setVerifyCount(userRegister.getVerifyCount() + 1);
        userRegisterRepository.save(userRegister);
        if (userRegister.getVerifyCount() > 10) {
            throw new InvalidRequestException("verify count error");
        }
        if (verifyCode.equals(userRegister.getVerifyCode())) {
            User user = new User(userRegister);
            userRepository.save(user);
            userRegisterRepository.delete(userRegister);
            return "succeed";
        }
        throw new InvalidRequestException("verify failed error");
    }
}
