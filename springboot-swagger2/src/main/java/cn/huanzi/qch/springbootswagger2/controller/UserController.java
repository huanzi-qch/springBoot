package cn.huanzi.qch.springbootswagger2.controller;

import cn.huanzi.qch.springbootswagger2.vo.UserVo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
@Api(tags="user模块")
public class UserController {

    @ApiOperation(value = "根据id查询用户信息", notes = "查询数据库中某个的用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", paramType = "path",dataType="int", required = true)
    @GetMapping("get/{id}")
    public String getUserById(@PathVariable Integer id) {
        if(id == 0){
            return "查无此人";
        }else{
            return "{\"id\":\""+id+"\",\"userName\":\"张三\"}";
        }
    }

    @ApiOperation(value = "根据id、name查询用户信息", notes = "查询数据库中某个的用户信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户ID", paramType = "query",dataType="int"),
        @ApiImplicitParam(name = "name", value = "用户名称", paramType = "query",dataType="String"),
        @ApiImplicitParam(name = "appId", value = "应用认证id", paramType = "header",dataType="String"),
    })
    @GetMapping("getUserByIdAndName")
    public String getUserByIdAndName(HttpServletRequest request,Integer id, String name) {
        if(id == 0){
            return "查无此人";
        }else{
            return "{\"appId\":\""+request.getHeader("appId")+"\",\"id\":\""+id+"\",\"userName\":\""+name+"\"}";
        }
    }

    @ApiOperation(value = "根据UserVo对象查询用户信息", notes = "查询数据库中符合条件的用户信息")
    @ApiImplicitParam(paramType = "UserVo")
    @PostMapping("list")
    public String list(UserVo userVo) {
        if(userVo.getId() == 0){
            return "查无此人";
        }else{
            return userVo.toString();
        }
    }

    @ApiOperation(value = "分页查询用户信息", notes = "查询数据库中符合条件的用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "query",dataType="int",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", paramType = "query",dataType="int",defaultValue = "10"),
    })
    @ApiResponses({
            @ApiResponse(code=10001,message="xx业务规则不符合")
    })
    @PostMapping("page")
    public String page(Integer page,Integer pageSize,UserVo userVo) {
        if(userVo.getId() == 0){
            return "查无此人";
        }else{
            return "page:"+page+",pageSize:"+pageSize+","+userVo.toString();
        }
    }

}
