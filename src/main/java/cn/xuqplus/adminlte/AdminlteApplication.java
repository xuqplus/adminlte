package cn.xuqplus.adminlte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AdminlteApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminlteApplication.class, args);
    }
}
