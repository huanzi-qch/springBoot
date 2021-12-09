package cn.huanzi.qch.springbootaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Aspect 切面
 */
@Aspect
@Component
public class LogAspect {

    /**
     * Pointcut 切入点
     */
    /*
        切入点指示符如下：
        execution：用于匹配方法执行的连接点；
        within：用于匹配指定类型内的方法执行；
        this：用于匹配当前AOP代理对象类型的执行方法；注意是AOP代理对象的类型匹配，这样就可能包括引入接口也类型匹配；
        target：用于匹配当前目标对象类型的执行方法；注意是目标对象的类型匹配，这样就不包括引入接口也类型匹配；
        args：用于匹配当前执行的方法传入的参数为指定类型的执行方法；
        @within：用于匹配所以持有指定注解类型内的方法；
        @target：用于匹配当前目标对象类型的执行方法，其中目标对象持有指定的注解；
        @args：用于匹配当前执行的方法传入的参数持有指定注解的执行；
        @annotation：用于匹配当前执行方法持有指定注解的方法；
        bean：Spring AOP扩展的，AspectJ没有对于指示符，用于匹配特定名称的Bean对象的执行方法；
        reference pointcut：表示引用其他命名切入点，只有@ApectJ风格支持，Schema风格不支持。
    */
//    @Pointcut("execution(public * cn.huanzi.qch.springbootaop.SpringbootAopApplication.index(..))")//用于匹配方法执行
    @Pointcut("@annotation(cn.huanzi.qch.springbootaop.MyAnnotation)")//用于匹配带有注解的方法
    public void webLog(){}

    /**
     * 环绕通知
     */
    @Around(value = "webLog()")
    public Object arround(ProceedingJoinPoint pjp) {
        try {
            System.out.println("1、Around：方法环绕开始.....");
            Object o =  pjp.proceed();
            System.out.println("3、Around：方法环绕结束，结果是 :" + o);
            return o;
        } catch (Throwable e) {
            System.err.println(pjp.getSignature() + " 出现异常： " + e);
            return "异常";
        }
    }

    /**
     * 方法执行前
     */
    @Before(value = "webLog()")
    public void before(JoinPoint joinPoint){
        System.out.println("2、Before：方法执行开始...");
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD : " + request.getMethod());
        System.out.println("IP : " + request.getRemoteAddr());
        System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    /**
     * 方法执行结束，不管是抛出异常或者正常退出都会执行
     */
    @After(value = "webLog()")
    public void after(JoinPoint joinPoint){
        System.out.println("4、After：方法最后执行.....");
    }

    /**
     * 方法执行结束，增强处理
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret){
        // 处理完请求，返回内容
        System.out.println("5、AfterReturning：方法的返回值 : " + ret);
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing(value = "webLog()")
    public void throwss(JoinPoint joinPoint){
        System.err.println("AfterThrowing：方法异常时执行.....");
    }
}
