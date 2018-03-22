package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.util.PathUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("env")
public class EnvController {
    @GetMapping("env")
    public ResponseEntity env() {
        Properties properties = System.getProperties();
        return new ResponseEntity(properties, HttpStatus.OK);
    }

    @GetMapping("web")
    public ResponseEntity web(HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        Enumeration<String> names = context.getAttributeNames();
        Map<String, String> map = new HashMap();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            map.put(name, context.getAttribute(name).toString());
        }
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("this")
    public ResponseEntity this0() {
        return new ResponseEntity(this.getClass().getClassLoader().getResource(""), HttpStatus.OK);
    }

    @GetMapping("object")
    public ResponseEntity object0() {
        return new ResponseEntity(Object.class.getClassLoader().getResource(""), HttpStatus.OK);
    }

    @GetMapping("path")
    public ResponseEntity path() {
        return new ResponseEntity(PathUtil.path, HttpStatus.OK);
    }
}
