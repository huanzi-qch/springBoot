package cn.huanzi.qch.springbootmybatisplus.tbuser.entity;

import cn.huanzi.qch.springbootmybatisplus.common.entity.PageCondition;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author huanzi-qch
 * @since 2020-08-25
 */
@Data
public class TbUserVo extends PageCondition {
    private Integer id;
    private String username;
    private String password;
    private Date created;
    private Integer descriptionId;
}
