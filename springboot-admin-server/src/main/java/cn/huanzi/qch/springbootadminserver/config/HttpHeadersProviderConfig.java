package cn.huanzi.qch.springbootadminserver.config;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * 注入额外的请求头，方便客户端区分请求来源
 */
@Component
public class HttpHeadersProviderConfig implements HttpHeadersProvider {
    @Value("${server.port}")
    private String port;

    @Override
    public HttpHeaders getHeaders(Instance instance) {
        HttpHeaders httpHeaders = new HttpHeaders();
        //设置约定好的请求头参数
        httpHeaders.add("spring-boot-admin-service", port);
        return httpHeaders;
    }
}
