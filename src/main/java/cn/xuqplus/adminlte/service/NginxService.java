package cn.xuqplus.adminlte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NginxService {
    private static final String PREFIX = "docker/nginx/";

    @Value("${adminlte.docker.nginx}")
    String nginx;

    @Autowired
    ShellService shellService;

    public String catConf() {
        return this.exec("cat-conf.sh", nginx);
    }

    public String createConf(String name, String ip, String port) {
        return this.exec("echo-x-conf.sh", nginx, name, ip, port);
    }

    public String reload() {
        return this.exec("reload.sh", nginx);
    }

    private String exec(String... script) {
        script[0] = PREFIX + script[0];
        return shellService.exec(script);
    }
}
