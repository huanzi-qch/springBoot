package cn.huanzi.qch.springbootsecurity.sysuser.controller;

import cn.huanzi.qch.springbootsecurity.common.controller.*;
import cn.huanzi.qch.springbootsecurity.sysuser.pojo.SysUser;
import cn.huanzi.qch.springbootsecurity.sysuser.vo.SysUserVo;
import cn.huanzi.qch.springbootsecurity.sysuser.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sysUser/")
public class SysUserController extends CommonController<SysUserVo, SysUser, String> {
    @Autowired
    private SysUserService sysUserService;
}
