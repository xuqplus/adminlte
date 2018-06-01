package cn.xuqplus.adminlte.service;

import cn.xuqplus.adminlte.AdminlteApplicationTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.Assert.*;

public class ShellServiceTest extends AdminlteApplicationTests {

    @Autowired
    ShellService shellService;

    @Test
    public void exec() {
        String r = shellService.exec("echo", "aaaa");
        Assert.assertTrue(r.contains("exit 1") || r.contains("exit 0"));
        r = shellService.exec("echo", "bbbb");
        Assert.assertTrue(r.contains("exit 1") || r.contains("exit 0"));
        r = shellService.exec("bash", "-c", "echo aaaa");
        Assert.assertTrue(r.contains("exit 1") || r.contains("exit 0"));
    }

    @Test
    public void execScript() throws IOException {
        String r = shellService.execScript("docker/nginx/catConf", "container", "serverName", "serverIp", "serverPort");
        Assert.assertTrue(r.contains("exit 1") || r.contains("exit 0"));
    }

    @Test
    public void aa() throws IOException {
        String r = shellService.exec("bash", "-c", "openvpn /etc/openvpn/presk.conf ");
    }
}