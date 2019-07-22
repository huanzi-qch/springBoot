package cn.huanzi.qch.springbootsecurity.sysshortcutmenu.controller;

import cn.huanzi.qch.springbootsecurity.common.controller.*;
import cn.huanzi.qch.springbootsecurity.sysshortcutmenu.pojo.SysShortcutMenu;
import cn.huanzi.qch.springbootsecurity.sysshortcutmenu.vo.SysShortcutMenuVo;
import cn.huanzi.qch.springbootsecurity.sysshortcutmenu.service.SysShortcutMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sysShortcutMenu/")
public class SysShortcutMenuController extends CommonController<SysShortcutMenuVo, SysShortcutMenu, String> {
    @Autowired
    private SysShortcutMenuService sysShortcutMenuService;
}
