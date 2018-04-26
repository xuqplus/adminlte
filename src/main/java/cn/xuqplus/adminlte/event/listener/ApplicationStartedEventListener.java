package cn.xuqplus.adminlte.event.listener;

import org.flywaydb.core.Flyway;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationStartedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    String url = "jdbc:mysql://adminlte:3306/";
    String username = "test";
    String password = "123456";
    String[] schemas = {"adminlte"};
    String[] initSqls = {"create database if not exists adminlte default charset utf8"};

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(url, username, password, initSqls);
        flyway.setSchemas(schemas);
        flyway.migrate();
    }
}
