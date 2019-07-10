package cn.huanzi.qch.springbootjpa.tbuser.vo;

import cn.huanzi.qch.springbootjpa.common.pojo.PageCondition;
import cn.huanzi.qch.springbootjpa.tbdescription.vo.TbDescriptionVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TbUserVo extends PageCondition implements Serializable {
    private Integer id;//表id

    private String username;//用户名

    private String password;//密码

    private Date created;//创建时间

    private Integer descriptionId;//关联详情id

    //用户描述信息
    private TbDescriptionVo description;

}
