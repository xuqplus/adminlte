package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.domain.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("data")
public class DataController {
    private static List<User> users;

    static {
        users = new ArrayList<>();
        int n = new Random().nextInt(100) + 100;
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

    private static List<Map> colors;

    static {
        colors = new ArrayList<>();
        int n = new Random().nextInt(100);
        for (int i = 0; i++ < n; ) {
        }
    }

    @GetMapping("colors")
    public List colors(int page, int size) {
        List result = new ArrayList();
        for (int i = page * size; i < users.size(); i++) {
            result.add(users.get(i));
        }
        return result;
    }

    @GetMapping("addr")
    public String addr(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    @GetMapping("ip")
    public Map ip(HttpServletRequest request) {
        Map map = new HashMap();
        map.put("X-real-ip", request.getHeader("X-real-ip"));
        map.put("X-Forwarded-For", request.getHeader("X-Forwarded-For"));
        map.put("RemoteAddr", request.getRemoteAddr());
        map.put("ContextPath", request.getContextPath());
        map.put("ServerName", request.getServerName());
        map.put("RequestedSessionId", request.getRequestedSessionId());
        return map;
    }

    public static List<User> getUsers() {
        return users;
    }
}
