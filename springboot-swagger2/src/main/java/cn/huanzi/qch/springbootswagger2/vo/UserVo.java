package cn.huanzi.qch.springbootswagger2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "User实体Vo")
public class UserVo {
    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("用户名称")
    private String userName;
}
