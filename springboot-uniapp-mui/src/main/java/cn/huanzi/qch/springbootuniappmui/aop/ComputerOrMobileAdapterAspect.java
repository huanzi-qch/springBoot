package cn.huanzi.qch.springbootuniappmui.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * PC端、手机端页面适配器
 */
@Aspect
@Component
@Slf4j
public class ComputerOrMobileAdapterAspect {

    /**
     * Pointcut 切入点
     * 匹配controller包下面的所有方法
     */
    @Pointcut(value = "execution(public * cn.huanzi.qch.springbootuniappmui.*.controller.*.*(..))")
    public void pointcut() {
    }

    /**
     * 环绕通知
     */
    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint pjp) {
        //request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        try {
            //获取返回值
            Object o = pjp.proceed();

            //拦截返回值为ModelAndView即可
            if("org.springframework.web.servlet.ModelAndView".equals(o.getClass().getName())){
                 /*
                PC端ua：windows nt、macintosh
                移动端ua：iphone、ipod、android
             */
                String adapter;
                String ua = request.getHeader("user-agent").toLowerCase();
                if (ua.contains("iphone") || ua.contains("ipod") || ua.contains("android")) {
                    adapter = "mobile";
                } else {
                    adapter = "computer";
                }
                log.info("PC端、手机端页面适配器：" + adapter);

                //强势修改
                ModelAndView modelAndView = (ModelAndView) o;
                String viewName = modelAndView.getViewName();
                modelAndView.setViewName(adapter + "/" + viewName);
                o = modelAndView;
            }

            return o;
        } catch (Throwable throwable) {
            //返回统一错误页面
            log.error(throwable.getMessage());
            return new ModelAndView("common/error/500");
        }
    }
}
