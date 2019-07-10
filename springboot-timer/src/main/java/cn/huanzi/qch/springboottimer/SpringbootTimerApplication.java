package cn.huanzi.qch.springboottimer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //启用定时器
@SpringBootApplication
public class SpringbootTimerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTimerApplication.class, args);
    }

}
