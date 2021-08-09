package cn.huanzi.qch.springboottimer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试定时器-简单的定时器使用
 */
@Slf4j
@Component
public class TestScheduler {

    /**
     * 使用@Scheduled，配置简单定时器
     */
    @Scheduled(cron="0/30 * * * * ?")
    private void test(){
        //在能被30整除的秒数执行一次
        log.error("这句话每30秒打印一次  "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
