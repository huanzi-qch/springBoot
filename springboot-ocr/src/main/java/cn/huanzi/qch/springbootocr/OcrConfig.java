package cn.huanzi.qch.springbootocr;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OcrConfig {

    @Bean
    public Tesseract tesseract() {
        Tesseract instance = new Tesseract();
        String dir = System.getProperty("user.dir");
        instance.setDatapath(dir+"\\springboot-ocr\\src\\main\\resources\\tessdata"); // 设置语言包路径
        instance.setLanguage("chi_sim"); // 设置语言包，这里使用简体中文
        return instance;
    }
}
