package cn.huanzi.qch.springbootmybatisplus.common.entity;

import cn.huanzi.qch.springbootmybatisplus.util.CopyUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * 分页对象（参考JqGrid插件）
 */
@Data
public class PageInfo<T> {
    private int page;//当前页码
    private int pageSize;//页面大小
    private String sidx;//排序字段
    private String sord;//排序方式

    private List<T> rows;//分页结果
    private int records;//总记录数
    private int total;//总页数

    /**
     * 获取统一分页对象
     */
    public static <T> PageInfo<T> of(IPage page, Class<T> entityVoClass) {
        int records = (int) page.getTotal();
        int pageSize = (int) page.getSize();
        int total = records % pageSize == 0 ? records / pageSize : records / pageSize + 1;

        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setPage((int) page.getCurrent());//页码
        pageInfo.setPageSize(pageSize);//页面大小
        pageInfo.setRows(CopyUtil.copyList(page.getRecords(), entityVoClass));//分页结果
        pageInfo.setRecords(records);//总记录数
        pageInfo.setTotal(total);//总页数
        return pageInfo;
    }
}
