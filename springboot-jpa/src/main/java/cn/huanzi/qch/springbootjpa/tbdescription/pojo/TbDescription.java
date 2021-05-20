package cn.huanzi.qch.springbootjpa.tbdescription.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_description")
@Data
public class TbDescription implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;//表id

    private Integer userId;//用户id

    private String description;//用户描述

}