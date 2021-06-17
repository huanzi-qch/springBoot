package cn.huanzi.qch.springbootadminclient.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 针对actuator接口做安全限制，只允许服务端调用
 */
@WebFilter
@ServletComponentScan
@Component
public class ActuatorFilter implements Filter {
    @Value("${spring.boot.admin.client.port}")
    private String adminServicePort;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        //判断约定好的请求头参数
        if (request.getRequestURI().contains("/actuator") && !adminServicePort.equals(request.getHeader("spring-boot-admin-service"))){
            throw new RuntimeException("抱歉，你无权限访问，Actuator端口受保护！ Sorry, you have no permission to access it，Actuator port protected！");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
