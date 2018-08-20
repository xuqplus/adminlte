package cn.xuqplus.adminlte.config;

import cn.xuqplus.adminlte.shiro.AuthorizingRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        /**
         * 登录页面
         */
        factoryBean.setLoginUrl("/public/login2.html");

        /**
         * 过滤规则
         */
        Map definitions = new HashMap();
        definitions.put("/js/**", "anon");
        definitions.put("/css/**", "anon");
        definitions.put("/image/**", "anon");
        definitions.put("/fonts/**", "anon");
        definitions.put("/public/**", "anon");
        definitions.put("/api/**", "anon");
        definitions.put("/**", "anon");
        factoryBean.setFilterChainDefinitionMap(definitions);
        /**
         * 安全管理器
         */
        factoryBean.setSecurityManager(securityManager());
        return factoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        return securityManager;
    }

    @Bean
    public Realm realm() {
        return new AuthorizingRealm();
    }
}
