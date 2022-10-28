package cn.huanzi.qch.springbootfilesupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
public class SpringbootFilesUpLoadApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootFilesUpLoadApplication.class, args);
    }
}

/**
 * 测试
 */
@RestController
@RequestMapping("/")
class TestController{

    @GetMapping("test")
    public ModelAndView index(){
        return new ModelAndView("test.html");
    }

    @GetMapping("test2")
    public ModelAndView index2(){
        return new ModelAndView("test2.html");
    }
}