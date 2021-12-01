package cn.huanzi.qch.springbootmybatisplus.tbuser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author huanzi-qch
 * @since 2020-08-25
 */
@Data
@TableName("tb_user")
public class TbUser {

    /**
     * 表id
     */
    //主键生成策略自动递增：type = IdType.AUTO，指定id回显
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField(value="username")
    private String username;

    /**
     * 密码
     */
    @TableField(value="password")
    private String password;

    /**
     * 创建时间
     */
    @TableField(value="created")
    private Date created;

    /**
     * 关联详情id
     */
    @TableField(value="description_id")
    private Integer descriptionId;
}
