package main.com.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "测试接口")
@RestController
public class Test {
    final Logger log = Logger.getLogger(Test.class);
    @ApiOperation(value = "测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, paramType = "query")
    })
    @RequestMapping("/hello")
    public String hello(@RequestParam Long id, @RequestParam String name) {
        log.error("进入Hello路由");
        return "nothing here";
    }
}
