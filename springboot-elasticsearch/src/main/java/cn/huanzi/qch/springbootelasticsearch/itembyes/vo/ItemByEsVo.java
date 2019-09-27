package cn.huanzi.qch.springbootelasticsearch.itembyes.vo;

import cn.huanzi.qch.springbootelasticsearch.common.pojo.PageCondition;
import lombok.Data;

@Data
public class ItemByEsVo extends PageCondition {
    private String id;
    private String title; //标题
    private String category;// 分类
    private String brand; // 品牌
    private Double price; // 价格
    private String images; // 图片地址
}
