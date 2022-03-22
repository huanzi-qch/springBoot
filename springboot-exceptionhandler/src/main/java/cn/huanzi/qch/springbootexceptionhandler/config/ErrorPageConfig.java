package cn.huanzi.qch.springbootexceptionhandler.config;

import cn.huanzi.qch.springbootexceptionhandler.pojo.ErrorPageException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义errorPage
 */
@Controller
public class ErrorPageConfig implements ErrorController{

    private final static String ERROR_PATH = "/error" ;

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(ERROR_PATH)
    public void errorPathHandler(HttpServletRequest request, HttpServletResponse response) throws Throwable{
        //解决RestControllerAdvice无法拦截Filter内抛出异常
        if (request.getAttribute("javax.servlet.error.exception") != null) {
            throw (Throwable) request.getAttribute("javax.servlet.error.exception");
        }

        //抛出ErrorPageException异常，方便被ExceptionHandlerConfig处理
        throw new ErrorPageException(String.valueOf(response.getStatus()));
    }
}
