package cn.huanzi.qhc.springbootcache.tbuser.service;



import cn.huanzi.qhc.springbootcache.tbuser.pojo.TbUser;

import java.util.List;

public interface TbUserService {
    List<TbUser> list(TbUser entityVo);

    TbUser get(Integer id);

    TbUser save(TbUser entityVo);

    Integer delete(Integer id);
}
