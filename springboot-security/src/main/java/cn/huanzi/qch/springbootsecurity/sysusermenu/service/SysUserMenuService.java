package cn.huanzi.qch.springbootsecurity.sysusermenu.service;

import cn.huanzi.qch.springbootsecurity.common.pojo.Result;
import cn.huanzi.qch.springbootsecurity.common.service.CommonService;
import cn.huanzi.qch.springbootsecurity.sysusermenu.pojo.SysUserMenu;
import cn.huanzi.qch.springbootsecurity.sysusermenu.vo.SysUserMenuVo;

import java.util.List;

public interface SysUserMenuService extends CommonService<SysUserMenuVo, SysUserMenu, String> {
    Result<List<SysUserMenuVo>> findByUserId(String userId);
}
