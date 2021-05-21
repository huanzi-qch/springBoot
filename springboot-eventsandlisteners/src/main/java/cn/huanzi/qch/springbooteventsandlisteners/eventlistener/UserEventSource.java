package cn.huanzi.qch.springbooteventsandlisteners.eventlistener;

import cn.huanzi.qch.springbooteventsandlisteners.pojo.UserVo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户事件源
 */
@Getter
@Setter
public class UserEventSource extends ApplicationEvent {
    private UserVo userVo;

    UserEventSource(UserVo userVo) {
        super(userVo);
        this.userVo = userVo;
    }
}
