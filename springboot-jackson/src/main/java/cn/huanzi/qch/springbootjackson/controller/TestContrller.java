package cn.huanzi.qch.springbootjackson.controller;

import cn.huanzi.qch.springbootjackson.vo.UserVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/")
public class TestContrller {
    /**
     * 跳转页面，页面引入了jquery，主要用于下面的ajax调用测试
     */
    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    /*
        $.ajax({
           type:"POST",
           url:"http://localhost:10099/testByJson",
           data:JSON.stringify({
                userName:"sa",
                pass_word:"123fff",
                captcha:"abcd",
                createDate:"2019-08-05 11:34:31"
            }),
           dataType:"JSON",
           contentType:"application/json;charset=UTF-8",
           success:function(data){
               console.log(data);
           },
           error:function(data){
                console.log("报错啦");
           }
        })
     */
    /**
     * 反序列化方式注入
     */
    @PostMapping("testByJson")
    public UserVo testByJson(@RequestBody UserVo userVo) {
        System.out.println(userVo);
        return userVo;
    }

    /*
        $.ajax({
           type:"GET",
           url:"http://localhost:10099/testByMvc",
           data:{
                username:"sa",
                password:"123fff",
                captcha:"abcd"
            },
           dataType:"JSON",
           contentType:"application/json;charset=UTF-8",
           success:function(data){
               console.log(data);
           },
           error:function(data){
                console.log("报错啦");
           }
        })
     */
    /**
     * MVC方式注入
     */
    @GetMapping("testByMvc")
    public UserVo testByMvc(UserVo userVo) {
        System.out.println(userVo);
        return userVo;
    }

    /**
     * 测试 ObjectMapper对象
     */
    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            //当属性的值为空（null或者""）时，不进行序列化，可以减少数据传输
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            //设置日期格式
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

            //1、Java对象转Json字符串
            UserVo userVo = new UserVo();
            userVo.setUsername("张三");
            userVo.setPassword("666");
            String jsonString = mapper.writeValueAsString(userVo);
            System.out.println(jsonString);

            //2、Json字符串转Java对象
            jsonString = "{\"userName\":\"张三\"}";
            UserVo userVo1 = mapper.readValue(jsonString, UserVo.class);
            System.out.println(userVo1);

            //3、Java对象类型转换
            HashMap<Object, Object> map = new HashMap<>();
            map.put("userName", "张三");
            UserVo userVo2 = mapper.convertValue(map, UserVo.class);
            System.out.println(userVo2);

            //4、将json字符串转换成List
            String listJsonString = "[{\"userName\":\"张三\"},{\"userName\":\"李四\"}]";
            List<UserVo> userVoList = mapper.readValue(listJsonString, mapper.getTypeFactory().constructParametricType(List.class, UserVo.class));
            System.out.println(userVoList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
