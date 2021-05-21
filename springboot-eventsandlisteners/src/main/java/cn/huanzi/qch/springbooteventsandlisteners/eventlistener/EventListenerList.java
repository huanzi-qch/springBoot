package cn.huanzi.qch.springbooteventsandlisteners.eventlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 事件监听
 */
@Slf4j
@Component
public class EventListenerList {

    /**
     * 用户注册事件监听
     */
    @Async("asyncTaskExecutor")
    @EventListener
    @Order(1)//一个事件多个事监听，使用@order值越小，执行顺序优先
    public void userRegisterListener(UserEventSource eventSourceEvent){
        log.info("用户注册事件监听1："+eventSourceEvent.getUserVo());

        //开展其他业务，例如发送邮件、短信等
    }
    /**
     * 用户注册事件监听
     */
    @Async("asyncTaskExecutor")
    @EventListener
    @Order(2)//一个事件多个事监听，使用@order值越小，执行顺序优先
    public void userRegisterListener2(UserEventSource eventSourceEvent){
        log.info("用户注册事件监听2："+eventSourceEvent.getUserVo());

        //开展其他业务，例如发送邮件、短信等
    }

    /**
     * 业务工单发起事件监听
     */
    @Async("asyncTaskExecutor")
    @EventListener
    public void workOrderStartListener(WorkOrderEventSource eventSourceEvent){
        log.info("业务工单发起事件："+eventSourceEvent.getWorkOrderVo());

        //开展其他业务，例如发送邮件、短信等
    }
}
