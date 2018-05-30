package cn.xuqplus.adminlte.service;

import cn.xuqplus.adminlte.AdminlteApplicationTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.Assert.*;

public class NginxServiceTest extends AdminlteApplicationTests {

    @Autowired
    NginxService nginxService;

    @Test
    public void catConf() throws IOException {
        String r = nginxService.catConf();
        Assert.assertTrue(r.contains("exit 1") || r.contains("exit 0"));
    }

    @Test
    public void createConf() throws IOException {
        String r = nginxService.createConf("serverName", "serverIp", "serverPort");
        Assert.assertTrue(r.contains("exit 1"));
    }
}