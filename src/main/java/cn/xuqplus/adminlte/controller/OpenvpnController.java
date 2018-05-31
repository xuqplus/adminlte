package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.service.OpenvpnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("openvpn")
public class OpenvpnController {

    @Autowired
    OpenvpnService service;

    @GetMapping("catConf")
    public String catConf() throws IOException {
        return service.catConf();
    }
}
