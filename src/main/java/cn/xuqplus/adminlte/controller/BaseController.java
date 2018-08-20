package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.repository.query.ARepository;
import cn.xuqplus.adminlte.repository.query.BRepository;
import cn.xuqplus.adminlte.repository.query.CRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class BaseController {
    @Autowired
    ARepository aRepository;
    @Autowired
    BRepository bRepository;
    @Autowired
    CRepository cRepository;
    @Autowired
    ApplicationContext context;
}
