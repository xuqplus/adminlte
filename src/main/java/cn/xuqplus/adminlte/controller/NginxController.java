package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.service.NginxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("nginx")
public class NginxController {

    @Autowired
    NginxService nginxService;

    @GetMapping("catConf")
    public String catConf() throws IOException {
        return nginxService.catConf();
    }

    @GetMapping("initConf")
    public String initConf() throws IOException {
        return nginxService.catConf();
    }

    @GetMapping("createConf")
    public String createConf(String name, String ip, Integer port) throws IOException {
        return nginxService.createConf(name, ip, port.toString());
    }

    @GetMapping("reload")
    public String reload() throws IOException {
        return nginxService.reload();
    }
}
