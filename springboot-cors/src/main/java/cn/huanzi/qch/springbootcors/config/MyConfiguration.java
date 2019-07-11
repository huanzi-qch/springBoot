package cn.huanzi.qch.springbootcors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/cors/corsByConfig")
                        .allowedOrigins("https://www.cnblogs.com")
                        .allowedMethods("POST")
                        .allowedHeaders("*")
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }
}
