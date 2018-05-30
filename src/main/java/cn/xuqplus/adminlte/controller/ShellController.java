package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.service.ShellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShellController {

    @Autowired
    ShellService shellService;

    /**
     * %20=space
     * %7c=|
     */
    @GetMapping("shell")
    public String shell(String script) {
        return shellService.exec(script.split(","));
    }
}
