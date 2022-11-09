package cn.huanzi.qch.springbootexceptionhandler.config;

import cn.huanzi.qch.springbootexceptionhandler.pojo.ErrorEnum;
import cn.huanzi.qch.springbootexceptionhandler.pojo.ErrorPageException;
import cn.huanzi.qch.springbootexceptionhandler.pojo.Result;
import cn.huanzi.qch.springbootexceptionhandler.pojo.ServiceException;
import cn.huanzi.qch.springbootexceptionhandler.util.ErrorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerConfig{

    /**
     * 业务异常 统一处理
     */
    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public Object exceptionHandler400(ServiceException e){
        return returnResult(e,Result.error(e.getErrorEnum()));
    }

    /**
     * 错误页面异常 统一处理
     */
    @ExceptionHandler(value = ErrorPageException.class)
    @ResponseBody
    public Object exceptionHandler(ErrorPageException e){
        ErrorEnum errorEnum;
        switch (Integer.parseInt(e.getCode())) {
            case 404:
                errorEnum = ErrorEnum.NOT_FOUND;
                break;
            case 403:
                errorEnum = ErrorEnum.FORBIDDEN;
                break;
            case 401:
                errorEnum = ErrorEnum.UNAUTHORIZED;
                break;
            case 400:
                errorEnum = ErrorEnum.BAD_REQUEST;
                break;
            default:
                errorEnum = ErrorEnum.UNKNOWN;
                break;
        }

        return returnResult(e,Result.error(errorEnum));
    }

    /**
     * 空指针异常 统一处理
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public Object exceptionHandler500(NullPointerException e){
        return returnResult(e,Result.error(ErrorEnum.INTERNAL_SERVER_ERROR));
    }

    /**
     * 其他异常 统一处理
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Object exceptionHandler(Exception e){
        return returnResult(e,Result.of(ErrorEnum.UNKNOWN.getCode(), false, "【" + e.getClass().getName() + "】" + e.getMessage()));
    }

    /**
     * 是否为ajax请求
     * ajax请求，响应json格式数据，否则应该响应html页面
     */
    private Object returnResult(Exception e,Result errorResult){
        //把错误信息输入到日志中
        log.error(ErrorUtil.errorInfoToString(e));

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();

        //设置http响应状态
        response.setStatus(200);

        //判断是否为ajax请求
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))){
            return errorResult;
        }else{
            return new ModelAndView("error","msg",errorResult.getMsg());
        }
    }
}
