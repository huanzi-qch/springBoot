package cn.huanzi.qch.springbootfilter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/index")
    public String index(){
        return "index首页";
    }

    @GetMapping("/test")
    public String test(){
        return "test路径测试";
    }
}
