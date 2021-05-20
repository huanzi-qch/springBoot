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