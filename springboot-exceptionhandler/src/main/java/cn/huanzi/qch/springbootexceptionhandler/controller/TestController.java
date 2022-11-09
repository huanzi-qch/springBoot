package cn.huanzi.qch.springbootexceptionhandler.controller;

import cn.huanzi.qch.springbootexceptionhandler.pojo.ErrorEnum;
import cn.huanzi.qch.springbootexceptionhandler.pojo.Result;
import cn.huanzi.qch.springbootexceptionhandler.pojo.ServiceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟异常测试
 */
@RestController
@RequestMapping("/test/")
public class TestController {
    /**
     * 正常返回数据
     */
    @GetMapping("index")
    public Result index(){
        return Result.of("正常返回数据");
    }

    /**
     * 模拟空指针异常
     */
    @GetMapping("nullPointerException")
    public Result nullPointerException(){
        //故意制造空指针异常
        String msg = null;
        msg.equals("huanzi-qch");
        return Result.of("正常返回数据");
    }

    /**
     * 模拟业务异常，手动抛出业务异常
     */
    @GetMapping("serviceException")
    public Result serviceException(){
        throw new ServiceException(ErrorEnum.USER_NAME_IS_NOT_NULL);
    }

    /**
     * 其他异常
     */
    @GetMapping("runtimeException")
    public Result runtimeException(){
        throw new RuntimeException("其他异常");
    }
}
