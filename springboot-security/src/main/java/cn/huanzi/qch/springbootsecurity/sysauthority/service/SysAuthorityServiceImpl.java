package cn.huanzi.qch.springbootsecurity.sysauthority.service;

import cn.huanzi.qch.springbootsecurity.common.service.*;
import cn.huanzi.qch.springbootsecurity.sysauthority.pojo.SysAuthority;
import cn.huanzi.qch.springbootsecurity.sysauthority.vo.SysAuthorityVo;
import cn.huanzi.qch.springbootsecurity.sysauthority.repository.SysAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class SysAuthorityServiceImpl extends CommonServiceImpl<SysAuthorityVo, SysAuthority, String> implements SysAuthorityService{

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private SysAuthorityRepository sysAuthorityRepository;
}
