package cn.huanzi.qch.springboottimer.task;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 动态定时任务表
 * 重要属性：唯一表id、Runnable任务类、Cron表达式，
 * 其他的都是一些额外补充说明属性
 */
@Entity
@Table(name = "tb_task")
@Data
public class TbTask {
    @Id
    private String taskId;//定时任务id
    private String taskName;//定时任务名称
    private String taskDesc;//定时任务描述
    private String taskExp;//定时任务Cron表达式
    private Integer taskStatus;//定时任务状态，0停用 1启用
    private String taskClass;//定时任务的Runnable任务类完整路径
    private Date updateTime;//更新时间
    private Date createTime;//创建时间
}
