package cn.huanzi.qch.springbootvalidation.vo;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * 用户Vo
 */
@Data
public class UserVoByEdit {

    @NotEmpty(message = "主键不能为空")
    private String id;//表id

    private String name;//名字

    @DecimalMin(value = "18",message = "年龄不能小于18岁")
    @DecimalMax(value = "25",message = "年龄不能大于25岁")
    private Integer age;//年龄

    private String addr;//地址

    @Email(message = "邮件格式不正确")
    private String email;//邮件
}
