package cn.huanzi.qch.springbootjpa.tbdescription.vo;

import cn.huanzi.qch.springbootjpa. common.pojo.PageCondition;import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class TbDescriptionVo extends PageCondition implements Serializable {
    private Integer id;//

    private Integer userId;//

    private String description;//

}
