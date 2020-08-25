package cn.huanzi.qch.springbootmybatisplus.common.service;

import cn.huanzi.qch.springbootmybatisplus.common.entity.PageInfo;
import cn.huanzi.qch.springbootmybatisplus.common.entity.Result;

import java.util.List;

/**
 * 通用service接口
 * @param <V> vo对象
 * @param <T> entity实体
 */
public interface CommonService<V,T> {

    /**
     * page接口 分页、排序
     */
    Result<PageInfo<V>> page(V entityVo);

    /**
     * list接口，条件查询
     */
    Result<List<V>> list(V entityVo);

    /**
     * get接口，根据id查询
     */
    Result<V> get(String id);

    /**
     * save接口，vo无id为新增、vo有id为更新（只更新vo有值的属性）
     */
    Result<V> save(V entityVo);

    /**
     * delete接口，根据id删除
     */
    Result<String> delete(String id);
}
