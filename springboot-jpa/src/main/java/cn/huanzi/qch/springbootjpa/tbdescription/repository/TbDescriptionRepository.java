package cn.huanzi.qch.springbootjpa.tbdescription.repository;

import cn.huanzi.qch.springbootjpa.common.repository.*;
import cn.huanzi.qch.springbootjpa.tbdescription.pojo.TbDescription;
import org.springframework.stereotype.Repository;

@Repository
public interface TbDescriptionRepository extends CommonRepository<TbDescription, Integer> {
}
