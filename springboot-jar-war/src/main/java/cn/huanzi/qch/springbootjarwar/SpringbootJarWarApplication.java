package cn.huanzi.qch.springbootjarwar;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//jar包
@SpringBootApplication
public class SpringbootJarWarApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJarWarApplication.class, args);
    }

}

//war包
//@SpringBootApplication
//public class SpringbootJarWarApplication  extends SpringBootServletInitializer implements WebApplicationInitializer {
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(SpringbootJarWarApplication.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(SpringbootJarWarApplication.class, args);
//    }
//}


/**
 * 测试controller
 */
@RestController
class IndexController{
    /**
     * 启动成功
     */
    @Bean
    public ApplicationRunner applicationRunner() {
        return applicationArguments -> {
            System.out.println("启动成功！");
        };
    }

    @GetMapping("/")
    String index(){
        return "欢迎访问 springboot-jar-war";
    }
}

