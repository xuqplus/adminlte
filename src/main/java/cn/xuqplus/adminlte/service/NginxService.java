package cn.xuqplus.adminlte.service;

import cn.xuqplus.adminlte.util.StringUtil;
import cn.xuqplus.adminlte.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Service
public class NginxService {
    private static final String PREFIX = "docker/nginx/";

    @Value("${adminlte.docker.nginx}")
    String container;

    @Autowired
    ShellService shellService;

    public String catConf() throws IOException {
        return this.exec(ThreadUtil.getCurrentMethodName(), container);
    }

    public String initConf() throws IOException {
        return this.exec(ThreadUtil.getCurrentMethodName(), container);
    }

    public String createConf(String name, String ip, String port) throws IOException {
        return this.exec(ThreadUtil.getCurrentMethodName(), container, name, ip, port);
    }

    public String reload() throws IOException {
        return this.exec(ThreadUtil.getCurrentMethodName(), container);
    }

    private String exec(String... script) throws IOException {
        script[0] = String.format("%s%s", PREFIX, script[0]);
        return shellService.execScript(script);
    }
}
