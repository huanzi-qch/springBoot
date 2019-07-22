package cn.huanzi.qch.springbootsecurity.sysshortcutmenu.service;

import cn.huanzi.qch.springbootsecurity.common.pojo.Result;
import cn.huanzi.qch.springbootsecurity.common.service.CommonService;
import cn.huanzi.qch.springbootsecurity.sysshortcutmenu.pojo.SysShortcutMenu;
import cn.huanzi.qch.springbootsecurity.sysshortcutmenu.vo.SysShortcutMenuVo;

import java.util.List;

public interface SysShortcutMenuService extends CommonService<SysShortcutMenuVo, SysShortcutMenu, String> {
    Result<List<SysShortcutMenuVo>> findByUserId(String userId);
}
