package cn.huanzi.qch.springbootelasticsearch.common.service;

import cn.huanzi.qch.springbootelasticsearch.common.pojo.PageCondition;
import cn.huanzi.qch.springbootelasticsearch.common.pojo.PageInfo;
import cn.huanzi.qch.springbootelasticsearch.common.pojo.Result;
import cn.huanzi.qch.springbootelasticsearch.common.repository.CommonRepository;
import cn.huanzi.qch.springbootelasticsearch.util.CopyUtil;
import cn.huanzi.qch.springbootelasticsearch.util.UUIDUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 通用Service实现类
 *
 * @param <V> 实体类Vo
 * @param <E> 实体类
 * @param <T> id主键类型
 */
public class CommonServiceImpl<V, E, T extends Serializable> implements CommonService<V, E, T> {

    private Class<V> entityVoClass;//实体类Vo

    private Class<E> entityClass;//实体类

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate; //原生查询API

    @Autowired
    private CommonRepository<E, T> commonRepository;//注入实体类仓库

    public CommonServiceImpl() {
        Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        this.entityVoClass = (Class<V>) types[0];
        this.entityClass = (Class<E>) types[1];
    }

    @Override
    public Result<PageInfo<V>> page(V entityVo) {
        //实体类缺失分页信息
        if (!(entityVo instanceof PageCondition)) {
            throw new RuntimeException("实体类" + entityVoClass.getName() + "未继承PageCondition。");
        }

        // 构建查询对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //设置高亮
        queryBuilder.withHighlightFields(new HighlightBuilder.Field("title").preTags("<font color='red'>").postTags("</font>"));

        // 反射设置多条件等值
        E entity = CopyUtil.copy(entityVo, entityClass);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        try {
            //反射获取Class的属性（Field表示类中的成员变量）
            for (Field field : entity.getClass().getDeclaredFields()) {
                //获取授权
                field.setAccessible(true);
                //属性名称
                String fieldName = field.getName();
                //属性的值
                Object fieldValue = field.get(entity);

                //不为空则设置查询
                if (!StringUtils.isEmpty(fieldValue)) {
                    //等值查询 termQuery、模糊查询 fuzzyQuery
                    boolQueryBuilder.must(QueryBuilders.termQuery(fieldName, fieldValue));
                }
            }
            queryBuilder.withQuery(boolQueryBuilder);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //价格区间过滤
//        queryBuilder.withFilter(QueryBuilders.rangeQuery("price").from(10).to(50));

        //排序
        String sord = null;
        String sidx = ((PageCondition) entityVo).getSidx();
        if (!StringUtils.isEmpty(sidx)) {
            FieldSortBuilder sortBuilder = SortBuilders.fieldSort(sidx).order(SortOrder.ASC);
            sord = ((PageCondition) entityVo).getSord();
            if ("desc".equals(sord)) {
                sortBuilder = SortBuilders.fieldSort(sidx).order(SortOrder.DESC);
                sord = "asc";
            }
            queryBuilder.withSort(sortBuilder);
        }

        //设置page、size
        PageCondition pageCondition = (PageCondition) entityVo;
        queryBuilder.withPageable(pageCondition.getPageable());

        // 搜索，获取结果
        Page<E> page = commonRepository.search(queryBuilder.build());

        PageInfo<V> pageInfo = PageInfo.of(page, entityVoClass);
        pageInfo.setSidx(sidx);
        pageInfo.setSord(sord);
        return Result.of(pageInfo);
    }

    @Override
    public Result<List<V>> list(V entityVo) {
        //小技巧：设置每页展示的数量，从而达到类似查询所有的效果 注：Result window is too large, from + size must be less than or equal to: [10000]
        PageCondition pageCondition = (PageCondition) entityVo;
        pageCondition.setPage(1);
        pageCondition.setRows(10000);

        //调用page
        Result<PageInfo<V>> result = page(entityVo);
        return Result.of(result.getData().getRows(), result.isFlag(), result.getMsg());
    }

    @Override
    public Result<V> get(T id) {
        Optional<E> optionalE = commonRepository.findById(id);
        if (!optionalE.isPresent()) {
            throw new RuntimeException("ID不存在！");
        }
        return Result.of(CopyUtil.copy(optionalE.get(), entityVoClass));
    }

    @Override
    public Result<V> save(V entityVo) {
        //传进来的对象（属性可能残缺）
        E entity = CopyUtil.copy(entityVo, entityClass);

        //最终要保存的对象
        E entityFull = entity;

        //为空的属性值，忽略属性，BeanUtils复制的时候用到
        List<String> ignoreProperties = new ArrayList<String>();

        //获取最新数据，解决部分更新时jpa其他字段设置null问题
        try {
            //新增 true，更新 false，要求实体类的Id属性排在第一位，因为for循环读取是按照顺序的
            boolean isInsert = false;

            //反射获取Class的属性（Field表示类中的成员变量）
            for (Field field : entity.getClass().getDeclaredFields()) {
                //获取授权
                field.setAccessible(true);
                //属性名称
                String fieldName = field.getName();
                //属性的值
                Object fieldValue = field.get(entity);

                //找出Id主键
                if (field.isAnnotationPresent(Id.class)) {
                    if (!StringUtils.isEmpty(fieldValue)) {
                        //如果Id主键不为空，则为更新
                        Optional<E> one = commonRepository.findById((T) fieldValue);
                        if (one.isPresent()) {
                            entityFull = one.get();
                        }
                    } else {
                        //如果Id主键为空，则为新增
                        fieldValue = UUIDUtil.getUUID();
                        //set方法，第一个参数是对象
                        field.set(entity, fieldValue);
                        isInsert = true;
                    }
                }
                //如果前端不传这两个值，后台来维护创建时间、更新时间
                if (isInsert && "createTime".equals(fieldName) && StringUtils.isEmpty(fieldValue)) {
                    //set方法，第一个参数是对象
                    field.set(entity, new Date());
                }
                if ("updateTime".equals(fieldName) && StringUtils.isEmpty(fieldValue)) {
                    //set方法，第一个参数是对象
                    field.set(entity, new Date());
                }

                //找出值为空的属性，值为空则为忽略属性，我们复制的时候不进行赋值
                if (null == fieldValue) {
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

        E e = commonRepository.save(entityFull);
        return Result.of(CopyUtil.copy(e, entityVoClass));
    }

    @Override
    public Result<T> delete(T id) {
        commonRepository.deleteById(id);
        return Result.of(id);
    }
}
