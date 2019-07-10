package cn.huanzi.qch.springbootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringbootRedisApplication {

    @Autowired
    private StringRedisTemplate template;

    //测试：http://localhost:10088/redis/get/huanzi
    @RequestMapping("/redis/get/{key}")
    private String get(@PathVariable("key") String key){
        return template.opsForValue().get(key);
    }

    //测试：http://localhost:10088/redis/set/huanzi/huanzi
    @RequestMapping("/redis/set/{key}/{value}")
    private Boolean set(@PathVariable("key") String key,@PathVariable("value") String value){
        boolean flag = true;
        try {
            template.opsForValue().set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisApplication.class, args);
    }

}
