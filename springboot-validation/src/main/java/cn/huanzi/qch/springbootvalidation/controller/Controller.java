package cn.huanzi.qch.springbootvalidation.controller;

import cn.huanzi.qch.springbootvalidation.pojo.Result;
import cn.huanzi.qch.springbootvalidation.vo.UserVoByAdd;
import cn.huanzi.qch.springbootvalidation.vo.UserVoByEdit;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 测试Controller
 */
@Validated
@RestController
@RequestMapping("/test/")
public class Controller {

    /**
     * 新增用户
     */
    @RequestMapping("addUser")
    public Result addUser(@Validated UserVoByAdd userVo){
        System.out.println(userVo);
        return Result.of( "操作成功！");
    }

    /**
     * 编辑用户
     */
    @RequestMapping("editUser")
    public Result editUser(@Validated UserVoByEdit userVo){
        System.out.println(userVo);
        return Result.of( "操作成功！");
    }

    /**
     * 根据id查找用户
     */
    @RequestMapping("findUserById")
    public Result findUserById(@Size(min = 1, max = 5,message = "id超出范围") @NotEmpty(message = "id不能为空") String id) {
        System.out.println(id);
        return Result.of( "操作成功！");
    }
}
