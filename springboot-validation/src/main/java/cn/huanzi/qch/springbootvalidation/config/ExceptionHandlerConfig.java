package cn.huanzi.qch.springbootvalidation.config;

import cn.huanzi.qch.springbootvalidation.pojo.Result;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


/**
 * 统一异常处理
 */
@RestControllerAdvice
public class ExceptionHandlerConfig {

    /**
     * validation参数校验异常 统一处理
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result exceptionHandler500(BindException e){
        e.printStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (ObjectError error : e.getAllErrors()) {
            stringBuilder.append("[");
            stringBuilder.append(((FieldError) error).getField());
            stringBuilder.append(" ");
            stringBuilder.append(error.getDefaultMessage());
            stringBuilder.append("]");
        }
        return Result.of(10002,false,"【参数校验失败】 " + stringBuilder.toString());
    }
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Result exceptionHandler500(ConstraintViolationException e){
        e.printStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (ConstraintViolation<?> error : e.getConstraintViolations()) {
            PathImpl pathImpl = (PathImpl) error.getPropertyPath();
            String paramName = pathImpl.getLeafNode().getName();
            stringBuilder.append("[");
            stringBuilder.append(paramName);
            stringBuilder.append(" ");
            stringBuilder.append(error.getMessage());
            stringBuilder.append("]");
        }
        return Result.of(10002,false,"【参数校验失败】 " + stringBuilder.toString());

    }

    /**
     * 未知异常 统一处理
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e){
        e.printStackTrace();
        return Result.of(10001,false,"【未知异常】 "+e.getMessage());
    }
}
