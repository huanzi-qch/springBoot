package cn.huanzi.qch.springbootidem.idem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("idem")
public class Idem {
    @TableId(value = "id")
    private String id;//唯一主键

    @TableField(value="msg")
    private String msg;//业务数据

    @TableField(value="version")
    private Integer version;//乐观锁版本号
}
