package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.domain.User;
import cn.xuqplus.adminlte.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("findAll")
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
