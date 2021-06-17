package cn.huanzi.qch.springbootadminclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("test1")
    public String test1(){
        return "test1";
    }

    @PostMapping("test2")
    public String test2(){
        return "test2";
    }

    @RequestMapping("test3")
    public String test3(){
        return "test3";
    }
}
