package cn.xuqplus.adminlte.controller.login;

import cn.xuqplus.adminlte.context.event.user.UserResetEvent;
import cn.xuqplus.adminlte.context.exception.InvalidRequestException;
import cn.xuqplus.adminlte.domain.user.User;
import cn.xuqplus.adminlte.domain.user.UserRegister;
import cn.xuqplus.adminlte.domain.user.UserReset;
import cn.xuqplus.adminlte.repository.user.UserRegisterRepository;
import cn.xuqplus.adminlte.repository.user.UserRepository;
import cn.xuqplus.adminlte.repository.user.UserResetRepository;
import cn.xuqplus.adminlte.util.MessageDigestUtil;
import cn.xuqplus.adminlte.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

@Controller
public class ResetController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserResetRepository userResetRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Value("${context.controller.login.passwordSaltLength}")
    int passwordSaltLength;


    @PostMapping("/public/reset")
    @ResponseBody
    public String reset(String name, String email, HttpServletRequest request) throws InvalidRequestException, NoSuchAlgorithmException {
        User user = name.contains("@") ? userRepository.getByEmail(name) : userRepository.getByName(name);
        String code = RandomUtil.getString(passwordSaltLength * 2);
        String url = String.format("%s://%s:%s/public/reset/verify/", request.getScheme(), request.getServerName(), request.getServerPort());
        UserReset userReset = new UserReset();
        userReset.setName(user.getName());
        userReset.setEmail(user.getEmail());
        userReset.setCode(code);
        userReset.setUrl(url);
        userReset.setCount(0);
        userReset.setExpiresAt(System.currentTimeMillis() + 1000L * 60 * 20);
        userResetRepository.save(userReset);
        eventPublisher.publishEvent(new UserResetEvent(userReset));
        return "succeed";
    }

    @GetMapping("/public/reset/verify")
    public ModelAndView resetVerify(Long id, String code, ModelAndView mav) throws InvalidRequestException, NoSuchAlgorithmException {
        mav.setViewName(String.format("redirect:/public/resetPassword.html?id=%s&code=%s", id, code));
        return mav;
    }

    @PostMapping("/public/reset/verify")
    @ResponseBody
    public String resetVerify(Long id, String code, String password, ModelAndView mav) throws InvalidRequestException, NoSuchAlgorithmException {
        UserReset userReset = userResetRepository.getOne(id);
        if (null == userReset) {
            throw new InvalidRequestException("verify id error");
        }
        if (System.currentTimeMillis() > userReset.getExpiresAt()) {
            throw new InvalidRequestException("verify expired error");
        }
        userReset.setCount(userReset.getCount() + 1);
        userResetRepository.save(userReset);
        if (userReset.getCount() > 10) {
            throw new InvalidRequestException("verify count error");
        }
        if (code.equals(userReset.getCode())) {
            String salt0 = RandomUtil.getString(passwordSaltLength);
            String salt1 = RandomUtil.getString(passwordSaltLength);
            password = MessageDigestUtil.md5(MessageDigestUtil.md5(password + salt0).toUpperCase() + salt1) + "@" + salt0 + "@" + salt1;
            User user = userRepository.getByName(userReset.getName());
            user.setPassword(password);
            userRepository.save(user);
            userResetRepository.delete(userReset);
        } else {
            throw new InvalidRequestException("verify error");
        }
        return "succeed";
    }
}
