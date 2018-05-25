package cn.xuqplus.adminlte.controller.login;

import cn.xuqplus.adminlte.context.event.user.UserRegisterEvent;
import cn.xuqplus.adminlte.context.exception.InvalidRequestException;
import cn.xuqplus.adminlte.domain.user.User;
import cn.xuqplus.adminlte.domain.user.UserRegister;
import cn.xuqplus.adminlte.repository.user.UserRegisterRepository;
import cn.xuqplus.adminlte.repository.user.UserRepository;
import cn.xuqplus.adminlte.util.MessageDigestUtil;
import cn.xuqplus.adminlte.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

@Controller
public class RegisterController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRegisterRepository userRegisterRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Value("${context.controller.login.passwordSaltLength}")
    int passwordSaltLength;

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
        userRegister.setCode(verifyCode);
        userRegister.setCount(0);
        userRegister.setUrl(String.format("%s://%s:%s/public/register/verify/", request.getScheme(), request.getServerName(), request.getServerPort()));
        userRegisterRepository.save(userRegister);
        eventPublisher.publishEvent(new UserRegisterEvent(userRegister));
        return "succeed";
    }

    @GetMapping("/public/register/verify")
    @ResponseBody
    public String verify(Long id, String code) throws InvalidRequestException {
        if (!userRegisterRepository.existsById(id)) {
            throw new InvalidRequestException("verify id error");
        }
        UserRegister userRegister = userRegisterRepository.getOne(id);
        if (System.currentTimeMillis() > userRegister.getExpiresAt()) {
            throw new InvalidRequestException("verify expired error");
        }
        userRegister.setCount(userRegister.getCount() + 1);
        userRegisterRepository.save(userRegister);
        if (userRegister.getCount() > 10) {
            throw new InvalidRequestException("verify count error");
        }
        if (code.equals(userRegister.getCode())) {
            User user = new User(userRegister);
            userRepository.save(user);
            userRegisterRepository.delete(userRegister);
            return "succeed";
        }
        throw new InvalidRequestException("verify failed error");
    }
}
