package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.service.NginxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NginxController {

    @Autowired
    NginxService nginxService;

    @GetMapping("catConf")
    public String catConf() {
        return nginxService.catConf();
    }

    @GetMapping("createConf")
    public String createConf(String name, String ip, Integer port) {
        return nginxService.createConf(name, ip, port.toString());
    }

    @GetMapping("reload")
    public String reload() {
        return nginxService.reload();
    }
}
