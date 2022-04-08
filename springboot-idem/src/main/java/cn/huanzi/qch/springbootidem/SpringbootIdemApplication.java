package cn.huanzi.qch.springbootidem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.huanzi.qch.springbootidem.idem.mapper")
public class SpringbootIdemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootIdemApplication.class, args);
    }

}
