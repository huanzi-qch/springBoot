package cn.huanzi.qch.springbootsecurity.config;

import cn.huanzi.qch.springbootsecurity.sysmenu.vo.SysMenuVo;
import cn.huanzi.qch.springbootsecurity.sysshortcutmenu.service.SysShortcutMenuService;
import cn.huanzi.qch.springbootsecurity.sysshortcutmenu.vo.SysShortcutMenuVo;
import cn.huanzi.qch.springbootsecurity.sysuser.service.SysUserService;
import cn.huanzi.qch.springbootsecurity.sysuser.vo.SysUserVo;
import cn.huanzi.qch.springbootsecurity.sysusermenu.service.SysUserMenuService;
import cn.huanzi.qch.springbootsecurity.sysusermenu.vo.SysUserMenuVo;
import cn.huanzi.qch.springbootsecurity.util.VerifyCodeImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMenuService sysUserMenuService;

    @Autowired
    private SysShortcutMenuService sysShortcutMenuService;

    /*  错误响应页面  */

    @GetMapping("/error/403")
    public ModelAndView error403(){
        return new ModelAndView("error/403");
    }

    @GetMapping("/error/404")
    public ModelAndView error404(){
        return new ModelAndView("error/404");
    }

    @GetMapping("/error/500")
    public ModelAndView error500(){
        return new ModelAndView("error/500");
    }



    @GetMapping("/loginPage")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");
        //登录用户
        SysUserVo sysUserVo = getLoginUser();
        modelAndView.addObject( "loginUser", sysUserVo);

        //登录用户系统菜单
        List<SysMenuVo> menuVoList = new ArrayList<>();
        List<SysUserMenuVo> sysUserMenuVoList = sysUserMenuService.findByUserId(sysUserVo.getUserId()).getData();
        sysUserMenuVoList.forEach((sysUserMenuVo) -> {
            SysMenuVo sysMenuVo = sysUserMenuVo.getSysMenu();
            if(StringUtils.isEmpty(sysMenuVo.getMenuParentId())){
                //上级节点
                menuVoList.add(sysMenuVo);
            }
        });
        sysUserMenuVoList.forEach((sysUserMenuVo) -> {
            SysMenuVo sysMenuVo = sysUserMenuVo.getSysMenu();
            if(!StringUtils.isEmpty(sysMenuVo.getMenuParentId())){
                //子节点
                menuVoList.forEach((sysMenuVoP) -> {
                    if(sysMenuVoP.getMenuId().equals(sysMenuVo.getMenuParentId())){
                        sysMenuVoP.getSysMenuVos().add(sysMenuVo);
                    }
                });
            }
        });
        modelAndView.addObject("menuList",menuVoList);


        //登录用户快捷菜单
        List<SysShortcutMenuVo> shortcutMenuVoList = new ArrayList<>();
        List<SysShortcutMenuVo> sysShortcutMenuVoList = sysShortcutMenuService.findByUserId(sysUserVo.getUserId()).getData();
        sysShortcutMenuVoList.forEach((SysShortcutMenuVo) -> {
            if(StringUtils.isEmpty(SysShortcutMenuVo.getShortcutMenuParentId())){
                //上级节点
                shortcutMenuVoList.add(SysShortcutMenuVo);
            }
        });
        sysShortcutMenuVoList.forEach((SysShortcutMenuVo) -> {
            if(!StringUtils.isEmpty(SysShortcutMenuVo.getShortcutMenuParentId())){
                //子节点
                shortcutMenuVoList.forEach((sysShortcutMenuVoP) -> {
                    if(sysShortcutMenuVoP.getShortcutMenuId().equals(SysShortcutMenuVo.getShortcutMenuParentId())){
                        sysShortcutMenuVoP.getSysShortcutMenuVos().add(SysShortcutMenuVo);
                    }
                });
            }
        });
        modelAndView.addObject("shortcutMenuList",shortcutMenuVoList);
        return modelAndView;
    }

    @GetMapping("/admin/test")
    public String test(){
        return "管理员才能访问啵";
    }

    /**
     * 获取验证码图片和文本(验证码文本会保存在HttpSession中)
     */
    @RequestMapping("getVerifyCodeImage")
    public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置页面不缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.getOutputStream();
        String verifyCode = VerifyCodeImageUtil.generateTextCode(VerifyCodeImageUtil.TYPE_NUM_UPPER, 4, null);

        //将验证码放到HttpSession里面
        request.getSession().setAttribute("verifyCode", verifyCode);
        System.out.println("本次生成的验证码为：" + verifyCode + ",已存放到HttpSession中");

        //设置输出的内容的类型为JPEG图像
        response.setContentType("image/jpeg");
        BufferedImage bufferedImage = VerifyCodeImageUtil.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);

        //写给浏览器
        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
    }

    /**
     * 获取登录用户
     */
    private SysUserVo getLoginUser() {
        User user = null;
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) user = (User) auth.getPrincipal();
        SysUserVo sysUserVo = sysUserService.findByLoginName(user.getUsername()).getData();
        //隐藏部分属性
        sysUserVo.setPassword(null);
        return sysUserVo;
    }
}
