package cn.huanzi.qch.springbootadminclient.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class TestScheduler {

    @Scheduled(cron="0/30 * * * * ?")
    private void test(){
        //在能被30整除的秒数执行一次
        log.error("这句话每30秒打印一次  "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
