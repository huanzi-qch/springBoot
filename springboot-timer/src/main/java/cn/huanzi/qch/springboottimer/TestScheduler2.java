package cn.huanzi.qch.springboottimer;

import cn.huanzi.qch.springboottimer.task.TbTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 测试定时器2-动态定时器
 */
@Slf4j
@Component
public class TestScheduler2 {

    //数据库的任务
    public static ConcurrentHashMap<String, TbTask> tasks = new ConcurrentHashMap<>(10);

    //正在运行的任务
    public static ConcurrentHashMap<String,ScheduledFuture> runTasks = new ConcurrentHashMap<>(10);

    //线程池任务调度
    private ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

    @Autowired
    private TbTaskRepository tbTaskRepository;

    /**
     * 初始化线程池任务调度
     */
    @Autowired
    public TestScheduler2(){
        this.threadPoolTaskScheduler.setPoolSize(10);
        this.threadPoolTaskScheduler.setThreadNamePrefix("task-thread-");
        this.threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        this.threadPoolTaskScheduler.initialize();
    }

    /**
     * 获取所有数据库里的定时任务
     */
    private void getAllTbTask(){
        //查询所有，并put到tasks
        TestScheduler2.tasks.clear();
        List<TbTask> list = tbTaskRepository.findAll();
        list.forEach((task)-> TestScheduler2.tasks.put(task.getTaskId(),task));
    }

    /**
     * 根据定时任务id，启动定时任务
     */
    void start(String taskId){
        try {
            //如果为空，重新获取
            if(TestScheduler2.tasks.size() <= 0){
                this.getAllTbTask();
            }
            TbTask tbTask = TestScheduler2.tasks.get(taskId);

            //获取并实例化Runnable任务类
            Class<?> clazz = Class.forName(tbTask.getTaskClass());
            Runnable runnable = (Runnable)clazz.newInstance();

            //Cron表达式
            CronTrigger cron = new CronTrigger(tbTask.getTaskExp());

            //执行，并put到runTasks
            TestScheduler2.runTasks.put(taskId, Objects.requireNonNull(this.threadPoolTaskScheduler.schedule(runnable, cron)));

            this.updateTaskStatus(taskId,1);

            log.info("{}，任务启动！",taskId);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            log.error("{}，任务启动失败...",taskId);
            e.printStackTrace();
        }

    }

    /**
     * 根据定时任务id，停止定时任务
     */
    void stop(String taskId){
        TestScheduler2.runTasks.get(taskId).cancel(true);

        TestScheduler2.runTasks.remove(taskId);

        this.updateTaskStatus(taskId,0);

        log.info("{}，任务停止...",taskId);
    }

    /**
     * 更新数据库动态定时任务状态
     */
    private void updateTaskStatus(String taskId,int status){
        TbTask task = tbTaskRepository.getOne(taskId);
        task.setTaskStatus(status);
        task.setUpdateTime(new Date());
        tbTaskRepository.save(task);
    }
}
