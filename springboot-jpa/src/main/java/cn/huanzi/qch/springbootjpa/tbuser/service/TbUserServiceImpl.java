package cn.huanzi.qch.springbootjpa.tbuser.service;

import cn.huanzi.qch.springbootjpa.common.service.*;
import cn.huanzi.qch.springbootjpa.tbuser.pojo.TbUser;
import cn.huanzi.qch.springbootjpa.tbuser.vo.TbUserVo;
import cn.huanzi.qch.springbootjpa.tbuser.repository.TbUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class TbUserServiceImpl extends CommonServiceImpl<TbUserVo, TbUser, Integer> implements TbUserService{

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private TbUserRepository tbUserRepository;
}
