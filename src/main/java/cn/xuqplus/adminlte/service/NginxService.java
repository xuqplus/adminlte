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

    public String catConf(String script) {
        return this.exec("cat-conf.sh", nginx);
    }

    public String createConf(String script) {
        return this.exec("echo-x-conf.sh");
    }

    public String reload(String script) {
        return this.exec("reload.sh");
    }

    private String exec(String... script) {
        script[0] = PREFIX + script[0];
        return shellService.exec(script);
    }
}
