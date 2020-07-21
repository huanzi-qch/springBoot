package cn.huanzi.qch.springbootjackson.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVoByMvc implements Serializable {
    private String username;
    private String password;
    private Date createDate;
    private String captcha;
}