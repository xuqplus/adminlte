package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.domain.query.A;
import cn.xuqplus.adminlte.domain.query.B;
import cn.xuqplus.adminlte.domain.query.C;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("结构化查询")
@RestController
@RequestMapping("qq")
public class QueryController extends BaseController {

    @PostMapping("")
    public Object exec(@RequestBody String query) {
        context.getBean("jpaContext");
        context.getBean("jpaMappingContext");
        return query;
    }

    @PostMapping("a")
    public Object o(@RequestBody A o) {
        aRepository.save(o);
        return o;
    }

    @PostMapping("b")
    public Object o(@RequestBody B o) {
        bRepository.save(o);
        return o;
    }

    @PostMapping("c")
    public Object o(@RequestBody C o) {
        cRepository.save(o);
        return o;
    }
}
