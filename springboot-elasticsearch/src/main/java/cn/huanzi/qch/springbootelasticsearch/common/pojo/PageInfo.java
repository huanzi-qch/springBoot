package cn.huanzi.qch.springbootelasticsearch.common.pojo;

import cn.huanzi.qch.springbootelasticsearch.util.CopyUtil;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页对象（参考JqGrid插件）
 */
@Data
public class PageInfo<M> {
    private Integer page;//当前页码
    private Integer pageSize;//页面大小
    private String sidx;//排序字段
    private String sord;//排序方式

    private List<M> rows;//分页结果
    private Integer records;//总记录数
    private Integer total;//总页数

    /**
     * 获取统一分页对象
     */
    public static <M> PageInfo<M> of(Page page, Class<M> entityModelClass) {
        int records = (int) page.getTotalElements();
        int pageSize = page.getSize();
        int total = records % pageSize == 0 ? records / pageSize : records / pageSize + 1;

        PageInfo<M> pageInfo = new PageInfo<>();
        pageInfo.setPage(page.getNumber() + 1);//页码
        pageInfo.setPageSize(pageSize);//页面大小
        pageInfo.setRows(CopyUtil.copyList(page.getContent(), entityModelClass));//分页结果
        pageInfo.setRecords(records);//总记录数
        pageInfo.setTotal(total);//总页数
        return pageInfo;
    }
}
