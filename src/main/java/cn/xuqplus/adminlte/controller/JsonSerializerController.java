package cn.xuqplus.adminlte.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("json")
public class JsonSerializerController {

    @Autowired
    ApplicationContext context;

    @GetMapping("a")
    public Vo a() {
        return new Vo(new A());
    }

    @GetMapping("b")
    public Vo b() {
        return new Vo(new A(0L, "", new ArrayList<>(), new HashSet<>(), new HashMap<>()));
    }

    @Data
    public static class Vo {
        private String code;
        private String message;
        private Object data;

        public Vo(A a) {
            this.code = "200";
            this.message = "OK";
            this.data = a;
        }
    }

    @Data
    public static class A {
        private Long a;
        private String b;
        private List c;
        private ArrayList d;
        private Set e;
        private HashSet f;
        private Map g;
        private HashMap h;

        public A() {
        }

        public A(Long a, String b, ArrayList<Object> c, HashSet<Object> d, HashMap<Object, Object> e) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = c;
            this.e = d;
            this.f = d;
            this.g = e;
            this.h = e;
        }
    }
}
