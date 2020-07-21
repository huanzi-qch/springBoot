package cn.huanzi.qch.springbootjackson.controller;

import cn.huanzi.qch.springbootjackson.vo.UserVoByJson;
import cn.huanzi.qch.springbootjackson.vo.UserVoByMvc;
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
     * 反序列化方式注入，只能post请求
     */
    @PostMapping("testByJson")
    public UserVoByJson testByJson(@RequestBody UserVoByJson userVo) {
        System.out.println(userVo);
        return userVo;
    }

    /*
        $.ajax({
           type:"POST",
           url:"http://localhost:10099/testByMvc",
           data:{
                username:"sa",
                password:"123fff",
                captcha:"abcd"
            },
           dataType:"JSON",
           //contentType:"application/json;charset=UTF-8",//使用这个，get请求能接到参数，post接不到
           contentType:"application/x-www-form-urlencoded",//使用这个，get、post都能接收到参数
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
    @RequestMapping("testByMvc")
    public UserVoByMvc testByMvc(UserVoByMvc userVo) {
        System.out.println(userVo);
        return userVo;
    }

    /*
    let datas = [];//test对象集合
     for(let i = 0; i < 5; i++){
         let data = {"userName":i + ""};//test对象
         datas.push(data);
     }
     $.ajax({
         type:"POST",
         url:"http://localhost:10099/testListByJson",
         data:JSON.stringify(datas),
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
     * 反序列化方式，接收集合对象，只能post请求
     */
    @PostMapping("testListByJson")
    public String testListByJson(@RequestBody List<UserVoByJson> userVos){
        userVos.forEach(System.out::println);
        return "{\"code\":200}";
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
            UserVoByJson userVo = new UserVoByJson();
            userVo.setUsername("张三");
            userVo.setPassword("666");
            String jsonString = mapper.writeValueAsString(userVo);
            System.out.println(jsonString);

            //2、Json字符串转Java对象
            jsonString = "{\"userName\":\"张三\"}";
            UserVoByJson userVo1 = mapper.readValue(jsonString, UserVoByJson.class);
            System.out.println(userVo1);

            //3、Java对象类型转换
            HashMap<Object, Object> map = new HashMap<>();
            map.put("userName", "张三");
            UserVoByJson userVo2 = mapper.convertValue(map, UserVoByJson.class);
            System.out.println(userVo2);

            //4、将json字符串转换成List
            String listJsonString = "[{\"userName\":\"张三\"},{\"userName\":\"李四\"}]";
            List<UserVoByJson> userVoList = mapper.readValue(listJsonString, mapper.getTypeFactory().constructParametricType(List.class, UserVoByJson.class));
            System.out.println(userVoList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
