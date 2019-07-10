package cn.huanzi.qch.springbootmybatis.controller;

import cn.huanzi.qch.springbootmybatis.pojo.Result;
import cn.huanzi.qch.springbootmybatis.pojo.User;
import cn.huanzi.qch.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //测试：http://localhost:10090/user/insert?username=张三疯&password=111&created=2019-11-11 11:11:11
    @RequestMapping("/insert")
    public Result insert(User user) {
        return  userService.insert(user);
    }

    //测试：http://localhost:10090/user/delete?id=44
    @RequestMapping("/delete")
    public Result delete(User user) {
        return  userService.delete(user);
    }

    //测试：http://localhost:10090/user/update?id=44&username=张四疯
    @RequestMapping("/update")
    public Result update(User user) {
        return  userService.update(user);
    }

    //测试：http://localhost:10090/user/select?id=44
    @RequestMapping("/select")
    public Result select(User user) {
        return  userService.select(user);
    }
}
