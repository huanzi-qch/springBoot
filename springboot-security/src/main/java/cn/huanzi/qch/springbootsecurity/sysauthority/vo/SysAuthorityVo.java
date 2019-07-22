package cn.huanzi.qch.springbootsecurity.sysauthority.vo;

import cn.huanzi.qch.springbootsecurity. common.pojo.PageCondition;import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class SysAuthorityVo extends PageCondition implements Serializable {
    private String authorityId;//权限id

    private String authorityName;//权限名称，ROLE_开头，全大写

    private String authorityRemark;//权限描述

}
