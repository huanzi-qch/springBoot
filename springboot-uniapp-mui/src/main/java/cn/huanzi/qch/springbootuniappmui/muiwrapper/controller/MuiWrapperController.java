package cn.huanzi.qch.springbootuniappmui.muiwrapper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/muiwrapper/")
public class MuiWrapperController {
    /*
        底部按钮切换模式，真正项目应用中，可以从数据库动态读取
     */
    @Value("${huanzi.buttom.switch.mode}")
    private String switchMode;

    /*
        底部按钮配置，真正项目应用中，可以从数据库动态读取
        [{
            "text": "mui原生",
            "icon": "mui-icon-phone",
            "url": "/muiwrapper/muiDialog"
        }, {
            "text": "自定义封装",
            "icon": "mui-icon-email",
            "url": "/muiwrapper/dialog"
        }, {
            "text": "头尾操作",
            "icon": "mui-icon-chatbubble",
            "url": "/muiwrapper/test3"
        }, {
            "text": "页面4",
            "icon": "mui-icon-weixin",
            "url": "/muiwrapper/test4"
        }]
     */
    @Value("${huanzi.buttom.list}")
    private String buttomList;

    //跳转主页面
    @GetMapping("main")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView("muiwrapper/main");
        modelAndView.addObject("switchMode",switchMode);
        ArrayList arrayList = new ArrayList<>();
        try {
            //从配置文件读取，有编码问题，我这里先不管了，直接赋值
            buttomList = "[{\"text\":\"mui原生\",\"icon\":\"mui-icon-phone\",\"url\":\"/muiwrapper/muiDialog\"},{\"text\":\"自定义封装\",\"icon\":\"mui-icon-email\",\"url\":\"/muiwrapper/dialog\"},{\"text\":\"头尾操作\",\"icon\":\"mui-icon-chatbubble\",\"url\":\"/muiwrapper/test3\"},{\"text\":\"页面切换\",\"icon\":\"mui-icon-weixin\",\"url\":\"/muiwrapper/test4\"}]";
            arrayList = new ObjectMapper().readValue(buttomList, ArrayList.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        modelAndView.addObject("buttomList", arrayList);
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
