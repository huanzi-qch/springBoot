package cn.huanzi.qch.springbootuniappmui.muiwrapper.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/muiwrapper/")
public class MuiWrapperController {
    @Value("${huanzi.buttom.switch.mode}")
    private String switchMode;

    //跳转主页面
    @GetMapping("main")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView("muiwrapper/main");
        modelAndView.addObject("switchMode",switchMode);
        return modelAndView;
    }

    @GetMapping("dialog")
    public ModelAndView dialog(){
        return new ModelAndView("muiwrapper/dialog");
    }

    @GetMapping("muiDialog")
    public ModelAndView muiDialog(){
        return new ModelAndView("muiwrapper/muiDialog");
    }

    @GetMapping("test3")
    public ModelAndView test3(){
        return new ModelAndView("muiwrapper/test3");
    }

    @GetMapping("test4")
    public ModelAndView test4(){
        return new ModelAndView("muiwrapper/test4");
    }
}
