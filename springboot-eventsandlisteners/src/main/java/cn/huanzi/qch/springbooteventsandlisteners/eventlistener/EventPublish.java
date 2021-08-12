package cn.huanzi.qch.springbooteventsandlisteners.eventlistener;

import cn.huanzi.qch.springbooteventsandlisteners.pojo.UserVo;
import cn.huanzi.qch.springbooteventsandlisteners.pojo.WorkOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 事件发布
 */
@Slf4j
@RestController
@RequestMapping("/eventPublish/")
public class EventPublish {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 用户注册
     */
    @GetMapping("userRegister")
    public String userRegister(UserVo userVo) {
        log.info("用户注册!");

        //发布 用户注册事件
        applicationEventPublisher.publishEvent(userVo);

        return "操作成功！";
    }

    /**
     * 业务工单发起
     */
    @GetMapping("workOrderStart")
    public String workOrderStart(WorkOrderVo workOrderVo) {
        log.info("业务工单发起!");

        //发布 业务工单发起事件
        applicationEventPublisher.publishEvent(workOrderVo);

        return "操作成功！";
    }
}
