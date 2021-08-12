package cn.huanzi.qch.springbootadminclient;

import cn.huanzi.qch.springbootadminclient.async.TestAsync;
import cn.huanzi.qch.springbootadminclient.caches.TestCaches;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling//启用定时器
@EnableAsync//开启异步调用
@EnableCaching//开启缓存
@SpringBootApplication
public class SpringbootAdminClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootAdminClientApplication.class, args);
    }

    @Autowired
    private TestCaches testCaches;
    @Autowired
    private TestAsync testAsync;

    /**
     * 启动成功
     */
    @Bean
    public ApplicationRunner applicationRunner() {
        return applicationArguments -> {
            log.info("启动成功！");

            //使用缓存
            testCaches.list();
            //异步调用
            testAsync.asyncTask();
        };
    }
}
