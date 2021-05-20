package cn.huanzi.qch.springbootexceptionhandler.config;

import cn.huanzi.qch.springbootexceptionhandler.pojo.ErrorEnum;
import cn.huanzi.qch.springbootexceptionhandler.pojo.ErrorPageException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public void errorPathHandler(HttpServletResponse response) {
        //抛出ErrorPageException异常，方便被ExceptionHandlerConfig处理
        ErrorEnum errorEnum;
        switch (response.getStatus()) {
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
        throw new ErrorPageException(errorEnum);
    }
}
