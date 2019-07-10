package cn.huanzi.qch.springbootmybatis.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Integer id;

    private String username;

    private String password;

    private Date created;
}

/*
    创建表SQL：
    DROP TABLE IF EXISTS `tb_user`;
    CREATE TABLE `tb_user`  (
      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
      `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
      `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
      `created` datetime NULL DEFAULT NULL COMMENT '创建时间',
      PRIMARY KEY (`id`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;
 */
