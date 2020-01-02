package cn.huanzi.qch.springboothttps.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpsController {

    @GetMapping("/hello")
    public String hello() {
        return "SpringBoot系列——启用https";
    }

}
