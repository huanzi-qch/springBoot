package cn.huanzi.qch.springbootswagger2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 接口文档API
 */
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 添加摘要信息(Docket)
     */
    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .apiInfo(new ApiInfoBuilder()
                        .title("标题：接口文档")
                        .description("描述：用于管理、查看API信息。")
                        .contact(new Contact("huanzi", "https://www.cnblogs.com/huanzi-qch/", "huznai.qch@qq.com"))
                        .version("版本号:1.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.huanzi.qch.springbootswagger2.Controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
