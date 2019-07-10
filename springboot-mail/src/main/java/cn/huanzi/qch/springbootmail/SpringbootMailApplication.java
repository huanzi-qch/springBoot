package cn.huanzi.qch.springbootmail;

import cn.huanzi.qch.springbootmail.serive.mail.SpringBootMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.io.File;
import java.util.ArrayList;

@RestController
@SpringBootApplication
public class SpringbootMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMailApplication.class, args);
    }

    @Autowired
    private SpringBootMailService springBootMailService;

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/")
    public String index() throws MessagingException {
        //简单邮件
        springBootMailService.sendSimpleMail("1726639183@qq.com","Simple Mail","第一封简单邮件");

        //HTML格式邮件
        Context context = new Context();
        context.setVariable("username","我的小号");
        springBootMailService.sendHtmlMail("1726639183@qq.com","HTML Mail",templateEngine.process("mail/mail",context));

        //HTML格式邮件，带附件
        Context context2 = new Context();
        context2.setVariable("username","我的小号（带附件）");
        ArrayList<File> files = new ArrayList<>();
        //File对象
        files.add(new File("C:\\Users\\Administrator\\Desktop\\上传测试.txt"));
        files.add(new File("C:\\Users\\Administrator\\Desktop\\上传测试2.txt"));
        springBootMailService.sendAttachmentsMail("1726639183@qq.com","Attachments Mail",templateEngine.process("mail/attachment",context2),files);

        return "欢迎访问 springboot-mail，邮件发送成功！";
    }
}
