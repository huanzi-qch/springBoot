package cn.huanzi.qch.springbootuniappmui.muiwrapper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/muiwrapper/")
public class MuiWrapperController {

    @GetMapping("dialog")
    public ModelAndView index(){
        return new ModelAndView("muiwrapper/dialog");
    }

}
