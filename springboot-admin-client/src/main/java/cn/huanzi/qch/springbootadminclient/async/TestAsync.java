package cn.huanzi.qch.springbootadminclient.async;

import org.springframework.scheduling.annotation.Async;

import org.springframework.stereotype.Component;

@Component
public class TestAsync {
    @Async
    public void asyncTask() {
        long startTime = System.currentTimeMillis();
        try {
            //模拟耗时
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "：void asyncTask()，耗时：" + (endTime - startTime));
    }
}
