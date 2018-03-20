package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("data")
public class DataController {
    private static List<User> users;

    static {
        users = new ArrayList<>();
        int n = new Random().nextInt(100);
        for (int i = 0; i++ < n; ) {
            User user = new User();
            user.setId(Long.valueOf(i));
            user.setName("name_" + i);
            user.setPassword("password_" + i);
            users.add(user);
        }
    }

    @GetMapping("users")
    public List users(int page, int size) {
        List result = new ArrayList();
        for (int i = page * size; i < users.size(); i++) {
            result.add(users.get(i));
        }
        return result;
    }
}
