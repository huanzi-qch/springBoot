package cn.huanzi.qch.springbootsecurity.sysauthority.pojo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sys_authority")
@Data
public class SysAuthority implements Serializable {
    @Id
    private String authorityId;//权限id

    private String authorityName;//权限名称，ROLE_开头，全大写

    private String authorityRemark;//权限描述

}
