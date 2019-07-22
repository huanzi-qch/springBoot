package cn.huanzi.qch.springbootsecurity.sysusermenu.controller;

import cn.huanzi.qch.springbootsecurity.common.controller.*;
import cn.huanzi.qch.springbootsecurity.sysusermenu.pojo.SysUserMenu;
import cn.huanzi.qch.springbootsecurity.sysusermenu.vo.SysUserMenuVo;
import cn.huanzi.qch.springbootsecurity.sysusermenu.service.SysUserMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sysUserMenu/")
public class SysUserMenuController extends CommonController<SysUserMenuVo, SysUserMenu, String> {
    @Autowired
    private SysUserMenuService sysUserMenuService;
}
