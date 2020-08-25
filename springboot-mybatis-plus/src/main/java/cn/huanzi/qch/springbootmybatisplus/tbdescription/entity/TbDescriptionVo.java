package cn.huanzi.qch.springbootmybatisplus.tbdescription.entity;

import cn.huanzi.qch.springbootmybatisplus.common.entity.PageCondition;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 用户描述表
 * </p>
 *
 * @author huanzi-qch
 * @since 2020-08-25
 */
@Data
public class TbDescriptionVo extends PageCondition {
    private Integer id;
    private Integer userId;
    private String description;
}
