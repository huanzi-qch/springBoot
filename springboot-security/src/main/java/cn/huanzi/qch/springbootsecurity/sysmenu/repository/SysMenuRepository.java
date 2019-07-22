package cn.huanzi.qch.springbootsecurity.sysmenu.repository;

import cn.huanzi.qch.springbootsecurity.common.repository.*;
import cn.huanzi.qch.springbootsecurity.sysmenu.pojo.SysMenu;
import org.springframework.stereotype.Repository;

@Repository
public interface SysMenuRepository extends CommonRepository<SysMenu, String> {
}
