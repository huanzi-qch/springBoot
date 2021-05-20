package cn.huanzi.qch.springbootexceptionhandler.config;

import cn.huanzi.qch.springbootexceptionhandler.pojo.ErrorEnum;
import cn.huanzi.qch.springbootexceptionhandler.pojo.ErrorPageException;
import cn.huanzi.qch.springbootexceptionhandler.pojo.Result;
import cn.huanzi.qch.springbootexceptionhandler.pojo.ServiceException;
import cn.huanzi.qch.springbootexceptionhandler.util.ErrorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result exceptionHandler400(ServiceException e){
        //把错误信息输入到日志中
        log.error(ErrorUtil.errorInfoToString(e));
        return Result.error(e.getErrorEnum());
    }

    /**
     * 错误页面异常 统一处理
     */
    @ExceptionHandler(value = ErrorPageException.class)
    @ResponseBody
    public Result exceptionHandler(ErrorPageException e){
        //把错误信息输入到日志中
        log.error(ErrorUtil.errorInfoToString(e));
        return Result.error(e.getErrorEnum());
    }

    /**
     * 空指针异常 统一处理
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public Result exceptionHandler500(NullPointerException e){
        //把错误信息输入到日志中
        log.error(ErrorUtil.errorInfoToString(e));
        return Result.error(ErrorEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 未知异常 统一处理
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e){
        //把错误信息输入到日志中
        log.error(ErrorUtil.errorInfoToString(e));
        return Result.error(ErrorEnum.UNKNOWN);
    }
}
