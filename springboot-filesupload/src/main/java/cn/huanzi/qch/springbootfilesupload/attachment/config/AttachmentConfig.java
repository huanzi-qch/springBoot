package cn.huanzi.qch.springbootfilesupload.attachment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 附件管理Config配置
 */
@Component
public class AttachmentConfig implements WebMvcConfigurer {
    /**
     * 附件存储路径
     */
    @Value("${file.upload-path}")
    private String uploadPath;

    /**
     * 附件路径映射，映射后可直接通过接口访问文件
     * 例如：http://localhost:10010/api/file/123.png
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/file/**").addResourceLocations("file:"+uploadPath);
    }
}
