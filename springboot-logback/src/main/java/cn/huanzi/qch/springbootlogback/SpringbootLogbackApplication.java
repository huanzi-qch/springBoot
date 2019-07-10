package cn.huanzi.qch.springbootlogback;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j//使用lombok的@Slf4j，帮我们创建Logger对象，效果与下方获取日志对象一样
@SpringBootApplication
public class SpringbootLogbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLogbackApplication.class, args);
    }

    /**
     * 配置内部类
     */
    @Controller
    @Configuration
    class Config {

        /**
         * 获取日志对象，构造函数传入当前类，查找日志方便定位
         */
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Value("${user.home}")
        private String userName;

        /**
         * 端口
         */
        @Value("${server.port}")
        private String port;
        /**
         * 启动成功
         */
        @Bean
        public ApplicationRunner applicationRunner() {
            return applicationArguments -> {
                try {
                    InetAddress ia = InetAddress.getLocalHost();
                    //获取本机内网IP
                    log.info("启动成功：" + "http://" + ia.getHostAddress() + ":" + port + "/");
                    logger.info("${user.home} ：" + userName);
                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                }
            };
        }

        /**
         * 跳转实时日志
         */
        @GetMapping("/logging")
        public ModelAndView logging() {
            return new ModelAndView("logging.html");
        }
    }
}
