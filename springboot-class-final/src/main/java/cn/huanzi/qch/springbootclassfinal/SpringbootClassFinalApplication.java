package cn.huanzi.qch.springbootclassfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("/")
@SpringBootApplication
public class SpringbootClassFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootClassFinalApplication.class, args);
        // 使用默认浏览器打开
        try {
            Runtime.getRuntime().exec(String.format("cmd /c start %s", "http://localhost:10094/test"));
        } catch (Exception e) {
            System.out.println("打开失败");
        }
    }

    /**
     * 测试接口
     * @return 字符串
     */
    @GetMapping("test")
    public String test(){
        return "SpringBoot系列——ClassFinal混淆加密";
    }

}
