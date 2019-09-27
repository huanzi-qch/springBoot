package cn.huanzi.qch.springbootelasticsearch.itembyes.repository;

import cn.huanzi.qch.springbootelasticsearch.common.repository.CommonRepository;
import cn.huanzi.qch.springbootelasticsearch.itembyes.pojo.ItemByEs;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemByEsRepository extends CommonRepository<ItemByEs, String> {
}
