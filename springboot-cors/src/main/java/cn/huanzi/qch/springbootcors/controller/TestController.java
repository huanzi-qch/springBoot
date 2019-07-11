package cn.huanzi.qch.springbootcors.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("cors/")
@RestController
public class TestController {
    /*
       通过注解配置CORS跨域测试
       $.ajax({
           type:"POST",
           url:"http://localhost:10095/cors/corsByAnnotation",
           data:{id:1},
		   dataType:"text",//因为我们响应的是不是json，这里要改一下
           contentType:"application/x-www-form-urlencoded",
           //contentType:"application/json;charset=UTF-8",//如果用这个，则为非简单请求
		   xhrFields:{ withCredentials:true },
           success:function(data){
               console.log(data);
           },
           error:function(data){
				console.log("报错啦");
           }
       })
    */
    @CrossOrigin(
            origins = "https://www.cnblogs.com",
            allowedHeaders = "*",
            methods = {RequestMethod.POST},
            allowCredentials = "true",
            maxAge = 3600
    )
    @PostMapping("corsByAnnotation")
    public String corsByAnnotation(String id) {
        return "corsByAnnotation，" + id;
    }

    /*
       通过Config配置CORS跨域测试
       $.ajax({
           type:"POST",
           url:"http://localhost:10095/cors/corsByConfig",
           data:{id:2},
		   dataType:"text",//因为我们响应的是不是json，这里要改一下
           contentType:"application/x-www-form-urlencoded",
           //contentType:"application/json;charset=UTF-8",//如果用这个，则为非简单请求
		   xhrFields:{ withCredentials:true },
           success:function(data){
               console.log(data);
           },
           error:function(data){
				console.log("报错啦");
           }
       })
    */
    @PostMapping("corsByConfig")
    public String corsByConfig(String id) {
        return "corsByConfig，" + id;
    }

    /*
       通过拦截器配置CORS跨域测试
       $.ajax({
           type:"POST",
           url:"http://localhost:10095/cors/corsByMyCorsFilter",
           data:{id:3},
		   dataType:"text",//因为我们响应的是不是json，这里要改一下
           contentType:"application/x-www-form-urlencoded",
           //contentType:"application/json;charset=UTF-8",//如果用这个，则为非简单请求
           xhrFields:{ withCredentials:true },
           success:function(data){
               console.log(data);
           },
           error:function(data){
                console.log("报错啦");
           }
        })
    */
    @PostMapping("corsByMyCorsFilter")
    public String corsByMyCorsFilter(String id) {
        return "corsByMyCorsFilter，" + id;
    }
}
