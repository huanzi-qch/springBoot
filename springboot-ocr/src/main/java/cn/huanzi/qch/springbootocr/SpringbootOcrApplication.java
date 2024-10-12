package cn.huanzi.qch.springbootocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootOcrApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootOcrApplication.class, args);
        // 使用默认浏览器打开
        try {
            Runtime.getRuntime().exec(String.format("cmd /c start %s", "http://localhost:10093/ocr/remote?url=https://i-blog.csdnimg.cn/blog_migrate/52d7bc4fc9ac8cf99e34421bac3b35a6.png"));
        } catch (Exception e) {
            System.out.println("打开失败");
        }
    }

}
