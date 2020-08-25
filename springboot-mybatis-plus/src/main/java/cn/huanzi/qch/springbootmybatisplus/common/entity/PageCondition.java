package cn.huanzi.qch.springbootmybatisplus.common.entity;

import lombok.Data;

/**
 * 分页条件（参考JqGrid插件）
 */
@Data
public class PageCondition {
    private int page = 1;//当前页码
    private int rows = 10;//页面大小
    private String sidx;//排序字段
    private String sord;//排序方式
}
