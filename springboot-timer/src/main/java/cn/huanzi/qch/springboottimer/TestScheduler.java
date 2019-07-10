package cn.huanzi.qch.springboottimer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试定时器
 */
@Component
public class TestScheduler {

    @Scheduled(cron="0/30 * * * * ?")
    private void test(){
        //在能被30整除的秒数执行一次
        System.err.println("这句话每30秒打印一次  "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
