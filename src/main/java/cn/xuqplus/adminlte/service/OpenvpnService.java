package cn.xuqplus.adminlte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OpenvpnService {
    private static final String PREFIX = "docker/nginx/";

    @Value("${adminlte.docker.openvpn}")
    String container;

    @Autowired
    ShellService shell;

    public String catConf() throws IOException {
        String r;
        r = dockerExec("cat /etc/openvpn/server.conf");
        return r;
    }

    private String dockerExec(String script) {
        return shell.exec("bash", "-c", String.format("docker exec -i %s %s", container, script));
    }
}
