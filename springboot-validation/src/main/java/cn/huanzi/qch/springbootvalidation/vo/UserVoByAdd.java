package cn.huanzi.qch.springbootvalidation.vo;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * 用户Vo
 */
@Data
public class UserVoByAdd {

    @Pattern(regexp = "\\d+$",message = "主键只能是数字")
    @NotEmpty(message = "主键不能为空")
    private String id;//表id

    @NotEmpty(message = "名字不能为空")
    private String name;//名字

    @DecimalMin(value = "18",message = "年龄不能小于18岁")
    @DecimalMax(value = "25",message = "年龄不能大于25岁")
    @NotNull(message = "年龄不能为空")
    private Integer age;//年龄

    @NotEmpty(message = "地址不能为空")
    private String addr;//地址

    @Email(message = "邮件格式不正确")
    @NotEmpty(message = "邮件不能为空")
    private String email;//邮件
}
