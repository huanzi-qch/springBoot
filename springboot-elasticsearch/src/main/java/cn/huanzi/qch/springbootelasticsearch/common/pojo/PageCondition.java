package cn.huanzi.qch.springbootelasticsearch.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

/**
 * 分页条件（参考JqGrid插件）
 */
@Data
//当属性的值为空（null或者""）时，不进行序列化，可以减少数据传输
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PageCondition {
    private Integer page;//当前页码
    private Integer rows;//页面大小
    private String sidx;//排序字段
    private String sord;//排序方式

    /**
     * 获取JPA的分页查询对象
     */
    @JsonIgnore
    public Pageable getPageable() {
        //处理非法页码
        if (StringUtils.isEmpty(page) || page < 0) {
            page = 1;
        }
        //处理非法页面大小
        if (StringUtils.isEmpty(rows) || rows < 0) {
            rows = 10;
        }
        return PageRequest.of(page - 1, rows);
    }
}
