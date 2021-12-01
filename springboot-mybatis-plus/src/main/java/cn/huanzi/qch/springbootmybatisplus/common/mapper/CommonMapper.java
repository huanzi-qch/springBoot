package cn.huanzi.qch.springbootmybatisplus.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 通用mapper映射
 */
public interface CommonMapper<T> extends BaseMapper<T> {

    /*
        投机取巧
        复杂SQL直接执行，不想写QueryWrapper条件拼来拼去...
     */

    @Update(value = "${sql}")
    int executeNativeSqlOfUpdate(String sql);

    @Select(value = "${sql}")
    Map<String,Object> executeNativeSqlOfOne(String sql);

    @Select(value = "${sql}")
    List<Map<String,Object>> executeNativeSqlOfList(String sql);

    @Select(value = "${sql}")
    T executeNativeSqlFindOne(String sql);

    @Select(value = "${sql}")
    List<T> executeNativeSqlFindList(String sql);
}
