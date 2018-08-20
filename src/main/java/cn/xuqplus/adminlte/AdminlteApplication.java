package cn.xuqplus.adminlte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableSwagger2
public class AdminlteApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminlteApplication.class, args);
    }
}
