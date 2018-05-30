package cn.xuqplus.adminlte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NginxService {
    private static final String PREFIX = "docker/nginx/";

    @Value("${adminlte.docker.nginx}")
    String nginx;

    @Autowired
    ShellService shellService;

    public String catConf() throws IOException {
        return this.exec("catConf", nginx);
    }

    public String createConf(String name, String ip, String port) throws IOException {
        return this.exec("createConf", nginx, name, ip, port);
    }

    public String reload() throws IOException {
        return this.exec("reload", nginx);
    }

    private String exec(String... script) throws IOException {
        script[0] = PREFIX + script[0];
        return shellService.execScript(script);
    }
}
