package cn.huanzi.qch.springboottimer;

import cn.huanzi.qch.springboottimer.task.TbTask;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态定时任务Controller测试
 */
@RestController
@RequestMapping("/tbTask/")
public class TbTaskController {

    @Autowired
    private TestScheduler2 testScheduler2;

    @Autowired
    private TbTaskRepository tbTaskRepository;

    /**
     * 启动一个动态定时任务
     * http://localhost:10085/tbTask/start/2
     */
    @RequestMapping("start/{taskId}")
    public String start(@PathVariable("taskId") String taskId){
        testScheduler2.start(taskId);
        return "操作成功";
    }

    /**
     * 停止一个动态定时任务
     * http://localhost:10085/tbTask/stop/2
     */
    @RequestMapping("stop/{taskId}")
    public String stop(@PathVariable("taskId") String taskId){
        testScheduler2.stop(taskId);
        return "操作成功";
    }

    /**
     * 更新一个动态定时任务
     * http://localhost:10085/tbTask/save?taskId=2&taskExp=0/2 * * * * ?&taskClass=cn.huanzi.qch.springboottimer.task.MyRunnable3
     */
    @RequestMapping("save")
    public String save(TbTask task) throws IllegalAccessException {
        //先更新表数据
        TbTask tbTask = tbTaskRepository.getOne(task.getTaskId());

        //null值忽略
        List<String> ignoreProperties = new ArrayList<>(7);

        //反射获取Class的属性（Field表示类中的成员变量）
        for (Field field : task.getClass().getDeclaredFields()) {
            //获取授权
            field.setAccessible(true);
            //属性名称
            String fieldName = field.getName();
            //属性的值
            Object fieldValue = field.get(task);

            //找出值为空的属性，我们复制的时候不进行赋值
            if(null == fieldValue){
                ignoreProperties.add(fieldName);
            }
        }

        //org.springframework.beans BeanUtils.copyProperties(A,B)：A中的值付给B
        BeanUtils.copyProperties(task, tbTask,ignoreProperties.toArray(new String[0]));
        tbTaskRepository.save(tbTask);
        TestScheduler2.tasks.clear();

        //停止旧任务
        testScheduler2.stop(tbTask.getTaskId());

        //重新启动
        testScheduler2.start(tbTask.getTaskId());
        return "操作成功";
    }
}
