package cn.huanzi.qch.springbootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class Controller {

    @Autowired
    private StringRedisTemplate template;

    /**
     * 获取缓存
     */
    //测试：http://localhost:10088/redis/get/huanzi
    @RequestMapping("/redis/get/{key}")
    public String get(@PathVariable("key") String key){
        return template.opsForValue().get(key);
    }

    /**
     * 设置缓存
     */
    //测试：http://localhost:10088/redis/set/huanzi/huanzi
    @RequestMapping("/redis/set/{key}/{value}")
    public Boolean set(@PathVariable("key") String key,@PathVariable("value") String value){
        boolean flag = true;
        try {
            template.opsForValue().set(key,value);
            //有效时长（秒）
            template.expire(key, 10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }

    /**
     * 发布消息
     */
    //测试：http://localhost:10088/redis/eventPush
    @RequestMapping("/redis/eventPush")
    public Boolean eventPush(){
        template.convertAndSend("topic1","topic1-我是第一种事件消息");
        template.convertAndSend("topic2","topic2-我是第二种事件消息");
        return true;
    }
}
