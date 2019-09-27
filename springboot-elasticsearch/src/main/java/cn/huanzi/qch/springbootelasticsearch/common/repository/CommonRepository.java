package cn.huanzi.qch.springbootelasticsearch.common.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 通用Repository
 *
 * @param <E> 实体类
 * @param <T> id主键类型
 */
@NoRepositoryBean
public interface CommonRepository<E,T extends Serializable> extends ElasticsearchRepository<E,T> {

}
