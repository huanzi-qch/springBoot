package cn.huanzi.qch.springbootswagger2.config;

import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.service.Parameter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger UI 配置信息
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 添加摘要信息(Docket)
     */
    @Bean
    public Docket controllerApi() {
        //全局统一参数
        List<Parameter> globalOperationParametersList = new ArrayList<>();
        globalOperationParametersList.add(new ParameterBuilder().name("token").description("认证token").modelRef(new ModelRef("string")).parameterType("header").required(true).build());

        //设置全局响应状态码
        List<ResponseMessage> globalResponseMessageList = new ArrayList<>();
        globalResponseMessageList.add(new ResponseMessageBuilder().code(401).message("没有认证，请重新登录").build());
        globalResponseMessageList.add(new ResponseMessageBuilder().code(403).message("没有访问权限，请联系管理人员").build());
        globalResponseMessageList.add(new ResponseMessageBuilder().code(404).message("找不到资源").build());
        globalResponseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部错误").build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("API实时接口文档")
                        .description("用于实时查看、测试API")
                        .contact(new Contact("huanzi-qch", "https://www.cnblogs.com/huanzi-qch/", ""))
                        .version("版本号:1.0")
                        .build())
                .globalResponseMessage(RequestMethod.GET,globalResponseMessageList)
                .globalResponseMessage(RequestMethod.POST,globalResponseMessageList)
                .globalOperationParameters(globalOperationParametersList)
                .select()
                //API基础扫描路径
                .apis(RequestHandlerSelectors.basePackage("cn.huanzi.qch.springbootswagger2.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
