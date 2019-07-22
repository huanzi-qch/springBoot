package cn.huanzi.qch.springbootsecurity.sysuserauthority.controller;

import cn.huanzi.qch.springbootsecurity.common.controller.*;
import cn.huanzi.qch.springbootsecurity.sysuserauthority.pojo.SysUserAuthority;
import cn.huanzi.qch.springbootsecurity.sysuserauthority.vo.SysUserAuthorityVo;
import cn.huanzi.qch.springbootsecurity.sysuserauthority.service.SysUserAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sysUserAuthority/")
public class SysUserAuthorityController extends CommonController<SysUserAuthorityVo, SysUserAuthority, String> {
    @Autowired
    private SysUserAuthorityService sysUserAuthorityService;
}
