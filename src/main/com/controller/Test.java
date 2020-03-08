package main.com.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Test {
    final Logger log = Logger.getLogger(Test.class);

    @RequestMapping("/hello")
    public String hello() {
        log.error("进入Hello路由");
        return "nothing here";
    }
}
