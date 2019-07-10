package cn.huanzi.qch.springbootjpa.tbuser.repository;

import cn.huanzi.qch.springbootjpa.common.repository.*;
import cn.huanzi.qch.springbootjpa.tbuser.pojo.TbUser;
import org.springframework.stereotype.Repository;

@Repository
public interface TbUserRepository extends CommonRepository<TbUser, Integer> {
}
