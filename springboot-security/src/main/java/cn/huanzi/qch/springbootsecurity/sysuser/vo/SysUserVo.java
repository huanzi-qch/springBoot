package cn.huanzi.qch.springbootsecurity.sysuser.vo;

import cn.huanzi.qch.springbootsecurity. common.pojo.PageCondition;import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserVo extends PageCondition implements Serializable {
    private String userId;//用户id

    private String loginName;//登录名

    private String userName;//用户名称

    private String password;//登录密码

    private String valid;//软删除标识，Y/N

    private String limitedIp;//限制允许登录的IP集合

    private String limitedMac;//更高级别的安全限制，限制允许登录的mac地址集合

    private Date expiredTime;//账号失效时间，超过时间将不能登录系统

    private Date lastChangePwdTime;//最近修改密码时间，超出时间间隔，提示用户修改密码

    private String limitMultiLogin;//是否允许账号同一个时刻多人在线，Y/N

    private Date greateTime;//创建时间

    private Date updateTime;//修改时间

}
