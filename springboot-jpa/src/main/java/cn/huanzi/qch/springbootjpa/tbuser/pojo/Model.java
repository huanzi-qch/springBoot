package cn.huanzi.qch.springbootjpa.tbuser.pojo;

import lombok.Data;

/**
 * 自定义字段查询 Model
 */
@Data
public class Model {
    String username;
    Integer descriptionId;
    String description;

    public Model() {
    }

    public Model(String username, Integer descriptionId, String description) {
        this.username = username;
        this.descriptionId = descriptionId;
        this.description = description;
    }
}
