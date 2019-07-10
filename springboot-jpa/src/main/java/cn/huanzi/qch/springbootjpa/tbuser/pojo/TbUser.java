package cn.huanzi.qch.springbootjpa.tbuser.pojo;

import cn.huanzi.qch.springbootjpa.tbdescription.pojo.TbDescription;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_user")
@Data
public class TbUser implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;//表id

    private String username;//用户名

    private String password;//密码

    private Date created;//创建时间

    private Integer descriptionId;//关联详情id

    @OneToOne
    @JoinColumn(name = "descriptionId",referencedColumnName = "id", insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    //用户描述信息
    private TbDescription description;
}

/*

CREATE TABLE `tb_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `created` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `description_id` int(11) NULL DEFAULT NULL COMMENT '关联详情id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;

 */