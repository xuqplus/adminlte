package cn.xuqplus.adminlte.service;

import cn.xuqplus.adminlte.AdminlteApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ShellServiceTest extends AdminlteApplicationTests {

    @Autowired
    ShellService shellService;

    @Test
    public void exec() {
        shellService.exec("test.sh");
    }
}