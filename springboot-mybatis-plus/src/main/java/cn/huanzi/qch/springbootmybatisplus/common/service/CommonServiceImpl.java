package cn.huanzi.qch.springbootmybatisplus.common.service;

import cn.huanzi.qch.springbootmybatisplus.common.entity.PageCondition;
import cn.huanzi.qch.springbootmybatisplus.common.entity.PageInfo;
import cn.huanzi.qch.springbootmybatisplus.common.entity.Result;
import cn.huanzi.qch.springbootmybatisplus.common.mapper.CommonMapper;
import cn.huanzi.qch.springbootmybatisplus.util.CopyUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用service实现类
 * @param <V> vo对象
 * @param <T> entity实体
 */
public class CommonServiceImpl<V,T> implements CommonService<V,T> {

    @Autowired
    private CommonMapper<T> commonMapper;

    private final Class<V> entityVoClass;//实体类Vo

    private final Class<T> entityClass;//实体类

    public CommonServiceImpl() {
        Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        this.entityVoClass = (Class<V>) types[0];
        this.entityClass = (Class<T>) types[1];
    }

    @Override
    public Result<PageInfo<V>> page(V entityVo) {
        //实体类缺失分页信息
        if (!(entityVo instanceof PageCondition)) {
            throw new RuntimeException("实体类" + entityVoClass.getName() + "未继承PageCondition");
        }
        PageCondition pageCondition = (PageCondition) entityVo;

        T entity = CopyUtil.copy(entityVo, entityClass);

        //查询条件
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);

        //排序
        if(!StringUtils.isEmpty(pageCondition.getSord()) && "desc".equalsIgnoreCase(pageCondition.getSord())){
            queryWrapper.orderByDesc(pageCondition.getSidx());
        }else{
            queryWrapper.orderByAsc(pageCondition.getSidx());
        }

        //分页
        IPage<T> page = new Page<>(pageCondition.getPage(), pageCondition.getRows());

        //查询获取数据
        page = commonMapper.selectPage(page, queryWrapper);

        //拼接数据
        PageInfo<V> pageInfo = PageInfo.of(page, entityVoClass);
        pageInfo.setSidx(pageCondition.getSidx());
        pageInfo.setSord(pageCondition.getSord());
        return Result.build(pageInfo);
    }

    @Override
    public Result<List<V>> list(V entityVo) {
        T entity = CopyUtil.copy(entityVo, entityClass);
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);
        return Result.build(CopyUtil.copyList(commonMapper.selectList(queryWrapper),entityVoClass));
    }

    @Override
    public Result<V> get(String id) {
        return Result.build(CopyUtil.copy(commonMapper.selectById(id),entityVoClass));
    }

    @Override
    public Result<V> save(V entityVo) {
        //传进来的对象（属性可能残缺）
        T entity = CopyUtil.copy(entityVo, entityClass);

        //最终要保存的对象
        T entityFull = entity;

        Object id = null;

        //为空的属性值，忽略属性，BeanUtils复制的时候用到
        List<String> ignoreProperties = new ArrayList<>();

        //获取最新数据，解决部分更新时jpa其他字段设置null问题
        try {
            //反射获取Class的属性（Field表示类中的成员变量）
            for (Field field : entity.getClass().getDeclaredFields()) {
                //获取授权
                field.setAccessible(true);
                //属性名称
                String fieldName = field.getName();
                //属性的值
                Object fieldValue = field.get(entity);

                //找出Id主键
                if (field.isAnnotationPresent(TableId.class) && !StringUtils.isEmpty(fieldValue)) {
                    id = fieldValue;
                    entityFull = commonMapper.selectById((Serializable) id);
                }

                //找出值为空的属性，值为空则为忽略属性
                if(null == fieldValue){
                    ignoreProperties.add(fieldName);
                }
            }
            /*
                org.springframework.beans BeanUtils.copyProperties(A,B); 是A中的值付给B
                org.apache.commons.beanutils; BeanUtils.copyProperties(A,B);是B中的值付给A
                把entity的值赋给entityFull，第三个参数是忽略属性，表示不进行赋值
             */
            BeanUtils.copyProperties(entity, entityFull, ignoreProperties.toArray(new String[0]));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //新增或更新
        if(StringUtils.isEmpty(id)){
            //1插入成功、0失败
            commonMapper.insert(entityFull);
        }else{
            commonMapper.updateById(entityFull);
        }

        return Result.build(CopyUtil.copy(entityFull,entityVoClass));
    }

    @Override
    public Result<String> delete(String id) {
        //1删除成功、0失败
        return Result.build(String.valueOf(commonMapper.deleteById(id)));
    }
}
