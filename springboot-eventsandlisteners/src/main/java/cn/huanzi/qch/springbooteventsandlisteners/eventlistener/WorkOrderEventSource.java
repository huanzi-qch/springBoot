package cn.huanzi.qch.springbooteventsandlisteners.eventlistener;

import cn.huanzi.qch.springbooteventsandlisteners.pojo.WorkOrderVo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 业务工单事件源
 */
@Getter
@Setter
public class WorkOrderEventSource extends ApplicationEvent {
    private cn.huanzi.qch.springbooteventsandlisteners.pojo.WorkOrderVo WorkOrderVo;

    WorkOrderEventSource(WorkOrderVo WorkOrderVo) {
        super(WorkOrderVo);
        this.WorkOrderVo = WorkOrderVo;
    }
}
