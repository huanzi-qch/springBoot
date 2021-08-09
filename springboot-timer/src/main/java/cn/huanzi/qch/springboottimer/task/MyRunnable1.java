package cn.huanzi.qch.springboottimer.task;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Runnable任务类1
 */
@Slf4j
public class MyRunnable1 implements Runnable {
    @Override
    public void run() {
        log.info("MyRunnable1  {}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
