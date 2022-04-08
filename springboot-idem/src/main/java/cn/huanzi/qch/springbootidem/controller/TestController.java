package cn.huanzi.qch.springbootidem.controller;

import cn.huanzi.qch.springbootidem.idem.entity.Idem;
import cn.huanzi.qch.springbootidem.idem.service.IdemService;
import cn.huanzi.qch.springbootidem.util.UUIDUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test/")
public class TestController {

    private final IdemService idemService;

    private final StringRedisTemplate template;

    public TestController(IdemService idemService, StringRedisTemplate template) {
        this.idemService = idemService;
        this.template = template;
    }

    /**
     * 跳转页面
     */
    @RequestMapping("index")
    private ModelAndView index(String id){
        ModelAndView mv = new ModelAndView();
        mv.addObject("token",UUIDUtil.getUUID());
        if(id != null){
            Idem idem = new Idem();
            idem.setId(id);
            List select = (List)idemService.select(idem);
            idem = (Idem)select.get(0);
            mv.addObject("id", idem.getId());
            mv.addObject("msg", idem.getMsg());
            mv.addObject("version", idem.getVersion());
        }
        mv.setViewName("test.html");
        return mv;
    }

    /**
     * 表单提交测试
     */
    @RequestMapping("test")
    private String test(String token,String id,String msg,int version){
        //如果token缓存不存在，立即设置缓存且设置有效时长（秒）
        Boolean setIfAbsent = template.opsForValue().setIfAbsent(token, "1", 60 * 5, TimeUnit.SECONDS);

        //缓存设置成功返回true，失败返回false
        if(Boolean.TRUE.equals(setIfAbsent)){

            //模拟耗时
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //打印测试数据
            System.out.println(token+","+id+","+msg+","+version);

            return "操作成功！";
        }else{
            return "操作失败，表单已被提交...";
        }
    }
}
