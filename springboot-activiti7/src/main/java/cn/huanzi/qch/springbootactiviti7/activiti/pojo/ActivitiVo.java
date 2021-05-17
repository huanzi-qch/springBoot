package cn.huanzi.qch.springbootactiviti7.activiti.pojo;

import lombok.Data;

@Data
public class ActivitiVo {
    //请假流程发起
    private String businessKey;
    private String username;
    private String time;
    private String count;
    private String pm;
    private String bm;

    //完成任务
    private String taskId;
    private boolean auditFlag;
    private String message;
}
