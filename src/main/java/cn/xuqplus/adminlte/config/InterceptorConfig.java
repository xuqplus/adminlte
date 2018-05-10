package cn.xuqplus.adminlte.config;

import cn.xuqplus.adminlte.context.interceptor.DemoInterceptor;
import cn.xuqplus.adminlte.context.interceptor.NonceInterceptor;
import cn.xuqplus.adminlte.context.interceptor.TimestampInterceptor;
import cn.xuqplus.adminlte.context.interceptor.attack.InvalidRequestInterceptor;
import cn.xuqplus.adminlte.context.interceptor.attack.WrongPasswordInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    DemoInterceptor demoInterceptor;

    @Autowired
    TimestampInterceptor timestampInterceptor;

    @Autowired
    NonceInterceptor nonceInterceptor;

    @Autowired
    InvalidRequestInterceptor invalidRequestInterceptor;

    @Autowired
    WrongPasswordInterceptor wrongPasswordInterceptor;


    @Value("${context.interceptor.replay:false}")
    boolean replay;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(demoInterceptor).addPathPatterns("/**");
        registry.addInterceptor(invalidRequestInterceptor).addPathPatterns("/**");
        registry.addInterceptor(wrongPasswordInterceptor).addPathPatterns("/public/login");
        /**
         * 防重放, 顺序有关
         */
        if (replay) {
            // registry.addInterceptor(timestampInterceptor).addPathPatterns("/public/**");
            // registry.addInterceptor(nonceInterceptor).addPathPatterns("/public/**");
        }
    }
}
