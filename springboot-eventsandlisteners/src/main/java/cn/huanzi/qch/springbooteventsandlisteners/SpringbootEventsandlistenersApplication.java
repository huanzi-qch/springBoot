package cn.huanzi.qch.springbooteventsandlisteners;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringbootEventsandlistenersApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEventsandlistenersApplication.class, args);
    }

}
