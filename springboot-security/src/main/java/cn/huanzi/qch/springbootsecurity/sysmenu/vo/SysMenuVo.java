package cn.huanzi.qch.springbootsecurity.sysmenu.vo;

import cn.huanzi.qch.springbootsecurity.common.pojo.PageCondition;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SysMenuVo extends PageCondition implements Serializable {
    private String menuId;//菜单id

    private String menuName;//菜单名称

    private String menuPath;//菜单路径

    private String menuParentId;//上级id

    private List<SysMenuVo> sysMenuVos = new ArrayList<>();//如果是父类，这里存孩子节点

}
