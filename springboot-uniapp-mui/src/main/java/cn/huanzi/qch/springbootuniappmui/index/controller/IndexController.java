package cn.huanzi.qch.springbootuniappmui.index.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/index/")
public class IndexController {

    @GetMapping("index")
    public ModelAndView index(){
        return new ModelAndView("index/index");
    }

    @GetMapping("getData")
    public String getData(){
        return "获取数据接口";
    }
}
