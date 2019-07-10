package cn.huanzi.qch.springbootloadmyprofiles;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//手动加载自定义配置文件
//@PropertySource(value = {
//        "classpath:xxx.properties",
//        "classpath:yyy.properties",
//        "classpath:zzz.yml",
//}, encoding = "utf-8")

@Component
@SpringBootApplication
public class SpringbootLoadmyprofilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLoadmyprofilesApplication.class, args);
    }

    @Value("${cn.huanzi.qch.xxx}")
    private String xxx;

    @Value("${cn.huanzi.qch.yyy}")
    private String yyy;

    @Value("${cn.huanzi.qch.zzz}")
    private String zzz;

    @Bean
    void index(){
        System.out.println(xxx);
        System.out.println(yyy);
        System.out.println(zzz);
    }
}
