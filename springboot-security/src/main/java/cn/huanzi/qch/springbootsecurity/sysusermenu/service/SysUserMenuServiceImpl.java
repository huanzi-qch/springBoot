package cn.huanzi.qch.springbootsecurity.sysusermenu.service;

import cn.huanzi.qch.springbootsecurity.common.pojo.Result;
import cn.huanzi.qch.springbootsecurity.common.service.CommonServiceImpl;
import cn.huanzi.qch.springbootsecurity.sysusermenu.pojo.SysUserMenu;
import cn.huanzi.qch.springbootsecurity.sysusermenu.repository.SysUserMenuRepository;
import cn.huanzi.qch.springbootsecurity.sysusermenu.vo.SysUserMenuVo;
import cn.huanzi.qch.springbootsecurity.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class SysUserMenuServiceImpl extends CommonServiceImpl<SysUserMenuVo, SysUserMenu, String> implements SysUserMenuService{

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private SysUserMenuRepository sysUserMenuRepository;

    @Override
    public Result<List<SysUserMenuVo>> findByUserId(String userId) {
        return Result.of(CopyUtil.copyList(sysUserMenuRepository.findByUserId(userId), SysUserMenuVo.class));
    }
}
