package cn.huanzi.qch.springbootthymeleaf.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户类Vo
 */
@Data
public class UserVo implements Serializable {

    private Integer id;

    private String username;

    private String password;

    private Date created;
}
